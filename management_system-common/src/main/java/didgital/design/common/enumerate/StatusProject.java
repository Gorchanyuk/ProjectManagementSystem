package didgital.design.common.enumerate;

public enum StatusProject {

    DRAFT("Черновик"),
    DEVELOP("В разработке"),
    TEST("В тестировании"),
    COMPLETE("Завершен");

    private String status;

    StatusProject(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
