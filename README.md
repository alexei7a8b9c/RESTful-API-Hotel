Hotel API
RESTful API application for hotel management.

Technologies
Java 21

Spring Boot 3.2.3

Spring Data JPA

Spring Web MVC

Liquibase

H2 Database

MapStruct

Lombok

OpenAPI/Swagger

JUnit 5 & Mockito

Requirements
Java 21

Maven 3.8+

Running the Application
bash
mvn spring-boot:run
The application will start on port 8092

API Endpoints
Base path: /property-view

Hotels
Method	Endpoint	Description
GET	/hotels	Get list of all hotels
GET	/hotels/{id}	Get detailed hotel information
POST	/hotels	Create a new hotel
POST	/hotels/{id}/amenities	Add amenities to a hotel
Search
Method	Endpoint	Description
GET	/search	Search hotels by parameters
Search parameters: name, brand, city, country, amenities

Histograms
Method	Endpoint	Description
GET	/histogram/{param}	Get hotel statistics
Parameters: brand, city, country, amenities

API Documentation
Swagger UI: http://localhost:8092/swagger-ui.html

OpenAPI JSON: http://localhost:8092/api-docs

Database
H2 Console: http://localhost:8092/h2-console

JDBC URL: jdbc:h2:mem:hoteldb

Username: sa

Password: (empty)

Build
bash
mvn clean package
Running Tests
bash
mvn test