package didgital.design.management.system.common.enumerate;

public enum StatusTask {
    NEW("Новая"),
    WORK("В работе"),
    COMPLETE("Выполнена"),
    CLOSE("Закрыта");

    private String status;

    StatusTask(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
