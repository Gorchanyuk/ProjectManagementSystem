
# ProjectManagementSystem

### Каркас приложения **management_system**

![каркас приложения](https://user-images.githubusercontent.com/94937271/236784003-783596bf-cd83-41bd-8fc7-5d262a3c8264.jpg)

Приложение представленно в виде мультимодольного приложения. Базовое приложение **management_system** состоит из 4 модулей:
* **app**
* **business**
* **dto**
* **exception**

В свою очередь модуль **business** состоит из 3 модулей:
* **entity**
* **repository**
* **service**

## Обоснование выбора данной структуры приложения

Модуль **app** содержит пакеты и классы которые необходимы данному приложению и врядли будут использоваться в других приложениях. 
Создание данного модуля обусловленно принципами работы мультимодульных приложений. При создании пакетов из этого модуля в корне
базового приложения (**management_system**)  не получится скомпилировать данное приложение без изменения настроек IDE.

Модули **dto** и **exception** в случае расширения приложения и превращения его в микросервисное приложение могут использоваться в 
других сервисах, поэтому эти пакеты было решено вынести в отдельные модули.  

Модуль **business** со всеми входящими модулями было решено вынести в отдельный модуль в учебных целях, что бы лучше познакомиться
с мультимодульной архитектурой приложений.  
  
## Описание модулей
Модуль **app** включает пакеты **config** и **controller**. 
* Пакет **config** содержит классы необходимые для конфигурации приложения. Например класс **SecurityConfiguration** 
предназначен для конфигурации Spring Boot Security, который будет подключен в будущем.
* Пакет **controller** содержит классы предназначенные для обработки запросов и взаимодействия с клиентом.

Модуль **entity** находится в модуле **business** и включает в себя пакеты **entity**, **enumerate** и **converter**
* Пакет **entity** содержит классы сущностей связанных с базой данных.
* Пакет **enumerate** содержит классы стандарных перечислений, которые используются сущностями.
* Пакет **converter** содержит классы конверторов, для преобразования полей объекта в значение базы данных и наоборот.

Модуль **repository** находится в модуле **business** и включает в себя пакеты **repository** и **dao**
* Пакет **repository** содержит интерфейсы необходимые для обращения к БД посредством SpringBootJPA.
* Пакет **dao** предназначен для классов в которых будут прописанны свои запросы к БД, которые не возможно осуществить
с помощью SpringBootJPA.

Модуль **service** находится в модуле **business** и включает в себя классы занимающиеся бизнес логикой приложения.

Модуль **dto** содержит классы предназначенные для передачи данных между компонентами приложения.

Модуль **exception** содержит собственные классы исключений наследуемые от **RuntimeException**.

# Блоки задач

* Определить используемые фреймворки и билиотеки для реализации даннного приложения
* Определить структуру БД
* Определить какие API будет реализовывать приложение
* Реализовать бизнес логику приложения

