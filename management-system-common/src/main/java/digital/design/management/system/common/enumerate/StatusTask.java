package digital.design.management.system.common.enumerate;

public enum StatusTask {
    CLOSE (null),   //Закрыта
    COMPLETE (CLOSE),       //Выполнена
    WORK (COMPLETE),        //В работе
    NEW (WORK);             //Новая

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

