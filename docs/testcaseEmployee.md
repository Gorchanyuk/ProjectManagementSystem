<style type="text/css">
.tg .tg-cly1{text-align:left;vertical-align:middle}
.tg .tg-lboi{text-align:left;vertical-align:middle}
.tg .tg-9wq8{text-align:center;vertical-align:middle}
.tg .tg-nrix{text-align:center;vertical-align:middle}
.tg .tg-0pky{text-align:left;vertical-align:top}
.tg .tg-0lax{text-align:left;vertical-align:top}
</style>
<table class="tg" style="table-layout: fixed; width: 1500px">

<thead>
  <tr>
    <th class="tg-9wq8">id</th>
    <th class="tg-9wq8">Название</th>
    <th class="tg-9wq8">Описание</th>
    <th class="tg-nrix">Окружающая среда</th>
    <th class="tg-9wq8">Шаги</th>
    <th class="tg-9wq8">Ожидаемый результат</th>
    <th class="tg-nrix">Фактический результат</th>
    <th class="tg-nrix">Статус</th>
  </tr>
</thead>
<tbody>
  <tr>
    <td class="tg-9wq8" rowspan="3">1</td>
    <td class="tg-9wq8" rowspan="3">getEmployeesShouldGetListEmployee</td>
    <td class="tg-9wq8" rowspan="3">Получение всех сотрудников из БД</td>
    <td class="tg-nrix" rowspan="3">IntelliJ IDEA 2023.1<br>JUnit 5</td>
    <td class="tg-0pky">1. Сгенерировать и записать начальные данные в БД</td>
    <td class="tg-9wq8" rowspan="3">Получим все записи из БД</td>
    <td class="tg-nrix" rowspan="3">Получены все записи из БД в виде <span style="font-style:italic">List</span>&lt;<span style="font-style:italic">EmployeeOutDTO</span>&gt;</td>
    <td class="tg-nrix" rowspan="3">Успешно</td>
  </tr>
  <tr>
    <td class="tg-lboi">2. Получить записи из БД</td>
  </tr>
  <tr>
    <td class="tg-0pky">3. Проверить результат</td>
  </tr>
  <tr>
    <td class="tg-nrix" rowspan="3">2</td>
    <td class="tg-nrix" rowspan="3">getEmployeeByUidShouldGetEmployeeByUid</td>
    <td class="tg-nrix" rowspan="3">Получение сотрудника по uid</td>
    <td class="tg-nrix" rowspan="3">IntelliJ IDEA 2023.1<br>JUnit 5<br></td>
    <td class="tg-0lax">1. Сгенерировать и записать начальные данные в БД</td>
    <td class="tg-nrix" rowspan="3">Получим сотрудника по uid</td>
    <td class="tg-nrix" rowspan="3">Сотрудник получен</td>
    <td class="tg-nrix" rowspan="3">Успешно</td>
  </tr>
  <tr>
    <td class="tg-cly1">2. Получить сотрудника по его uid</td>
  </tr>
  <tr>
    <td class="tg-0lax">3. Проверить результат</td>
  </tr>
  <tr>
    <td class="tg-nrix" rowspan="4">3</td>
    <td class="tg-nrix" rowspan="4">deleteEmployeeShouldChangeStatusEmployee</td>
    <td class="tg-nrix" rowspan="4">Удаление сотрудника по uid</td>
    <td class="tg-nrix" rowspan="4">IntelliJ IDEA 2023.1<br>JUnit 5 </td>
    <td class="tg-0lax">1. Сгенерировать и записать начальные данные в БД</td>
    <td class="tg-nrix" rowspan="4">Сотрудник должен быть удален</td>
    <td class="tg-nrix" rowspan="4">Сотрудник удален</td>
    <td class="tg-nrix" rowspan="4">Успешно</td>
  </tr>
  <tr>
    <td class="tg-cly1">2. Получить uid одного из созданных сотрудников</td>
  </tr>
  <tr>
    <td class="tg-cly1">3. Удаляем сотрудника</td>
  </tr>
  <tr>
    <td class="tg-0lax">4. Проверить результат</td>
  </tr>
  <tr>
    <td class="tg-nrix" rowspan="4">4</td>
    <td class="tg-nrix" rowspan="4">deleteEmployeeShouldDeleteEmployeeFromTeams</td>
    <td class="tg-nrix" rowspan="4">Удаление сотрудника из всех команд, при удалении из системы</td>
    <td class="tg-nrix" rowspan="4">IntelliJ IDEA 2023.1<br>JUnit 5</td>
    <td class="tg-0lax">1. Сгенерировать и записать начальные данные в БД</td>
    <td class="tg-nrix" rowspan="4">Сотрудник должен быть исключен из всех команд проектов</td>
    <td class="tg-nrix" rowspan="4">Сотрудник удален из всех команд</td>
    <td class="tg-nrix" rowspan="4">Успешно</td>
  </tr>
  <tr>
    <td class="tg-0lax">2. Получить uid одного из созданных сотрудников</td>
  </tr>
  <tr>
    <td class="tg-0lax">3. Удалить сотрудника</td>
  </tr>
  <tr>
    <td class="tg-0lax">4. Проверить результат</td>
  </tr>
  <tr>
    <td class="tg-nrix" rowspan="4">5</td>
    <td class="tg-nrix" rowspan="4">updateEmployeeShouldUpdateEmployee</td>
    <td class="tg-nrix" rowspan="4">Изменение данных сотрудника</td>
    <td class="tg-nrix" rowspan="4">IntelliJ IDEA 2023.1<br>JUnit 5</td>
    <td class="tg-0lax">1. Сгенерировать и записать начальные данные в БД</td>
    <td class="tg-nrix" rowspan="4">Данные сотрудник должны быть обновлены</td>
    <td class="tg-nrix" rowspan="4">Данные сотрудника обновлены</td>
    <td class="tg-nrix" rowspan="4">Успешно</td>
  </tr>
  <tr>
    <td class="tg-0lax">2. Создать модель с обновленными данными о сотруднике</td>
  </tr>
  <tr>
    <td class="tg-0lax">3.  Обновить данные</td>
  </tr>
  <tr>
    <td class="tg-0lax">4. Проверить результат</td>
  </tr>
  <tr>
    <td class="tg-nrix" rowspan="4">6</td>
    <td class="tg-nrix" rowspan="4">getEmployeeByKeyWordShouldFindEmployee</td>
    <td class="tg-nrix" rowspan="4">Поиск сотрудника по ключевому слову</td>
    <td class="tg-nrix" rowspan="4">IntelliJ IDEA 2023.1<br>JUnit 5</td>
    <td class="tg-0lax">1. Сгенерировать и записать начальные данные в БД</td>
    <td class="tg-nrix" rowspan="4">Должны быть получены сотрудники подпадающие под критерии поиска</td>
    <td class="tg-nrix" rowspan="4">Сотрудники найдены, поиск выдал только соответствующих критериям поиска сотрудников</td>
    <td class="tg-nrix" rowspan="4">Успешно</td>
  </tr>
  <tr>
    <td class="tg-0lax">2. Изменить разные поля у нескольких из ранее созданных сотрудников, что бы они соответствовали ключевому слову</td>
  </tr>
  <tr>
    <td class="tg-0lax">3. Осуществить поиск по ключевому слову</td>
  </tr>
  <tr>
    <td class="tg-0lax">4. Проверить результат</td>
  </tr>
  <tr>
    <td class="tg-nrix" rowspan="3">7</td>
    <td class="tg-nrix" rowspan="3">createEmployeeShouldCreateNewEmployee</td>
    <td class="tg-nrix" rowspan="3">Добавление нового сотрудника в БД</td>
    <td class="tg-nrix" rowspan="3">IntelliJ IDEA 2023.1<br>JUnit 5</td>
    <td class="tg-0lax">1. Создать модель сотрудника</td>
    <td class="tg-nrix" rowspan="3">В БД должен быть добавлен новый сотрудник</td>
    <td class="tg-nrix" rowspan="3">Новый сотрудник добавлен в БД</td>
    <td class="tg-nrix" rowspan="3">Успешно</td>
  </tr>
  <tr>
    <td class="tg-0lax">2. Добавить сотрудника</td>
  </tr>
  <tr>
    <td class="tg-0lax">3. Проверить результат</td>
  </tr>
</tbody>
</table>