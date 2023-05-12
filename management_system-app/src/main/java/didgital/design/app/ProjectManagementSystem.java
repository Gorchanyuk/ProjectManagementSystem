package didgital.design.app;

import didgital.design.common.enumerate.StatusEmployee;
import didgital.design.entity.Employee;
import didgital.design.repository.impl.EmployeeRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class ProjectManagementSystem {
    public static void main(String[] args) {

        EmployeeRepositoryImpl repository = new EmployeeRepositoryImpl();
        List<Employee> employees = new ArrayList<>();

        Employee employee1 = Employee.builder()
                .lastName("Jonson")
                .firstName("Tom")
                .email("Tom@mail.com")
                .status(StatusEmployee.ACTIV)
                .username("snake")
                .build();

        Employee employee2 = Employee.builder()
                .lastName("Black")
                .firstName("John")
                .email("John@mail.com")
                .status(StatusEmployee.ACTIV)
                .username("batman")
                .build();

        Employee employee3 = Employee.builder()
                .lastName("Willis")
                .firstName("Jack")
                .email("Jack@mail.com")
                .status(StatusEmployee.ACTIV)
                .username("mario")
                .build();

        Employee employee4 = Employee.builder()
                .lastName("Smith")
                .firstName("John")
                .email("new_mail@mail.com")
                .status(StatusEmployee.ACTIV)
                .username("superman")
                .build();

        System.out.println("========================================================================");
        System.out.println("Создание 1-й сущности:");
        System.out.println(repository.create(employee1));
        System.out.println("========================================================================");
        System.out.println("Создание 2-й сущности:");
        System.out.println(repository.create(employee2));
        System.out.println("========================================================================");
        System.out.println("Создание 3-й сущности:");
        System.out.println(repository.create(employee3));
        System.out.println("========================================================================");

        System.out.println("Получение всех сущностей");
        System.out.println(repository.getAll());
        System.out.println("========================================================================");

        System.out.println("Получение сущности с id = 1");
        System.out.println(repository.getById(1L));
        System.out.println("========================================================================");

        System.out.println("Изменение сущности с id = 1");
        System.out.println(repository.update(1L, employee4));
        System.out.println("========================================================================");

        System.out.println("Получение изменной сущности с id = 1");
        System.out.println(repository.getById(1L));
        System.out.println("========================================================================");

        System.out.println("Удаление сущности с id = 2");
        repository.deleteById(2L);
        System.out.println("========================================================================");

        System.out.println("Получение всех сущностей");
        System.out.println(repository.getAll());
        System.out.println("========================================================================");

    }
}
