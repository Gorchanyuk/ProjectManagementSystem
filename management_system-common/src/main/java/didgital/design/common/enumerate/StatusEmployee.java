package didgital.design.common.enumerate;

public enum StatusEmployee {
    ACTIV("Активный"),
    DELETED("Удаленный");

    private String status;

    StatusEmployee(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}