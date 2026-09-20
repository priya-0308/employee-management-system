# Employee Management System

A Java Spring Boot 3.x project with Gradle, H2 database, Swagger, role-based authorization, and OpenShift deployment configuration.

## Run locally

Prerequisites: Java 17 or newer and Gradle.

From the project root, run:

```bash
gradle clean build
gradle bootRun
```

Alternatively, run the executable JAR after building:

```bash
java -jar build/libs/employee-management-system-0.0.1-SNAPSHOT.jar
```

The application uses `src/main/resources/application.yml` for local configuration and starts on port `8080`.

Swagger UI: <http://localhost:8080/swagger-ui/index.html>

H2 Console: <http://localhost:8080/h2-console>

H2 JDBC URL: `jdbc:h2:mem:employeedb`

## Default users

- Admin: `admin` / `admin123`
- User: `user` / `user123`

## API endpoints

- `GET /api/employees` – Get all employees
- `GET /api/employees/{id}` – Search employee by ID
- `POST /api/employees` – Create employee (ADMIN only)
- `PUT /api/employees/{id}` – Update employee (ADMIN only)

## Authorization

- `USER` can access read endpoints.
- `ADMIN` can access create and update endpoints.
- Swagger and API documentation are public.

## OpenShift deployment

Use the YAML files in the `openshift/` folder with:

```bash
oc apply -f openshift/
```
