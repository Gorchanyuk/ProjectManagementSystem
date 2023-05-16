package didgital.design.management.system.common.enumerate;


public enum RoleEmployee {

    PROJECT_MANAGER("Руководитель проекта"),
    ANALYST("Аналитик"),
    DEVELOPER("Разработчик"),
    TESTER("Тестировщик");

    private String role;

    RoleEmployee(String role) {
        this.role = role;
    }


    public String getStatus() {
        return role;
    }

}
