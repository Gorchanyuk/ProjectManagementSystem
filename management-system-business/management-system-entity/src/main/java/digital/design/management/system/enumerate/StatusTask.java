package digital.design.management.system.enumerate;

public enum StatusTask {
    NEW("Новая"),
    WORK("В работе"),
    COMPLETE("Выполнена"),
    CLOSE("Закрыта");

    private final String status;

    StatusTask(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

