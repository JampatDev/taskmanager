# TaskManager API 

&#x20;

**TaskManager API** is a sample backend application built with Spring Boot to demonstrate:

- JWT-based authentication and registration flows  
- Secure, rate-limited endpoints with Spring Security  
- A full CRUD lifecycle for user-scoped “Task” entities  
- Best practices in validation, exception handling, and JPA modeling  

---

## Key Features

1. **User Management**
   - `POST /api/auth/register` — public user sign‑up
   - `POST /api/auth/login` — email/password -> JWT token
2. **Task Management**
   - `POST /api/tasks` — create a task (title, description, dueDate, status)
   - `GET  /api/tasks` — list your tasks
   - `GET  /api/tasks/{id}` — retrieve a specific task
   - `GET  /api/tasks/status/{status}` — filter by status (`TODO`, `IN_PROGRESS`, `DONE`)
   - `PUT  /api/tasks/{id}` — update title/description/dueDate/status
   - `DELETE /api/tasks/{id}` — delete a task
3. **Security & Resilience**
   - JWT‐based stateless auth with token validation
   - Endpoint rate limiting (e.g. registration/login)
   - Field validation (`@NotBlank`, `@Size`, `@Pattern`, `@Future`)

---

## Tech Stack

- **Java 21**
- **Spring Boot 3.4.4** (Web, Data JPA, Security, Validation)
- **Hibernate** (JPA implementation)
- **PostgreSQL** (SQL92‑compliant)
- **Maven** build & dependency management
- **Lombok** for boilerplate reduction

---

## Prerequisites

- **JDK 17+** installed on your machine
- **Maven** 3.6+
- **PostgreSQL** 16 running locally
- **(Optional)** Postman for testing

---

## Quick Start

1. **Clone the repo**

   ```bash
   git clone https://github.com/JampatDev/taskmanager.git
   cd taskmanager
   ```

2. **Configure database**

   - Create a database named `taskmanagerdb` in PostgreSQL
   - In `src/main/resources/application.properties`, update:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/taskmanagerdb
     spring.datasource.username=postgres
     spring.datasource.password=yourpassword

     # JWT secret (at least 32 chars)
     jwt.secret=your_jwt_secret_here
     jwt.expiration=3600000   # in milliseconds
     ```

3. **Build & Run**

   ```bash
   mvn clean package
   mvn spring-boot:run
   ```

4. **Access API**

   - Base URL: `http://localhost:8080`
   - Try **register** & **login** to get your JWT
   - Include `Authorization: Bearer <token>` header on all `/api/tasks` calls

---

##  API Endpoints

### Authentication

| Method | Path                 | Description               |
| ------ | -------------------- | ------------------------- |
| POST   | `/api/auth/register` | Register new user         |
| POST   | `/api/auth/login`    | Login & receive JWT token |

### Tasks (JWT Required)

| Method | Path                         | Description                                |
| ------ | ---------------------------- | ------------------------------------------ |
| POST   | `/api/tasks`                 | Create a new task                          |
| GET    | `/api/tasks`                 | List all your tasks                        |
| GET    | `/api/tasks/{id}`            | Get a single task by ID                    |
| GET    | `/api/tasks/status/{status}` | Filter tasks by status                     |
| PUT    | `/api/tasks/{id}`            | Update title, description, dueDate, status |
| DELETE | `/api/tasks/{id}`            | Delete a task                              |


---








