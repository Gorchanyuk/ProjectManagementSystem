package digital.design.management.system.common.enumerate;

public enum StatusProject {

    COMPLETE( null),
    TEST( COMPLETE),
    DEVELOP( TEST),
    DRAFT( DEVELOP);
    private final StatusProject nextStatus;

    StatusProject(StatusProject nextStatus) {
        this.nextStatus = nextStatus;
    }

    public StatusProject getNextStatus() {
        return nextStatus;
    }

    public boolean hasNextStatus() {
        return nextStatus != null;
    }
}

