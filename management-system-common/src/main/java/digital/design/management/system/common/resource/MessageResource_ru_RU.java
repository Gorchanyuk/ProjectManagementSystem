package digital.design.management.system.common.resource;

import java.util.ListResourceBundle;

public class MessageResource_ru_RU extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"EMPLOYEE_DOES_NOT_EXIST", "Сотрудник с таким uid не найден"},
                {"EMPLOYEE_BY_USERNAME_DOES_NOT_EXIST", "Сотрудник с такой учетной записью не найден"},
                {"PROJECT_DOES_NOT_EXIST", "Проект с таким id не найден"},
                {"SUCH_USERNAME_ALREADY_EXIST", "Сотрудник с такой учетной записью уже существует"},
                {"SUCH_CODE_PROJECT_ALREADY_EXIST", "Проект с таким кодом уже существует"},
                {"STATUS_PROJECT_EXCEPTION", "Статус должен быть представлен одним из следующих вариантов DRAFT, DEVELOP, TEST или COMPLETE"},
                {"STATUS_PROJECT_HAS_NOT_NEXT_STATUS", "Нельзя повысить статус проекта"},
                {"EMPLOYEE_ALREADY_PARTICIPATING_IN_PROJECT", "Сотрудник с таким uid уже учавствует в проекте"},

        };
    }
}
