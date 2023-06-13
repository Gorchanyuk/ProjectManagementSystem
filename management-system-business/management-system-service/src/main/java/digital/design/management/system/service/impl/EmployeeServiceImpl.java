package digital.design.management.system.service.impl;


import digital.design.management.system.common.exception.SuchUsernameAlreadyExistException;
import digital.design.management.system.dto.employee.EmployeeDTO;
import digital.design.management.system.dto.employee.EmployeeOutDTO;
import digital.design.management.system.dto.mail.EmailDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.common.enumerate.StatusEmployee;
import digital.design.management.system.util.CreatorMailDTO;
import digital.design.management.system.rabbitmq.MessageProducer;
import digital.design.management.system.mapping.Mapper;
import digital.design.management.system.repository.EmployeeRepository;
import digital.design.management.system.repository.ProjectTeamRepository;
import digital.design.management.system.service.EmployeeService;
import digital.design.management.system.util.PasswordGenerator;
import lombok.RequiredArgsConstructor;
import digital.design.management.system.common.exception.EmployeeDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final Mapper<Employee, EmployeeDTO, EmployeeOutDTO> mapper;
    private final ProjectTeamRepository projectTeamRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordGenerator passwordGenerator;
    private final MessageProducer messageProducer;
    private final CreatorMailDTO creatorMailDTO;

    public Employee findByUid(UUID uid) {
        log.debug("Search for the employee with uid: {}", uid);
        Employee employee = employeeRepository.findByUidAndStatus(uid, StatusEmployee.ACTIVE)
                .orElseThrow(EmployeeDoesNotExistException::new);
        log.info("Employee with uid: {} found", uid);
        return employee;
    }

    @Override
    public List<EmployeeOutDTO> getEmployees() {
        List<Employee> employees =
                employeeRepository.findTop100ByStatus(StatusEmployee.ACTIVE).stream()
                        .toList();
        log.info("All Employee found");
        return employees.stream()
                .map(mapper::entityToOutDto)
                .toList();
    }

    @Override
    public EmployeeOutDTO getEmployeeByUid(UUID uid) {
        Employee employee = findByUid(uid);
        return mapper.entityToOutDto(employee);
    }

    @Override
    @Transactional
    public EmployeeOutDTO deleteEmployee(UUID uid) {
        Employee employee = findByUid(uid);
        employee.setStatus(StatusEmployee.DELETED);
        employeeRepository.save(employee);
        //Удаляем этого сотрудника из всех проектов
        projectTeamRepository.deleteAllByEmployeeId(employee);
        log.info("Employee with uid: {} deleted", uid);
        return mapper.entityToOutDto(employee);
    }

    @Override
    public EmployeeOutDTO updateEmployee(UUID uid, EmployeeDTO employeeDTO) {
        log.debug("Update Employee with uid: {}", uid);
        Employee employee = findByUid(uid);
        //Проверка на повотряющиеся username, в случаее если это поле изменили
        if (!ObjectUtils.isEmpty(employeeDTO.getUsername()) &&
                !ObjectUtils.nullSafeEquals(employee.getUsername(), employeeDTO.getUsername()) &&
                employeeRepository.findByUsernameAndStatus(employeeDTO.getUsername(), StatusEmployee.ACTIVE).isPresent()) {
            throw new SuchUsernameAlreadyExistException();
        }
        String oldEmail = employee.getEmail(); //Адрес почты. до обновления сотрудника
        employee = mapper.dtoToEntity(employeeDTO, employee);
        //Проверка изменился адрес почты
        if (!ObjectUtils.isEmpty(employeeDTO.getEmail()) &&
            !ObjectUtils.nullSafeEquals(oldEmail, employeeDTO.getEmail())) {
            setPasswordAndSendEmail(employee);
            log.debug("For Employee with uid: {} has been generated password", uid);
        }
        employeeRepository.save(employee);
        log.info("Employee with uid: {} updated", uid);
        return mapper.entityToOutDto(employee);
    }

    @Override
    public List<EmployeeOutDTO> getEmployeeByKeyWord(String key) {
        List<Employee> employees =
                employeeRepository.findByKeyword(key, StatusEmployee.ACTIVE);
        log.info("All Employee with keyword {} found", key);
        return employees.stream()
                .map(mapper::entityToOutDto)
                .toList();
    }

    @Override
    public EmployeeOutDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = mapper.dtoToEntity(employeeDTO);
        UUID uid = UUID.randomUUID();
        employee.setUid(uid);
        log.debug("Create new Employee with uid: {}", uid);
        employee.setStatus(StatusEmployee.ACTIVE);

        if (!ObjectUtils.isEmpty(employee.getEmail())) {
            log.debug("For Employee {} generated password", uid);
            setPasswordAndSendEmail(employee);
        }
        employeeRepository.save(employee);
        log.info("New Employee created, uid: {}", uid);
        return mapper.entityToOutDto(employee);
    }

    private void setPasswordAndSendEmail(Employee employee) {
        String password = passwordGenerator.generate();
        employee.setPassword(passwordEncoder.encode(password));
        EmailDTO emailDTO = creatorMailDTO.getDtoForPassword(employee, password);
        messageProducer.sendMessage(emailDTO);
    }
}
