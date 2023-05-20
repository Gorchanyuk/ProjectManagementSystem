package digital.design.management.system.enumerate;

public enum StatusProject {

    COMPLETE("Завершен", null),
    TEST("В тестировании", COMPLETE),
    DEVELOP("В разработке", TEST),
    DRAFT("Черновик", DEVELOP );

    private final String status;
    private final StatusProject nextSatatus;

    StatusProject(String status, StatusProject nextStatus) {
        this.status = status;
        this.nextSatatus = nextStatus;
    }

    public String getStatus() {
        return status;
    }

    public StatusProject getNextSatatus(){return nextSatatus;}

    public boolean hasNextStatus(){return nextSatatus != null;}
}

