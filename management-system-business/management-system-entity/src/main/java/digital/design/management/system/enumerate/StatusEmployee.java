package digital.design.management.system.enumerate;

public enum StatusEmployee {
    ACTIV("Активный"),
    DELETED("Удаленный");

    private final String status;

    StatusEmployee(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
