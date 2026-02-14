# Hotel API

RESTful API приложение для управления отелями.

## Технологии

- Java 21
- Spring Boot 3.2.3
- Spring Data JPA
- Spring Web MVC
- Liquibase
- H2 Database
- MapStruct
- Lombok
- OpenAPI/Swagger
- JUnit 5 & Mockito

## Требования

- Java 21
- Maven 3.8+

## Запуск приложения

```bash
mvn spring-boot:run

Приложение запустится на порту 8092

API Endpoints
Базовый путь: /property-view

Отели
Метод	Endpoint	Описание
GET	/hotels	Получение списка всех отелей
GET	/hotels/{id}	Получение детальной информации об отеле
POST	/hotels	Создание нового отеля
POST	/hotels/{id}/amenities	Добавление удобств к отелю
Поиск
Метод	Endpoint	Описание
GET	/search	Поиск отелей по параметрам
Параметры поиска: name, brand, city, country, amenities

Гистограммы
Метод	Endpoint	Описание
GET	/histogram/{param}	Получение статистики по отелям
Параметры: brand, city, country, amenities

Документация API
Swagger UI: http://localhost:8092/swagger-ui.html

OpenAPI JSON: http://localhost:8092/api-docs

База данных
H2 Console: http://localhost:8092/h2-console

JDBC URL: jdbc:h2:mem:hoteldb

Username: sa

Password: (пустой)

Сборка
bash
mvn clean package
Запуск тестов
bash
mvn test
Структура проекта
text
src/
├── main/
│   ├── java/com/example/hotelapi/
│   │   ├── controller/     # REST контроллеры
│   │   ├── service/         # Бизнес-логика
│   │   ├── repository/      # Работа с БД
│   │   ├── model/           # JPA сущности
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── exception/       # Обработка ошибок
│   │   ├── config/          # Конфигурация
│   │   └── HotelApiApplication.java
│   └── resources/
│       ├── application.yml
│       ├── application-dev.yml
│       └── db/changelog/    # Liquibase миграции
└── test/                    # Тесты
Переключение на другую БД
Для использования другой базы данных (MySQL, PostgreSQL) измените конфигурацию в application.yml:

yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hoteldb
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: password
  jpa:
    database-platform: org.hibernate.dialect.MySQL8Dialect
Примеры запросов
Создание отеля
bash
curl -X POST http://localhost:8092/property-view/hotels \
  -H "Content-Type: application/json" \
  -d '{
    "name": "DoubleTree by Hilton Minsk",
    "brand": "Hilton",
    "address": {
      "houseNumber": 9,
      "street": "Pobediteley Avenue",
      "city": "Minsk",
      "country": "Belarus",
      "postCode": "220004"
    },
    "contacts": {
      "phone": "+375 17 309-80-00",
      "email": "info@hilton.com"
    },
    "arrivalTime": {
      "checkIn": "14:00",
      "checkOut": "12:00"
    }
  }'
Поиск отелей
bash
curl "http://localhost:8092/property-view/search?city=Minsk&brand=Hilton"
Получение гистограммы
bash
curl http://localhost:8092/property-view/histogram/city
Лицензия
MIT

text

## Инструкция по запуску

1. Убедитесь, что установлена Java 21:
```bash
java -version
Клонируйте репозиторий:

bash
git clone <your-repo-url>
cd hotel-api
Соберите проект:

bash
mvn clean package
Запустите приложение:

bash
mvn spring-boot:run
Откройте Swagger UI для тестирования API:

text
http://localhost:8092/swagger-ui.html
Это приложение полностью соответствует всем требованиям:

Использует Java 21

RESTful API с префиксом /property-view

Все указанные методы реализованы

Использует Spring Boot, Spring JPA, Liquibase

База данных H2

Включает тесты, документацию Swagger

Разделение на слои (Controller, Service, Repository)

Паттерны проектирования (Builder, DTO, Mapper)

Легко переключается на другую БД через конфигурацию