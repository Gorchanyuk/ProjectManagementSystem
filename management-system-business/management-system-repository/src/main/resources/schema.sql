CREATE TABLE Project
(
    id          BIGSERIAL PRIMARY KEY,
    code        VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    name        VARCHAR(50)  NOT NULL,
    status      VARCHAR(50)  NOT NULL
);

create table Employee
(
    id         BIGSERIAL PRIMARY KEY,
    email      VARCHAR(50),
    first_name VARCHAR(50) NOT NULL,
    job_title  VARCHAR(100),
    last_name  VARCHAR(50) NOT NULL,
    status     VARCHAR(50) NOT NULL,
    surname    VARCHAR(50),
    username   VARCHAR(50)
);

CREATE TABLE Project_Team
(
    employee_id   BIGINT      NOT NULL REFERENCES Employee ON DELETE CASCADE,
    project_id    BIGINT      NOT NULL REFERENCES Project ON DELETE CASCADE,
    role_employee VARCHAR(50) NOT NULL,
    PRIMARY KEY (employee_id, project_id)
);

CREATE TABLE Task
(
    id              bigserial PRIMARY KEY,
    date_of_created TIMESTAMP(6) NOT NULL,
    date_of_update  TIMESTAMP(6) NOT NULL,
    deadline        TIMESTAMP(6) NOT NULL,
    description     TEXT,
    execution_time  INTEGER      NOT NULL,
    name            VARCHAR(255) NOT NULL,
    status          VARCHAR(50)  NOT NULL,
    author          BIGINT REFERENCES Employee ON DELETE SET NULL,
    project_id      BIGINT REFERENCES Project ON DELETE CASCADE,
    performer       BIGINT REFERENCES Employee ON DELETE SET NULL
);

INSERT INTO Project (code, name, status)
VALUES ('project1', 'project1', 'В разработке'),
       ('project2', 'project2', 'Завершен'),
       ('project3', 'project3', 'В разработке');

INSERT INTO Employee (first_name, last_name, email, job_title, status)
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
