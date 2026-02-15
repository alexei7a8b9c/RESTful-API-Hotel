# Hotel API

RESTful API for hotel management built with Spring Boot 3.2.3 and Java 21.

## Technologies

- Java 21
- Spring Boot 3.2.3
- Spring Data JPA
- Spring Web
- Spring Validation
- Liquibase
- H2 Database
- MapStruct
- Lombok
- OpenAPI/Swagger
- JUnit 5 & Mockito

## Features

- Get all hotels with brief information
- Get detailed hotel information by ID
- Search hotels by name, brand, city, country, and amenities
- Create new hotels
- Add amenities to existing hotels
- Get histograms grouped by brand, city, country, or amenities
- Comprehensive validation and error handling
- API documentation with Swagger

## API Endpoints

All endpoints have the common prefix: `/property-view`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/property-view/hotels` | Get all hotels with brief information |
| GET | `/property-view/hotels/{id}` | Get detailed hotel information |
| GET | `/property-view/search` | Search hotels by parameters |
| POST | `/property-view/hotels` | Create a new hotel |
| POST | `/property-view/hotels/{id}/amenities` | Add amenities to a hotel |
| GET | `/property-view/histogram/{param}` | Get histogram grouped by parameter |

## Running the Application

### Prerequisites
- JDK 21
- Maven 3.8+

### Build and Run

```bash
# Clone the repository
git clone https://github.com/yourusername/hotel-api.git
cd hotel-api

# Build the project
mvn clean package

# Run the application
mvn spring-boot:run
The application will start on port 8092.

Access Points
Application: http://localhost:8092

Swagger UI: http://localhost:8092/swagger-ui.html

OpenAPI Docs: http://localhost:8092/api-docs

H2 Console: http://localhost:8092/h2-console

JDBC URL: jdbc:h2:mem:hoteldb

Username: sa

Password: (empty)

Testing with Swagger
Start the application

Open browser at http://localhost:8092/swagger-ui.html

Explore available endpoints

Click "Try it out" on any endpoint

Fill required parameters and execute

Swagger Examples
Create a new hotel:

Endpoint: POST /property-view/hotels

Request body:

json
{
  "name": "DoubleTree by Hilton Minsk",
  "description": "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital",
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
    "email": "doubletreeminsk.info@hilton.com"
  },
  "arrivalTime": {
    "checkIn": "14:00",
    "checkOut": "12:00"
  }
}
Testing with Postman
Collection Setup
Create a new collection in Postman named "Hotel API" and add the following requests:

1. Get All Hotels
Method: GET

URL: http://localhost:8092/property-view/hotels

2. Get Hotel by ID
Method: GET

URL: http://localhost:8092/property-view/hotels/1

3. Search Hotels
Method: GET

URL: http://localhost:8092/property-view/search?city=Minsk&brand=Hilton

Query Parameters:

name (optional)

brand (optional)

city (optional)

country (optional)

amenities (optional, can be multiple: amenities=WiFi,parking)

4. Create Hotel
Method: POST

URL: http://localhost:8092/property-view/hotels

Headers: Content-Type: application/json

Body: raw JSON

Example 1: Complete hotel with all fields

json
{
  "name": "The Ritz-Carlton Moscow",
  "description": "Luxury hotel in the heart of Moscow with views of Red Square",
  "brand": "Ritz-Carlton",
  "address": {
    "houseNumber": 3,
    "street": "Tverskaya Street",
    "city": "Moscow",
    "country": "Russia",
    "postCode": "125009"
  },
  "contacts": {
    "phone": "+7 495 225-80-00",
    "email": "moscow@ritzcarlton.com"
  },
  "arrivalTime": {
    "checkIn": "15:00",
    "checkOut": "12:00"
  }
}
Example 2: Hotel with optional fields omitted (description and checkOut)

json
{
  "name": "Park Hyatt Tokyo",
  "brand": "Hyatt",
  "address": {
    "houseNumber": 3,
    "street": "Nishishinjuku, Shinjuku City",
    "city": "Tokyo",
    "country": "Japan",
    "postCode": "163-1055"
  },
  "contacts": {
    "phone": "+81 3-5322-1234",
    "email": "tokyo.park@hyatt.com"
  },
  "arrivalTime": {
    "checkIn": "15:00"
  }
}
Example 3: Budget hotel

json
{
  "name": "Ibis Budget Paris",
  "description": "Affordable accommodation near Paris city center",
  "brand": "Ibis",
  "address": {
    "houseNumber": 15,
    "street": "Rue de Dunkerque",
    "city": "Paris",
    "country": "France",
    "postCode": "75010"
  },
  "contacts": {
    "phone": "+33 1 42 85 07 97",
    "email": "h1234@accor.com"
  },
  "arrivalTime": {
    "checkIn": "14:00"
  }
}
5. Add Amenities to Hotel
Method: POST

URL: http://localhost:8092/property-view/hotels/1/amenities

Headers: Content-Type: application/json

Body: raw JSON

json
[
  "Free parking",
  "Free WiFi",
  "Non-smoking rooms",
  "Fitness center",
  "Room service",
  "Business center",
  "Meeting rooms"
]
6. Get Histogram
Method: GET

URL: http://localhost:8092/property-view/histogram/city

Valid parameters: brand, city, country, amenities

Example response for /property-view/histogram/city:

json
{
  "Minsk": 1,
  "Moscow": 2,
  "Tokyo": 1,
  "Paris": 1
}
Example response for /property-view/histogram/amenities:

json
{
  "Free parking": 2,
  "Free WiFi": 3,
  "Non-smoking rooms": 2,
  "Fitness center": 1,
  "Room service": 1
}
Database Schema
The application uses H2 in-memory database with the following tables:

hotels - main hotel information

amenities - available amenities

hotel_amenities - many-to-many relationship between hotels and amenities

Configuration
Application Properties
Main configuration in application.yml:

Server port: 8092

Database: H2 in-memory

JPA: Hibernate with SQL logging

Liquibase: Enabled for schema management

Switching to Another Database
To switch from H2 to MySQL/PostgreSQL:

Add the appropriate dependency in pom.xml

Update application.yml:

For MySQL:

yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hoteldb
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: password
  jpa:
    database-platform: org.hibernate.dialect.MySQL8Dialect
For PostgreSQL:

yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/hoteldb
    driver-class-name: org.postgresql.Driver
    username: postgres
    password: password
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
Testing
Run tests with Maven:

bash
mvn test
The project includes:

Unit tests for services

Web layer tests for controllers

Integration tests (to be added)

Error Handling
The API returns consistent error responses:

json
{
  "timestamp": "2026-02-15T13:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Hotel not found with id: 999",
  "path": "/property-view/hotels/999"
}
Validation errors:

json
{
  "timestamp": "2026-02-15T13:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "errors": {
    "name": "Hotel name is required",
    "brand": "Brand is required"
  },
  "path": "/property-view/hotels"
}