
<table style="table-layout: fixed; width: 1482px">
<thead>
  <tr>
    <th>id</th>
    <th>Название</th>
    <th>Описание</th>
    <th>Окружающая среда</th>
    <th>Шаги</th>
    <th>Ожидаемый результат</th>
    <th>Фактический результат</th>
    <th>Статус</th>
  </tr>
</thead>
<tbody>
  <tr>
    <td rowspan="3">1</td>
    <td rowspan="3">getAllPartyShouldGetAllParty</td>
    <td rowspan="3">Получение всех сотрудников участвующих в заданном проекте</td>
    <td rowspan="3">IntelliJ IDEA 2023.1<br>JUnit 5</td>
    <td>1. Сгенерировать и записать начальные данные в БД</td>
    <td rowspan="3">Получим команду сотрудников заданного проекта</td>
    <td rowspan="3">Команда сотрудников была получена</td>
    <td rowspan="3">Успешно</td>
  </tr>
  <tr>
    <td>2. Получить команду сотрудников</td>
  </tr>
  <tr>
    <td>3. Проверить результат</td>
  </tr>
  <tr>
    <td rowspan="4">2</td>
    <td rowspan="4">addParticipantShouldAddNewParticipant</td>
    <td rowspan="4">Добавление сотрудника в команду проекта</td>
    <td rowspan="4">IntelliJ IDEA 2023.1<br>JUnit 5<br></td>
    <td>1. Сгенерировать и записать начальные данные в БД</td>
    <td rowspan="4">Сотрудник будет добавлен в команду</td>
    <td rowspan="4">Сотрудник был добавлен в команду</td>
    <td rowspan="4">Успешно</td>
  </tr>
  <tr>
    <td>2. Создать модель добавляемого сотрудника</td>
  </tr>
  <tr>
    <td>3. Добавить сотрудника в команду</td>
  </tr>
  <tr>
    <td>4. Проверить результат</td>
  </tr>
  <tr>
    <td rowspan="4">3</td>
    <td rowspan="4">deleteParticipantShouldDeleteParticipant</td>
    <td rowspan="4">Удаление сотрудника из команды проекта</td>
    <td rowspan="4">IntelliJ IDEA 2023.1<br>JUnit 5 </td>
    <td>1. Сгенерировать и записать начальные данные в БД</td>
    <td rowspan="4">Сотрудник будет удален из команды проекта</td>
    <td rowspan="4">Сотрудник был удален из команды проекта</td>
    <td rowspan="4">Успешно</td>
  </tr>
  <tr>
    <td>2. Создать модель удаляемого сотрудника</td>
  </tr>
  <tr>
    <td>3. Удалить сотрудника из команды</td>
  </tr>
  <tr>
    <td>4. Проверить результат</td>
  </tr>
</tbody>
</table>