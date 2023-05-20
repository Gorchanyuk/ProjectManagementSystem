package digital.design.management.system.enumerate;


import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum RoleEmployee {

    @JsonEnumDefaultValue
    PROJECT_MANAGER("Руководитель проекта"),

    ANALYST("Аналитик"),

    DEVELOPER("Разработчик"),

    TESTER("Тестировщик");

    private final String role;

    RoleEmployee(String role) {
        this.role = role;
    }


    public String getStatus() {
        return role;
    }

}
