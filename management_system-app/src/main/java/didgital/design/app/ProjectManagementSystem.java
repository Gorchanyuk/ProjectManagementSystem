package didgital.design.app;

import didgital.design.app.controller.EmployeeController;
import didgital.design.entity.Employee;

public class ProjectManagementSystem {
    public static void main(String[] args) {
        System.out.println("старт приложения");
        EmployeeController.contr();
        Employee.entity();
    }
}
