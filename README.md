# Employee Management System

A Java Spring Boot 3.x project with Gradle, H2 database, Swagger, role-based authorization, and OpenShift deployment configuration.

## Features

- Employee CRUD APIs
- Search employee by ID
- H2 in-memory database
- Spring Security authentication and authorization
- Swagger/OpenAPI documentation
- JUnit 5 and Mockito test cases
- OpenShift deployment manifests

## Project structure

- `src/main/java/com/priya/employeemanagement` - Application source code
- `src/test/java` - JUnit test classes
- `src/main/resources/application.properties` - Application configuration
- `openshift/` - OpenShift deployment YAML files

## Run locally

1. Open a terminal in the project root.
2. Run:

```bash
gradle clean build
java -jar build/libs/employee-management-system-0.0.1-SNAPSHOT.jar
```

3. Open Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

4. H2 Console:

```text
http://localhost:8080/h2-console
```

## Default users

- Admin:
  - username: `admin`
  - password: `admin123`
- User:
  - username: `user`
  - password: `user123`

## API endpoints

- `GET /api/employees` – Get all employees
- `GET /api/employees/{id}` – Get employee by ID
- `POST /api/employees` – Create employee (ADMIN only)
- `PUT /api/employees/{id}` – Update employee (ADMIN only)

## Authorization rules

- `USER` can access read endpoints
- `ADMIN` can access create and update endpoints
- Swagger and API docs are public

## OpenShift deployment

Use the YAML files in the `openshift/` folder with `oc apply -f openshift/`.

Example:

```bash
oc new-project employee-management
oc apply -f openshift/
```
