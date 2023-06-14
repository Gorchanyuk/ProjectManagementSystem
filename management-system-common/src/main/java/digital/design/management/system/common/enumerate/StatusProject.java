package digital.design.management.system.common.enumerate;

public enum StatusProject {

    COMPLETE( null),   //Завершен
    TEST( COMPLETE),            //В тестировании
    DEVELOP( TEST),             //В разработке
    DRAFT( DEVELOP);            //Черновик
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

