package digital.design.management.system.service;


import digital.design.management.system.common.exception.SuchUsernameAlreadyExistException;
import digital.design.management.system.dto.employee.EmployeeDTO;
import digital.design.management.system.dto.employee.EmployeeOutDTO;
import digital.design.management.system.entity.Employee;
import digital.design.management.system.enumerate.StatusEmployee;
import digital.design.management.system.mapping.Mapper;
import digital.design.management.system.repository.EmployeeRepository;
import digital.design.management.system.repository.ProjectTeamRepository;
import lombok.RequiredArgsConstructor;
import digital.design.management.system.common.exception.EmployeeDoesNotExistException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Log4j2
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final Mapper<Employee, EmployeeDTO, EmployeeOutDTO> mapper;
    private final ProjectTeamRepository projectTeamRepository;
    private final PasswordEncoder passwordEncoder;

    Employee findByUid(UUID uid) {
        return employeeRepository.findByUidAndStatus(uid, StatusEmployee.ACTIVE)
                .orElseThrow(EmployeeDoesNotExistException::new);
    }

    public List<EmployeeOutDTO> getEmployees() {
        List<Employee> employees =
                employeeRepository.findTop100ByStatus(StatusEmployee.ACTIVE).stream()
                        .toList();
        log.info("All Employee found");
        return employees.stream()
                .map(mapper::entityToOutDto)
                .toList();
    }

    public EmployeeOutDTO getEmployeeByUid(UUID uid) {
        Employee employee = findByUid(uid);
        log.info("Employee with uid: {} found", uid);
        return mapper.entityToOutDto(employee);
    }

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

    public EmployeeOutDTO updateEmployee(UUID uid, EmployeeDTO employeeDTO) {
        log.debug("Update Employee with uid: {}", uid);
        Employee employee = findByUid(uid);
        log.debug("Employee with uid: {} found", uid);
        //Проверка на повотряющиеся username, в случаее если это поле изменили
        if (employee.getUsername() != null && employeeDTO.getUsername() != null &&
                !employee.getUsername().equals(employeeDTO.getUsername()) &&
                employeeRepository.findByUsernameAndStatus(employeeDTO.getUsername(), StatusEmployee.ACTIVE).isPresent()) {
            throw new SuchUsernameAlreadyExistException();
        }
        if (ObjectUtils.isEmpty(employee.getEmail()) && !ObjectUtils.isEmpty(employeeDTO.getEmail())){
            //Если почты не было и сейчас добавили
            employee.setPassword(passwordEncoder.encode(generatePassword()));
            log.debug("For Employee with uid: {} generated password", uid);
            //TODO Добавить отправление по почте
        }
        employee = mapper.dtoToEntity(employeeDTO, employee);
        employeeRepository.save(employee);
        log.info("Employee with uid: {} updated", uid);
        return mapper.entityToOutDto(employee);
    }

    public List<EmployeeOutDTO> getEmployeeByKeyWord(String key) {
        List<Employee> employees =
                employeeRepository.findByKeyword(key, StatusEmployee.ACTIVE);
        log.info("All Employee with keyword found");
        return employees.stream()
                .map(mapper::entityToOutDto)
                .toList();
    }

    public EmployeeOutDTO createEmployee(EmployeeDTO employeeDTO) {
        log.debug("Create new Employee");
        Employee employee = mapper.dtoToEntity(employeeDTO);
        employee.setStatus(StatusEmployee.ACTIVE);

        if (employee.getEmail() != null) {
            log.debug("For Employee generated password");
            employee.setPassword(generatePassword());
            //TODO добавить отправку сообщения на почту
        }
        employeeRepository.save(employee);
        log.info("New Employee created");
        return mapper.entityToOutDto(employee);
    }

    private String generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=[]{}|;,./?";
        return RandomStringUtils.random(8, chars);
    }

}
