<table style="table-layout: fixed; width: 1600px">
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
    <td rowspan="4">1</td>
    <td rowspan="4">createTaskShouldCreateNewTask</td>
    <td rowspan="4">Добавление новой задачи в БД</td>
    <td rowspan="4">IntelliJ IDEA 2023.1<br>JUnit 5</td>
    <td>1. Сгенерировать и записать начальные данные в БД</td>
    <td rowspan="4">В БД должна быть добавлена новая задача</td>
    <td rowspan="4">Задача была добавлена в БД</td>
    <td rowspan="4">Успешно</td>
  </tr>
  <tr>
    <td>2. Создать модель задачи</td>
  </tr>
  <tr>
    <td>3. Добавить задачу в БД</td>
  </tr>
  <tr>
    <td>4. Проверить результат</td>
  </tr>
  <tr>
    <td rowspan="4">2</td>
    <td rowspan="4">updateTaskShouldUpdateTask</td>
    <td rowspan="4">Изменение данных задачи</td>
    <td rowspan="4">IntelliJ IDEA 2023.1<br>JUnit 5<br></td>
    <td>1. Сгенерировать и записать начальные данные в БД</td>
    <td rowspan="4">Данные задачи должны быть обновлены</td>
    <td rowspan="4">Данные задачи обновлены</td>
    <td rowspan="4">Успешно</td>
  </tr>
  <tr>
    <td>2. Создать модель с обновленными данными о задаче</td>
  </tr>
  <tr>
    <td>3. Обновить данные</td>
  </tr>
  <tr>
    <td>4. Проверить результат</td>
  </tr>
  <tr>
    <td rowspan="3">3</td>
    <td rowspan="3">updateStatusTaskShouldUpdateStatus</td>
    <td rowspan="3">Изменение статуса задачи, задача переводится в новый статус, который поступил на вход</td>
    <td rowspan="3">IntelliJ IDEA 2023.1<br>JUnit 5 </td>
    <td>1. Сгенерировать и записать начальные данные в БД</td>
    <td rowspan="3">Должен изменить статус задачи на заданный</td>
    <td rowspan="3">Значение статуса было изменено</td>
    <td rowspan="3">Успешно</td>
  </tr>
  <tr>
    <td>2. Задать задаче новый статус согласно условию перехода</td>
  </tr>
  <tr>
    <td>3. Проверить результат</td>
  </tr>
  <tr>
    <td rowspan="5">4</td>
    <td rowspan="5">getTasksWithFilterShouldGetTasks</td>
    <td rowspan="5">Поиск задачи по модели фильтра</td>
    <td rowspan="5">IntelliJ IDEA 2023.1<br>JUnit 5</td>
    <td>1. Сгенерировать и записать начальные данные в БД</td>
    <td rowspan="5">Должны быть получены задачи подпадающие под критерии поиска</td>
    <td rowspan="5">задачи найдены, поиск выдал только соответствующие критериям поиска задачи</td>
    <td rowspan="5">Успешно</td>
  </tr>
  <tr>
    <td>2. Изменить данные у нескольких из ранее созданных проектов, что бы они соответствовали модели фильтру</td>
  </tr>
  <tr>
    <td>3. Создать модель фильтра</td>
  </tr>
  <tr>
    <td>4. Осуществить поиск задач</td>
  </tr>
  <tr>
    <td>5. Проверить результат</td>
  </tr>
</tbody>
</table>