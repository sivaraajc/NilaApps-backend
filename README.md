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
- Maven 3.9+ (optional — the project includes the Maven Wrapper)

## Run

From the project root (no global Maven install required — the wrapper downloads it automatically):

| Shell | Command |
|-------|---------|
| PowerShell | `.\mvnw.cmd spring-boot:run` |
| Command Prompt | `mvnw.cmd spring-boot:run` |
| Linux/macOS | `./mvnw spring-boot:run` |

If Maven is installed globally, `mvn spring-boot:run` also works.

Server: http://localhost:8000

On first run, seed data loads 6 components and example path `lp-sat-adaptive-001`.

## Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/components` | Content library |
| `POST` | `/api/learning-paths` | Save learning path |
| `GET` | `/api/learning-paths/{id}` | Load learning path |
| `POST` | `/api/learning-paths/{id}/evaluate` | Resolve next node (optional) |

## Tests

| Shell | Command |
|-------|---------|
| PowerShell | `.\mvnw.cmd test` |
| Command Prompt | `mvnw.cmd test` |
| Linux/macOS | `./mvnw test` |

Or with a global Maven install: `mvn test`

## H2 console (optional)

http://localhost:8000/h2-console — JDBC URL `jdbc:h2:file:./data/learningpathdb`
