INSERT INTO project (code, name, status)
VALUES ('project1', 'project1', 'В разработке'),
       ('project2', 'project2', 'Завершен'),
       ('project3', 'project3', 'В разработке');

INSERT INTO employee (first_name, last_name, email, job_title, status, username, password)
VALUES ('Tom', 'Wilson', 'wilson@mail.com', 'Developer', 'Активный', 'user1', 'password'),
       ('Jack', 'White', 'white@mail.com', 'Tester', 'Активный', 'user2', 'password'),
       ('Mike', 'Stivens', 'stiv@mail.com', 'Developer', 'Активный', 'user3', 'password'),
       ('Nikolas', 'Pain', 'pain@mail.com', 'Developer', 'Удаленный', 'user4', 'password'),
       ('Dan', 'Fokin', 'fokin@mail.com', 'Tester', 'Удаленный', 'user5', 'password'),
       ('Petr', 'Samoilov', 'petr@mail.com', 'Tester', 'Активный', 'user6', 'password'),
       ('Andrey', 'Astafev', 'astafev@mail.com', 'Developer', 'Активный', 'user7', 'password');

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
