
<table style="table-layout: fixed; width: 1500px">

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
    <td rowspan="3">getEmployeesShouldGetListEmployee</td>
    <td rowspan="3">Получение всех сотрудников из БД</td>
    <td rowspan="3">IntelliJ IDEA 2023.1<br>JUnit 5</td>
    <td>1. Сгенерировать и записать начальные данные в БД</td>
    <td rowspan="3">Получим все записи из БД</td>
    <td rowspan="3">Получены все записи из БД в виде <span style="font-style:italic">List</span>&lt;<span style="font-style:italic">EmployeeOutDTO</span>&gt;</td>
    <td rowspan="3">Успешно</td>
  </tr>
  <tr>
    <td>2. Получить записи из БД</td>
  </tr>
  <tr>
    <td>3. Проверить результат</td>
  </tr>
  <tr>
    <td rowspan="3">2</td>
    <td rowspan="3">getEmployeeByUidShouldGetEmployeeByUid</td>
    <td rowspan="3">Получение сотрудника по uid</td>
    <td rowspan="3">IntelliJ IDEA 2023.1<br>JUnit 5<br></td>
    <td>1. Сгенерировать и записать начальные данные в БД</td>
    <td rowspan="3">Получим сотрудника по uid</td>
    <td rowspan="3">Сотрудник получен</td>
    <td rowspan="3">Успешно</td>
  </tr>
  <tr>
    <td>2. Получить сотрудника по его uid</td>
  </tr>
  <tr>
    <td>3. Проверить результат</td>
  </tr>
  <tr>
    <td rowspan="4">3</td>
    <td rowspan="4">deleteEmployeeShouldChangeStatusEmployee</td>
    <td rowspan="4">Удаление сотрудника по uid</td>
    <td rowspan="4">IntelliJ IDEA 2023.1<br>JUnit 5 </td>
    <td>1. Сгенерировать и записать начальные данные в БД</td>
    <td rowspan="4">Сотрудник должен быть удален</td>
    <td rowspan="4">Сотрудник удален</td>
    <td rowspan="4">Успешно</td>
  </tr>
  <tr>
    <td>2. Получить uid одного из созданных сотрудников</td>
  </tr>
  <tr>
    <td>3. Удаляем сотрудника</td>
  </tr>
  <tr>
    <td>4. Проверить результат</td>
  </tr>
  <tr>
    <td rowspan="4">4</td>
    <td rowspan="4">deleteEmployeeShouldDeleteEmployeeFromTeams</td>
    <td rowspan="4">Удаление сотрудника из всех команд, при удалении из системы</td>
    <td rowspan="4">IntelliJ IDEA 2023.1<br>JUnit 5</td>
    <td>1. Сгенерировать и записать начальные данные в БД</td>
    <td rowspan="4">Сотрудник должен быть исключен из всех команд проектов</td>
    <td rowspan="4">Сотрудник удален из всех команд</td>
    <td rowspan="4">Успешно</td>
  </tr>
  <tr>
    <td>2. Получить uid одного из созданных сотрудников</td>
  </tr>
  <tr>
    <td>3. Удалить сотрудника</td>
  </tr>
  <tr>
    <td>4. Проверить результат</td>
  </tr>
  <tr>
    <td rowspan="4">5</td>
    <td rowspan="4">updateEmployeeShouldUpdateEmployee</td>
    <td rowspan="4">Изменение данных сотрудника</td>
    <td rowspan="4">IntelliJ IDEA 2023.1<br>JUnit 5</td>
    <td>1. Сгенерировать и записать начальные данные в БД</td>
    <td rowspan="4">Данные сотрудник должны быть обновлены</td>
    <td rowspan="4">Данные сотрудника обновлены</td>
    <td rowspan="4">Успешно</td>
  </tr>
  <tr>
    <td>2. Создать модель с обновленными данными о сотруднике</td>
  </tr>
  <tr>
    <td>3.  Обновить данные</td>
  </tr>
  <tr>
    <td>4. Проверить результат</td>
  </tr>
  <tr>
    <td rowspan="4">6</td>
    <td rowspan="4">getEmployeeByKeyWordShouldFindEmployee</td>
    <td rowspan="4">Поиск сотрудника по ключевому слову</td>
    <td rowspan="4">IntelliJ IDEA 2023.1<br>JUnit 5</td>
    <td>1. Сгенерировать и записать начальные данные в БД</td>
    <td rowspan="4">Должны быть получены сотрудники подпадающие под критерии поиска</td>
    <td rowspan="4">Сотрудники найдены, поиск выдал только соответствующих критериям поиска сотрудников</td>
    <td rowspan="4">Успешно</td>
  </tr>
  <tr>
    <td>2. Изменить разные поля у нескольких из ранее созданных сотрудников, что бы они соответствовали ключевому слову</td>
  </tr>
  <tr>
    <td>3. Осуществить поиск по ключевому слову</td>
  </tr>
  <tr>
    <td>4. Проверить результат</td>
  </tr>
  <tr>
    <td rowspan="3">7</td>
    <td rowspan="3">createEmployeeShouldCreateNewEmployee</td>
    <td rowspan="3">Добавление нового сотрудника в БД</td>
    <td rowspan="3">IntelliJ IDEA 2023.1<br>JUnit 5</td>
    <td>1. Создать модель сотрудника</td>
    <td rowspan="3">В БД должен быть добавлен новый сотрудник</td>
    <td rowspan="3">Новый сотрудник добавлен в БД</td>
    <td rowspan="3">Успешно</td>
  </tr>
  <tr>
    <td>2. Добавить сотрудника</td>
  </tr>
  <tr>
    <td>3. Проверить результат</td>
  </tr>
</tbody>
</table>