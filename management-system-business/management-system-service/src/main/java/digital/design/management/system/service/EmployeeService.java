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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final Mapper<Employee, EmployeeDTO, EmployeeOutDTO> mapper;
    private final ProjectTeamRepository projectTeamRepository;

    Employee findByUid(UUID uid) {
        return employeeRepository.findByUidAndStatus(uid, StatusEmployee.ACTIV)
                .orElseThrow(EmployeeDoesNotExistException::new);
    }

    public List<EmployeeOutDTO> getEmployees() {
        List<Employee> employees =
                employeeRepository.findTop100ByStatus(StatusEmployee.ACTIV).stream()
                        .toList();

        return employees.stream()
                .map(mapper::entityToOutDto)
                .toList();
    }

    public EmployeeOutDTO getEmployeeByUid(UUID uid) {
        Employee employee = findByUid(uid);

        return mapper.entityToOutDto(employee);
    }

    @Transactional
    public EmployeeOutDTO deleteEmployee(UUID uid) {
        Employee employee = findByUid(uid);
        employee.setStatus(StatusEmployee.DELETED);
        employeeRepository.save(employee);
        //Удаляем этого сотрудника из всех проектов
        projectTeamRepository.deleteAllByEmployeeId(employee);

        return mapper.entityToOutDto(employee);
    }

    public EmployeeOutDTO updateEmployee(UUID uid, EmployeeDTO employeeDTO) {
        Employee employee = findByUid(uid);
        //Проверка на повотряющиеся username, в случаее если это поле изменили
        if (employee.getUsername() != null && employeeDTO.getUsername() != null &&
                !employee.getUsername().equals(employeeDTO.getUsername()) &&
                employeeRepository.findByUsernameAndStatus(employeeDTO.getUsername(), StatusEmployee.ACTIV).isPresent()) {
            throw new SuchUsernameAlreadyExistException();
        }
        employee = mapper.dtoToEntity(employeeDTO, employee);
        employeeRepository.save(employee);

        return mapper.entityToOutDto(employee);
    }

    public List<EmployeeOutDTO> getEmployeeByKeyWord(String key) {
        List<Employee> employees =
                employeeRepository.findByKeyword(key, StatusEmployee.ACTIV);

        return employees.stream()
                .map(mapper::entityToOutDto)
                .toList();
    }

    public EmployeeOutDTO createEmployee(EmployeeDTO employeeDTO) {
//        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        Employee employee = mapper.dtoToEntity(employeeDTO);
        employee.setStatus(StatusEmployee.ACTIV);
        //TODO добавить генерацию пароля
        employeeRepository.save(employee);

        return mapper.entityToOutDto(employee);
    }
}
