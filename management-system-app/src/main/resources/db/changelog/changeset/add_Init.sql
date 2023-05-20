INSERT INTO project (code, name, status)
VALUES ('project1', 'project1', 'В разработке'),
       ('project2', 'project2', 'Завершен'),
       ('project3', 'project3', 'В разработке');

INSERT INTO employee (first_name, last_name, email, job_title, status)
VALUES ('Tom', 'Wilson', 'wilson@mail.com', 'Developer', 'Активный'),
       ('Jack', 'White', 'white@mail.com', 'Tester', 'Активный'),
       ('Mike', 'Stivens', 'stiv@mail.com', 'Developer', 'Активный'),
       ('Nikolas', 'Pain', 'pain@mail.com', 'Developer', 'Удаленный'),
       ('Dan', 'Fokin', 'fokin@mail.com', 'Tester', 'Удаленный'),
       ('Petr', 'Samoilov', 'petr@mail.com', 'Tester', 'Активный'),
       ('Andrey', 'Astafev', 'astafev@mail.com', 'Developer', 'Активный');

INSERT INTO project_team(project_id, employee_id, role_employee)
VALUES (1, 1, 'Руководитель проекта'),
       (1, 2, 'Аналитик'),
       (1, 3, 'Разработчик'),
       (1, 4, 'Тестировщик'),
       (1, 5, 'Разработчик'),
       (2, 7, 'Разработчик'),
       (2, 5, 'Разработчик'),
       (2, 1, 'Аналитик'),
       (3, 7, 'Руководитель проекта'),
       (3, 6, 'Аналитик'),
       (3, 5, 'Разработчик'),
       (3, 4, 'Разработчик'),
       (3, 3, 'Разработчик'),
       (3, 2, 'Разработчик'),
       (3, 1, 'Разработчик');
