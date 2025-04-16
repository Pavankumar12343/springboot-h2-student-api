# Spring Boot - H2 Student API

A simple RESTful API built with Spring Boot that allows you to perform CRUD operations on student records and dynamically calculate a grade based on `marksObtained` and `admissionDate`.

---

##  How to Build and Run the Application

### Prerequisites

- Java 17+
- Maven 3.6+
- (Optional) Eclipse/IntelliJ for development

### Build

Run the following command from the project root:

```bash
mvn clean install

Different API calls:

Create Student:

POST /students
Content-Type: application/json

{
  "name": "Alice",
  "email": "alice@example.com",
  "marksObtained": 85,
  "admissionDate": "2024-12-01"
}

Get student details by ID:

GET /students/{id}

{
  "id": 1,
  "name": "Alice",
  "email": "alice@example.com",
  "marksObtained": 85,
  "admissionDate": "2024-12-01",
  "grade": "Merit"
}
Grade is calculated on the fly and not stored in the database.

Update student details: 

PUT /students/{id}
Content-Type: application/json

{
  "name": "Alice Smith",
  "email": "alice.smith@example.com",
  "marksObtained": 91,
  "admissionDate": "2024-12-01"
}

Delete student:
DELETE /students/{id}


The grade is dynamically calculated using the following rules:

Pass: marksObtained > 40

Merit: marksObtained >= 80 && < 90 and admissionDate within the last 6 months

Platinum: marksObtained >= 90 and admissionDate within the last 6 months

The grade is not stored in the database.

Accessing the H2 Database Console
The H2 database is in-memory and resets on each application restart.

URL:
http://localhost:8080/h2-console


Ensure H2 console is enabled in application.properties:

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.datasource.url=jdbc:h2:mem:testdb


Assumptions Made

email is validated using the @Email annotation.

Grade is not stored in the database; it’s calculated on-the-fly in the response.

admissionDate is assumed to be in yyyy-MM-dd format and in the past/future as required.

No authentication or authorization is implemented.

Application uses in-memory H2 DB; data is volatile and resets on restart.


Swagger UI :

http://localhost:8080/swagger-ui/index.html

Author:
Pavankumar12343
GitHub Repo