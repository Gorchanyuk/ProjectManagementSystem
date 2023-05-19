package digital.design.management.system.dao;

import digital.design.management.system.jdbcconfig.JdbcConfig;
import digital.design.management.system.common.enumerate.RoleEmployee;
import digital.design.management.system.common.enumerate.StatusEmployee;
import digital.design.management.system.common.enumerate.StatusProject;
import digital.design.management.system.dto.employee.EmployeeSearchDTO;
import digital.design.management.system.entity.Employee;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeJdbcDAO {

    private final JdbcConfig jdbcConfig;

    public EmployeeJdbcDAO(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }

//    public EmployeeJdbcDAO(){
//        jdbcConfig = new JdbcConfig();
//    }

    public List<Employee> searchEmployee(EmployeeSearchDTO dto) {

        List<Employee> employees = new ArrayList<>();
        String employeeStatus = StatusEmployee.ACTIV.getStatus();
        String roleEmployee = dto.getRoleEmployee() !=null
                ? dto.getRoleEmployee().getStatus()
                : RoleEmployee.PROJECT_MANAGER.getStatus();
        String statusProject = dto.getStatusProject() != null
                ? dto.getStatusProject().getStatus()
                : StatusProject.COMPLETE.getStatus();

        String query =
                "SELECT DISTINCT e.id, e.last_name, e.first_name, e.job_title, e.email " +
                        "FROM Employee e " +
                        "LEFT JOIN Project_Team pt ON e.id = pt.employee_id " +
                        "LEFT JOIN Project p ON pt.project_id = p.id " +
                        "WHERE e.status = ? " +
                        "AND pt.role_employee = ? " +
                        "AND p.status = ?";

        try (Connection connection = jdbcConfig.getConnection()) {

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, employeeStatus);
            ps.setString(2, roleEmployee);
            ps.setString(3, statusProject);

            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setJodTitle(resultSet.getString("job_title"));
                employee.setEmail(resultSet.getString("email"));

                employees.add(employee);
            }

            return employees;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    }
