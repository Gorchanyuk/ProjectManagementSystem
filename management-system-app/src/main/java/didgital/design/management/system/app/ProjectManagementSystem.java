package didgital.design.management.system.app;

import didgital.design.management.system.common.enumerate.RoleEmployee;
import didgital.design.management.system.common.enumerate.StatusProject;
import didgital.design.management.system.dao.EmployeeJdbcDAO;
import didgital.design.management.system.dao.ProjectJdbcDAO;
import didgital.design.management.system.dto.employee.EmployeeSearchDTO;
import didgital.design.management.system.entity.Employee;
import didgital.design.management.system.entity.Project;

import java.util.List;


public class ProjectManagementSystem {
    public static void main(String[] args) {

//        EmployeeRepositoryImpl repository =
//                new EmployeeRepositoryImpl("employee.model");
//
//        Employee employee1 = Employee.builder()
//                .lastName("Jonson")
//                .firstName("Tom")
//                .email("Tom@mail.com")
//                .status(StatusEmployee.ACTIV)
//                .username("snake")
//                .build();
//
//        Employee employee2 = Employee.builder()
//                .lastName("Black")
//                .firstName("John")
//                .email("John@mail.com")
//                .status(StatusEmployee.ACTIV)
//                .username("batman")
//                .build();
//
//        Employee employee3 = Employee.builder()
//                .lastName("Willis")
//                .firstName("Jack")
//                .email("Jack@mail.com")
//                .status(StatusEmployee.ACTIV)
//                .username("mario")
//                .build();
//
//        Employee employee4 = Employee.builder()
//                .lastName("Smith")
//                .firstName("John")
//                .email("new_mail@mail.com")
//                .status(StatusEmployee.ACTIV)
//                .username("superman")
//                .build();
//
//        System.out.println("========================================================================");
//        System.out.println("Создание 1-й сущности:");
//        System.out.println(repository.create(employee1));
//        System.out.println("========================================================================");
//        System.out.println("Создание 2-й сущности:");
//        System.out.println(repository.create(employee2));
//        System.out.println("========================================================================");
//        System.out.println("Создание 3-й сущности:");
//        System.out.println(repository.create(employee3));
//        System.out.println("========================================================================");
//
//        System.out.println("Получение всех сущностей");
//        System.out.println(repository.getAll());
//        System.out.println("========================================================================");
//
//        System.out.println("Получение сущности с id = 1");
//        System.out.println(repository.getById(1L));
//        System.out.println("========================================================================");
//
//        System.out.println("Изменение сущности с id = 1");
//        System.out.println(repository.update(1L, employee4));
//        System.out.println("========================================================================");
//
//        System.out.println("Получение изменной сущности с id = 1");
//        System.out.println(repository.getById(1L));
//        System.out.println("========================================================================");
//
//        System.out.println("Удаление сущности с id = 2");
//        repository.deleteById(2L);
//        System.out.println("========================================================================");
//
//        System.out.println("Получение всех сущностей");
//        System.out.println(repository.getAll());
//        System.out.println("========================================================================");

        ProjectJdbcDAO dao = new ProjectJdbcDAO();
//
        Project project1 = Project.builder()
                .description("some text 1")
                .code("testProject1")
                .name("test1")
                .status(StatusProject.DRAFT)
                .build();

        Project project2 = Project.builder()
                .description("some text 2")
                .code("testProject2")
                .name("test2")
                .status(StatusProject.COMPLETE)
                .build();

        Project project3 = Project.builder()
                .description("some text 3")
                .code("testProject3")
                .name("test1")
                .status(StatusProject.DEVELOP)
                .build();

        Project project4 = Project.builder()
                .description("updated")
                .code("update")
                .name("update")
                .status(StatusProject.COMPLETE)
                .build();
//
        System.out.println("===============================================================");
        System.out.println("Получаем все записи из БД");
        System.out.println(dao.getAll());
        System.out.println("===============================================================");
        System.out.println("Создаем 1й проект");
        System.out.println(dao.create(project1));
        System.out.println("===============================================================");
        System.out.println("Создаем 2й проект");
        System.out.println(dao.create(project2));
        System.out.println("===============================================================");
        System.out.println("Создаем 3й проект");
        System.out.println(dao.create(project3));
        System.out.println("===============================================================");
        System.out.println("Получаем проект с id = 1");
        System.out.println(dao.getById(1L));
        System.out.println("===============================================================");
        System.out.println("Удаляем проект с id = 2");
        dao.deleteById(2L);
        System.out.println("===============================================================");
        System.out.println("Получаем все записи из БД");
        System.out.println(dao.getAll());
        System.out.println("===============================================================");
        System.out.println("Изменяем проект с id = 1");
        System.out.println(dao.update(1L, project4));

        System.out.println("===============================================================");
        System.out.println("Ищем сотрудников состоящих в команде и имеющих роль 'Руководитель проекта'," +
                " и при этом этот проект имеет статус 'В разработке'");

        EmployeeJdbcDAO searcDao = new EmployeeJdbcDAO();
        EmployeeSearchDTO dto = new EmployeeSearchDTO(StatusProject.DEVELOP, RoleEmployee.PROJECT_MANAGER);
        List<Employee> searchDTOS = searcDao.searchEmployee(dto);

        System.out.println(searchDTOS);
    }
}
