<style type="text/css">
.tg .tg-9wq8{border-color:inherit;text-align:center;vertical-align:middle}
.tg .tg-c3ow{border-color:inherit;text-align:center;vertical-align:top}
.tg .tg-0pky{border-color:inherit;text-align:left;vertical-align:top}
.tg .tg-0lax{text-align:left;vertical-align:top}
</style>
<table class="tg" style="table-layout: fixed; width: 1600px">
<colgroup>
<col style="width: 25px">
<col style="width: 268px">
<col style="width: 253px">
<col style="width: 140px">
<col style="width: 388px">
<col style="width: 243px">
<col style="width: 214px">
<col style="width: 69px">
</colgroup>
<thead>
  <tr>
    <th class="tg-9wq8">id</th>
    <th class="tg-9wq8">Название</th>
    <th class="tg-9wq8">Описание</th>
    <th class="tg-9wq8">Окружающая среда</th>
    <th class="tg-c3ow">Шаги</th>
    <th class="tg-9wq8">Ожидаемый результат</th>
    <th class="tg-9wq8">Фактический результат</th>
    <th class="tg-9wq8">Статус</th>
  </tr>
</thead>
<tbody>
  <tr>
    <td class="tg-9wq8" rowspan="4">1</td>
    <td class="tg-9wq8" rowspan="4">createTaskShouldCreateNewTask</td>
    <td class="tg-9wq8" rowspan="4">Добавление новой задачи в БД</td>
    <td class="tg-9wq8" rowspan="4">IntelliJ IDEA 2023.1<br>JUnit 5</td>
    <td class="tg-0pky">1. Сгенерировать и записать начальные данные в БД</td>
    <td class="tg-9wq8" rowspan="4">В БД должна быть добавлена новая задача</td>
    <td class="tg-9wq8" rowspan="4">Задача была добавлена в БД</td>
    <td class="tg-9wq8" rowspan="4">Успешно</td>
  </tr>
  <tr>
    <td class="tg-0pky">2. Создать модель задачи</td>
  </tr>
  <tr>
    <td class="tg-0pky">3. Добавить задачу в БД</td>
  </tr>
  <tr>
    <td class="tg-0pky">4. Проверить результат</td>
  </tr>
  <tr>
    <td class="tg-9wq8" rowspan="4">2</td>
    <td class="tg-9wq8" rowspan="4">updateTaskShouldUpdateTask</td>
    <td class="tg-9wq8" rowspan="4">Изменение данных задачи</td>
    <td class="tg-9wq8" rowspan="4">IntelliJ IDEA 2023.1<br>JUnit 5<br></td>
    <td class="tg-0pky">1. Сгенерировать и записать начальные данные в БД</td>
    <td class="tg-9wq8" rowspan="4">Данные задачи должны быть обновлены</td>
    <td class="tg-9wq8" rowspan="4">Данные задачи обновлены</td>
    <td class="tg-9wq8" rowspan="4">Успешно</td>
  </tr>
  <tr>
    <td class="tg-0pky">2. Создать модель с обновленными данными о задаче</td>
  </tr>
  <tr>
    <td class="tg-0pky">3. Обновить данные</td>
  </tr>
  <tr>
    <td class="tg-0pky">4. Проверить результат</td>
  </tr>
  <tr>
    <td class="tg-9wq8" rowspan="3">3</td>
    <td class="tg-9wq8" rowspan="3">updateStatusTaskShouldUpdateStatus</td>
    <td class="tg-9wq8" rowspan="3">Изменение статуса задачи, задача переводится в новый статус, который поступил на вход</td>
    <td class="tg-9wq8" rowspan="3">IntelliJ IDEA 2023.1<br>JUnit 5 </td>
    <td class="tg-0pky">1. Сгенерировать и записать начальные данные в БД</td>
    <td class="tg-9wq8" rowspan="3">Должен изменить статус задачи на заданный</td>
    <td class="tg-9wq8" rowspan="3">Значение статуса было изменено</td>
    <td class="tg-9wq8" rowspan="3">Успешно</td>
  </tr>
  <tr>
    <td class="tg-0pky">2. Задать задаче новый статус согласно условию перехода</td>
  </tr>
  <tr>
    <td class="tg-0pky">3. Проверить результат</td>
  </tr>
  <tr>
    <td class="tg-9wq8" rowspan="5">4</td>
    <td class="tg-9wq8" rowspan="5">getTasksWithFilterShouldGetTasks</td>
    <td class="tg-9wq8" rowspan="5">Поиск задачи по модели фильтра</td>
    <td class="tg-9wq8" rowspan="5">IntelliJ IDEA 2023.1<br>JUnit 5</td>
    <td class="tg-0pky">1. Сгенерировать и записать начальные данные в БД</td>
    <td class="tg-9wq8" rowspan="5">Должны быть получены задачи подпадающие под критерии поиска</td>
    <td class="tg-9wq8" rowspan="5">задачи найдены, поиск выдал только соответствующие критериям поиска задачи</td>
    <td class="tg-9wq8" rowspan="5">Успешно</td>
  </tr>
  <tr>
    <td class="tg-0pky">2. Изменить данные у нескольких из ранее созданных проектов, что бы они соответствовали модели фильтру</td>
  </tr>
  <tr>
    <td class="tg-0pky">3. Создать модель фильтра</td>
  </tr>
  <tr>
    <td class="tg-0pky">4. Осуществить поиск задач</td>
  </tr>
  <tr>
    <td class="tg-0lax">5. Проверить результат</td>
  </tr>
</tbody>
</table>