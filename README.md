# JWT Auth Full-Stack Demo

A full-stack example application demonstrating JWT-based authentication and authorization.

- **Frontend**: Angular 21 with standalone components, reactive forms, route guards and an HTTP interceptor
- **Backend**: Spring Boot 3.3 (Java 17) with Spring Security, Spring Data JPA and JJWT

## Architecture

```
frontend/   Angular single-page application (dev server on :4200)
backend/    Spring Boot REST API with JWT auth (server on :8080)
```

The Angular dev server proxies all `/api` requests to the backend on `http://localhost:8080`
(see `frontend/proxy.conf.json`), so no CORS issues occur during local development.

## Features

- JWT token issue on login (`/api/auth/login`) with role-based payload
- Stateless, JWT-filtered Spring Security configuration
- Password storage using BCrypt
- Protected dashboard endpoint (`/api/dashboard/data`)
- Angular route guard redirecting unauthenticated users to the login page
- Angular HTTP interceptor attaching the `Authorization: Bearer <token>` header
- Demo users seeded automatically at startup (H2 in-memory database)

## Tech Stack

| Layer     | Technology                                              |
| --------- | ------------------------------------------------------- |
| Frontend  | Angular 21, TypeScript, RxJS, Angular Router & Forms    |
| Backend   | Spring Boot 3.3.5, Java 17, Spring Security, Spring Data JPA |
| Security  | JJWT 0.12.6, BCrypt                                     |
| Database  | H2 (in-memory)                                          |

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.6+
- Node.js 18+ and npm

### 1. Start the backend

```bash
cd backend
mvn spring-boot:run
```

The API is available at `http://localhost:8080` and the H2 console at `http://localhost:8080/h2-console`.

### 2. Start the frontend

```bash
cd frontend
npm install
npm start
```

Open `http://localhost:4200` in your browser.

### Demo accounts

| Username | Password | Role  |
| -------- | -------- | ----- |
| `admin`  | `admin123` | ADMIN |
| `user`   | `user123`  | USER  |

## API Endpoints

| Method | Path                 | Auth Required | Description                    |
| ------ | -------------------- | ------------- | ------------------------------ |
| POST   | `/api/auth/login`    | No            | Authenticate and return a JWT  |
| GET    | `/api/dashboard/data`| Yes           | Return protected dashboard data |

### Example login request

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

### Example protected request

```bash
curl http://localhost:8080/api/dashboard/data \
  -H "Authorization: Bearer <your-jwt-token>"
```

## Configuration

Backend settings live in `backend/src/main/resources/application.properties`:

- `server.port` — backend port (default `8080`)
- `app.jwt.secret` — signing secret (must be changed in production)
- `app.jwt.expiration-ms` — token lifetime in milliseconds (default 24h)

## Project Structure

```
backend/
  src/main/java/com/example/authbackend/
    config/          Spring Security config, demo user seeding
    controller/      Auth and Dashboard REST controllers
    dto/             Request/response DTOs
    entity/          JPA entity (User)
    repository/      Spring Data repository
    security/        JWT filter and token service
    service/         Authentication and user logic

frontend/
  src/app/
    login/           Login page with reactive form
    dashboard/       Protected dashboard page
    navbar/          Top navigation bar
    guards/          Route guard for protected pages
    services/        AuthService and JWT interceptor
```

## Notes

- The H2 database is in-memory, so all data is lost when the backend stops. Demo users are re-seeded on every start.
- In production, change `app.jwt.secret` to a long random value and replace the demo accounts.
