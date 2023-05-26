INSERT INTO project (code, name, status)
VALUES ('project1', 'project1', 'DEVELOP'),
       ('project2', 'project2', 'COMPLETE'),
       ('project3', 'project3', 'DEVELOP');

INSERT INTO employee (first_name, last_name, email, job_title, status, username, password)
VALUES ('Tom', 'Wilson', 'wilson@mail.com', 'Developer', 'ACTIVE', 'user1', '$2a$10$cO6U6l.pfm1VwUNfqZL19.UeqRk4VHuw2LiHvNUMJC.NGAA85NV0y'),
       ('Jack', 'White', 'white@mail.com', 'Tester', 'ACTIVE', 'user2', '$2a$10$cO6U6l.pfm1VwUNfqZL19.UeqRk4VHuw2LiHvNUMJC.NGAA85NV0y'),
       ('Mike', 'Stivens', 'stiv@mail.com', 'Developer', 'ACTIVE', 'user3', '$2a$10$cO6U6l.pfm1VwUNfqZL19.UeqRk4VHuw2LiHvNUMJC.NGAA85NV0y'),
       ('Nikolas', 'Pain', 'pain@mail.com', 'Developer', 'ACTIVE', 'user4', '$2a$10$cO6U6l.pfm1VwUNfqZL19.UeqRk4VHuw2LiHvNUMJC.NGAA85NV0y'),
       ('Dan', 'Fokin', 'fokin@mail.com', 'Tester', 'ACTIVE', 'user5', '$2a$10$cO6U6l.pfm1VwUNfqZL19.UeqRk4VHuw2LiHvNUMJC.NGAA85NV0y'),
       ('Petr', 'Samoilov', 'petr@mail.com', 'Tester', 'ACTIVE', 'user6', '$2a$10$cO6U6l.pfm1VwUNfqZL19.UeqRk4VHuw2LiHvNUMJC.NGAA85NV0y'),
       ('Andrey', 'Astafev', 'astafev@mail.com', 'Developer', 'ACTIVE', 'user7', '$2a$10$cO6U6l.pfm1VwUNfqZL19.UeqRk4VHuw2LiHvNUMJC.NGAA85NV0y');

INSERT INTO project_team(project_id, employee_id, role_employee)
VALUES (1, 1, 'PROJECT_MANAGER'),
       (1, 2, 'ANALYST'),
       (1, 3, 'DEVELOPER'),
       (1, 4, 'DEVELOPER'),
       (1, 5, 'DEVELOPER'),
       (2, 7, 'DEVELOPER'),
       (2, 5, 'DEVELOPER'),
       (2, 1, 'ANALYST'),
       (3, 7, 'PROJECT_MANAGER'),
       (3, 6, 'ANALYST'),
       (3, 5, 'DEVELOPER'),
       (3, 4, 'DEVELOPER'),
       (3, 3, 'DEVELOPER'),
       (3, 2, 'DEVELOPER'),
       (3, 1, 'DEVELOPER');
