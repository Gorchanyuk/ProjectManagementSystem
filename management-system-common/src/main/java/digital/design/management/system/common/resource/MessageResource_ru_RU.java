package digital.design.management.system.common.resource;

import java.util.ListResourceBundle;

public class MessageResource_ru_RU extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"EMPLOYEE_DOES_NOT_EXIST", "Сотрудник с таким uid не найден"},
                {"EMPLOYEE_BY_USERNAME_DOES_NOT_EXIST", "Сотрудник с такой учетной записью не найден"},
                {"PROJECT_DOES_NOT_EXIST", "Проект с таким uid не найден"},
                {"SUCH_USERNAME_ALREADY_EXIST", "Сотрудник с такой учетной записью уже существует"},
                {"SUCH_CODE_PROJECT_ALREADY_EXIST", "Проект с таким кодом уже существует"},
                {"STATUS_PROJECT_EXCEPTION", "Статус должен быть представлен одним или несколькими из следующих вариантов DRAFT, DEVELOP, TEST или COMPLETE"},
                {"STATUS_PROJECT_HAS_NOT_NEXT_STATUS", "Нельзя повысить статус проекта"},
                {"EMPLOYEE_ALREADY_PARTICIPATING_IN_PROJECT", "Сотрудник с таким uid уже учавствует в проекте"},
                {"DEADLINE_IS_TOO_SHORT", "Трудозатраты требуют больше времени"},
                {"AUTHOR_IS_NOT_INVOLVED_IN_PROJECT", "Создать задачу может только участник проекта"},
                {"EMPLOYEE_IS_NOT_INVOLVED_IN_PROJECT_1", "Задачу можно назначить только участнику проекта"},
                {"EMPLOYEE_IS_NOT_INVOLVED_IN_PROJECT_2", "Заданный сотрудник не учавствует в заданном проекте"},
                {"TASK_DOES_NOT_EXIST", "Задача с таким uid не найдена"},
                {"CANNOT_ASSIGN_GIVEN_TASK_STATUS", "Нельзя назначить заданный статус"},
                {"MAXIMUM_TASK_STATUS_EXCEPTION", "У задачи статус 'Закрыта', его нельзя изменить"}

        };
    }
}
