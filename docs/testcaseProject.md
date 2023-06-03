<style type="text/css">
.tg .tg-lboi{text-align:left;vertical-align:middle}
.tg .tg-9wq8{text-align:center;vertical-align:middle}
.tg .tg-0pky{text-align:left;vertical-align:top}
</style>
<table class="tg" style="table-layout: fixed; width: 1500px">
<thead>
  <tr>
    <th class="tg-9wq8">id</th>
    <th class="tg-9wq8">Название</th>
    <th class="tg-9wq8">Описание</th>
    <th class="tg-9wq8">Окружающая среда</th>
    <th class="tg-9wq8">Шаги</th>
    <th class="tg-9wq8">Ожидаемый результат</th>
    <th class="tg-9wq8">Фактический результат</th>
    <th class="tg-9wq8">Статус</th>
  </tr>
</thead>
<tbody>
  <tr>
    <td class="tg-9wq8" rowspan="3">1</td>
    <td class="tg-9wq8" rowspan="3">getProjectsShouldGetListProject</td>
    <td class="tg-9wq8" rowspan="3">Получение всех проектов из БД</td>
    <td class="tg-9wq8" rowspan="3">IntelliJ IDEA 2023.1<br>JUnit 5</td>
    <td class="tg-0pky">1. Сгенерировать и записать начальные данные в БД</td>
    <td class="tg-9wq8" rowspan="3">Получим все записи из БД</td>
    <td class="tg-9wq8" rowspan="3">Получены все записи из БД в виде List&lt;ProjectOutDTO&gt;</td>
    <td class="tg-9wq8" rowspan="3">Успешно</td>
  </tr>
  <tr>
    <td class="tg-lboi">2. Получить записи из БД</td>
  </tr>
  <tr>
    <td class="tg-0pky">3.Проверить результат</td>
  </tr>
  <tr>
    <td class="tg-9wq8" rowspan="3">2</td>
    <td class="tg-9wq8" rowspan="3">findByUidShouldGetProject</td>
    <td class="tg-9wq8" rowspan="3">Получим проект по uid</td>
    <td class="tg-9wq8" rowspan="3">IntelliJ IDEA 2023.1<br>JUnit 5<br></td>
    <td class="tg-0pky">1. Сгенерировать и записать начальные данные в БД</td>
    <td class="tg-9wq8" rowspan="3">Получим проект по uid</td>
    <td class="tg-9wq8" rowspan="3">Проект получен</td>
    <td class="tg-9wq8" rowspan="3">Успешно</td>
  </tr>
  <tr>
    <td class="tg-lboi">2. Получить проект по его uid</td>
  </tr>
  <tr>
    <td class="tg-0pky">3.Проверить результат</td>
  </tr>
  <tr>
    <td class="tg-9wq8" rowspan="3">3</td>
    <td class="tg-9wq8" rowspan="3">createProjectShouldCreateNewProject</td>
    <td class="tg-9wq8" rowspan="3">Добавление нового проекта в БД</td>
    <td class="tg-9wq8" rowspan="3">IntelliJ IDEA 2023.1<br>JUnit 5 </td>
    <td class="tg-0pky"> 1. Создать модель проекта</td>
    <td class="tg-9wq8" rowspan="3">В БД должен быть добавлен новый проект</td>
    <td class="tg-9wq8" rowspan="3">Новый проект добавлен в БД</td>
    <td class="tg-9wq8" rowspan="3">Успешно</td>
  </tr>
  <tr>
    <td class="tg-lboi">2. Добавить проект</td>
  </tr>
  <tr>
    <td class="tg-lboi">3. Проверить результат</td>
  </tr>
  <tr>
    <td class="tg-9wq8" rowspan="4">4</td>
    <td class="tg-9wq8" rowspan="4">updateProjectShouldUpdateProject</td>
    <td class="tg-9wq8" rowspan="4">Изменение данных проекта</td>
    <td class="tg-9wq8" rowspan="4">IntelliJ IDEA 2023.1<br>JUnit 5</td>
    <td class="tg-0pky">1. Сгенерировать и записать начальные данные в БД</td>
    <td class="tg-9wq8" rowspan="4">Данные проекта должны быть обновлены</td>
    <td class="tg-9wq8" rowspan="4">Данные проекта обновлены</td>
    <td class="tg-9wq8" rowspan="4">Успешно</td>
  </tr>
  <tr>
    <td class="tg-0pky">2. Создать модель с обновленными данными о проекте</td>
  </tr>
  <tr>
    <td class="tg-0pky">3. Обновить данные</td>
  </tr>
  <tr>
    <td class="tg-0pky">4. Проверить результат</td>
  </tr>
  <tr>
    <td class="tg-9wq8" rowspan="4">5</td>
    <td class="tg-9wq8" rowspan="4">getProjectsBySearchShouldFindProjects</td>
    <td class="tg-9wq8" rowspan="4">Поиск проекта по ключевому слову и статусу</td>
    <td class="tg-9wq8" rowspan="4">IntelliJ IDEA 2023.1<br>JUnit 5</td>
    <td class="tg-0pky">1. Сгенерировать и записать начальные данные в БД</td>
    <td class="tg-9wq8" rowspan="4">Должны быть получены проекты подпадающие под критерии поиска</td>
    <td class="tg-9wq8" rowspan="4">Проекты найдены, поиск выдал только соответствующие критериям поиска проекты</td>
    <td class="tg-9wq8" rowspan="4">Успешно</td>
  </tr>
  <tr>
    <td class="tg-0pky">2. Изменить наименование и статусы у нескольких из ранее созданных проектов, что бы они соответствовали ключевому слову и статусам для поиска</td>
  </tr>
  <tr>
    <td class="tg-0pky">3. Осуществить поиск</td>
  </tr>
  <tr>
    <td class="tg-0pky">4. Проверить результат</td>
  </tr>
  <tr>
    <td class="tg-9wq8" rowspan="3">6</td>
    <td class="tg-9wq8" rowspan="3">updateStatusProjectShouldUpdateStatus</td>
    <td class="tg-9wq8" rowspan="3">Изменение статуса проекта, проект переводится в новый статус, который поступил на вход</td>
    <td class="tg-9wq8" rowspan="3">IntelliJ IDEA 2023.1<br>JUnit 5</td>
    <td class="tg-0pky">1. Сгенерировать и записать начальные данные в БД</td>
    <td class="tg-9wq8" rowspan="3">Должен изменить статус проекта на заданный</td>
    <td class="tg-9wq8" rowspan="3">Значение статуса было изменено</td>
    <td class="tg-9wq8" rowspan="3">Успешно</td>
  </tr>
  <tr>
    <td class="tg-0pky">2. Задать проекту новый статус согласно условию перехода</td>
  </tr>
  <tr>
    <td class="tg-0pky">3. Проверить результат</td>
  </tr>
</tbody>
</table>