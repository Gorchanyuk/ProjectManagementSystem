package digital.design.management.system.common.enumerate;

public enum StatusTask {
    CLOSE (null),
    COMPLETE (CLOSE),
    WORK (COMPLETE),
    NEW (WORK);

    private final StatusTask nextStatus;

    StatusTask(StatusTask nextStatus) {
        this.nextStatus = nextStatus;
    }

    public StatusTask getNextStatus() {
        return nextStatus;
    }

    public boolean hasNextStatus() {
        return nextStatus != null;
    }
}

