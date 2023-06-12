
# ProjectManagementSystem

### Swagger: http://localhost:8080/swagger-ui.html
* username: superuser
* password: password

### Каркас приложения **management_system**

![схема приложения](https://github.com/Gorchanyuk/ProjectManagementSystem/assets/94937271/1582521e-b471-4054-9669-a977bc5e02a4)


Приложение представленно в виде мультимодольного приложения. Базовое приложение **management_system** состоит из 6 модулей:
* **app**
* **web**
* **business**
* **dto**
* **common**
* **test**
  
## Описание модулей
Модуль **app** отвечает за общую конфигурацию приложения и настройку безопасности. 

Модуль **web** содержит контроллеры и отвечает за взаимодействие с клиентом.

Модуль **business** отвечает за бизнес логику предложения и представлен 3 модулями:
* **entity**
* **repository**
* **service**

Модуль **entity** содержит модели сущностей которые представленны в БД.

Модуль отвечает за взаимодействие с БД.

Модуль **service** содержит основную бизнес логику приложения и 

Модуль **dto** содержит классы предназначенные для передачи данных между компонентами приложения.

Модуль **common** содержит общие, для других модулей, данные.

Модуль **test** содержит тесты, для проверки приложения.

## Особенности приложения

* При первом запуске приложения происходит проверка наличия зарегестрированных пользователей, если пользователи не обнаружены, то будет создан пользователь с данными логином: superuser и паролем: password.
* Аутентификация в приложении осуществляется с пощью JWT токена.
* При регистрации и изменении данных сотрудника, происходит проверка наличия адреса электронной почты, если почта указанна, то для пользователя генерируется пароль и отправляется на указанную почту.
* При создании и изменении задач, происходит проверка наличия исполнителя задачи, если исполнитель задан и у него указан адрес электронной почты, то ему будет направленно сообщение о новой задаче.
* Отправка сообщений реализована асинхронно через RabbitMQ.
* Реализованна возможность прикреплять файла к проектам и к задачам, так же есть возможность скачать, удалить и изменить файл.
* У задач реализована возможность установки зависимостей между задачами. При установке зависимости происходит проверка циклических связей.

## Структура БД

![Схема БД](https://github.com/Gorchanyuk/ProjectManagementSystem/assets/94937271/84f43042-ae6c-4c7f-a183-21e8e859bb85)




