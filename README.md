# NilaApps — Adaptive Learning Path API

Spring Boot backend for the Adaptive Learning Path Builder assessment.

**Frontend:** [NilaApps-frontend](https://github.com/sivaraajc/NilaApps-frontend)

## Stack

- Java 17, Spring Boot 3.2, Spring Data JPA
- H2 file database (`data/`)

## API contract

JSON schemas live in [`schemas/`](schemas/):

| Schema | Endpoint |
|--------|----------|
| `available-content.schema.json` | `GET /api/components` |
| `learning-path.schema.json` | `POST` / `GET /api/learning-paths` |

## Prerequisites

- JDK 17+

## Run

```powershell
.\run-backend.ps1
```

Or:

```PowerShell
.\mvnw.cmd spring-boot:run
Command Prompt
mvnw.cmd spring-boot:run
Linux/macOS
./mvnw spring-boot:run
```
Server: http://localhost:8080

On first run, seed data loads 6 components and example path `lp-sat-adaptive-001`.

## Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/components` | Content library |
| `POST` | `/api/learning-paths` | Save learning path |
| `GET` | `/api/learning-paths/{id}` | Load learning path |
| `POST` | `/api/learning-paths/{id}/evaluate` | Resolve next node (optional) |

## Tests

```powershell
.\mvnw.cmd test
```

## H2 console (optional)

http://localhost:8080/h2-console — JDBC URL `jdbc:h2:file:./data/learningpathdb`
