# Todo App — Spring Boot REST API

A simple, clean REST API for managing a to-do list, built with Spring Boot. I made this project mainly to get comfortable with the core building blocks of Spring — controllers, services, repositories, DTOs, and dependency injection — by actually building something end to end instead of just reading theory.

It's intentionally minimal right now: no database, no authentication, no fancy validation. Just a solid, working CRUD API that follows a layered architecture the way a "real" Spring application would, so the structure is something I can keep extending later.

---

## What this project does

The app exposes a REST API to manage to-do items. You can:

- Create a new to-do
- Fetch all to-dos
- Fetch a single to-do by its ID
- Update a to-do
- Mark a to-do as complete
- Delete a to-do

Data is stored **in memory** (in a `List` inside the repository layer), so every time the application restarts, the list resets. There's no database connected yet — this was a deliberate choice to keep the focus on the Spring layering itself before adding persistence.

---

## Why I built it this way

I wanted to practice the standard layered structure that most Spring Boot backends use:

```
Controller → Service → Repository
```

- **Controller** — handles HTTP requests/responses only. No business logic lives here.
- **Service** — where the actual rules live (e.g. a to-do can't be saved with an empty title).
- **Repository** — handles storage. Right now it's an in-memory list, but because it's separated out cleanly, swapping it for a real database later (Postgres, MySQL, etc.) shouldn't require touching the controller or service much at all.
- **DTOs (Data Transfer Objects)** — I didn't want to expose my internal `Todo` entity directly to the outside world. So `CreateTodoRequest`, `UpdateTodoRequest`, and `TodoResponse` act as a boundary between "what the API accepts/returns" and "what the app stores internally."
- **Mapper** — a small utility class (`TodoMapper`) that converts between DTOs and the entity, so that conversion logic isn't scattered across controllers or services.

This is also why there's an `idGenerator` bean (an `AtomicInteger`) defined in `AppConfig` — since there's no database to auto-generate IDs for me, I needed a simple, thread-safe way to hand out incrementing IDs myself.

---

## Project structure

```
src/main/java/org/learingspring/todoapp/
│
├── config/
│   └── AppConfig.java          → defines the AtomicInteger bean used for ID generation
│
├── controller/
│   └── TodoController.java     → exposes the REST endpoints under /todos
│
├── dto/
│   ├── CreateTodoRequest.java  → shape of the data needed to create a to-do
│   ├── UpdateTodoRequest.java  → shape of the data needed to update a to-do
│   └── TodoResponse.java       → shape of the data sent back to the client
│
├── entity/
│   └── Todo.java               → the internal model representing a to-do item
│
├── repository/
│   └── TodoRepository.java     → in-memory storage and basic CRUD operations
│
├── service/
│   └── TodoService.java        → business logic (validation, ID assignment, etc.)
│
├── util/
│   └── TodoMapper.java         → converts between entity and DTOs
│
└── TodoAppApplication.java     → main entry point of the Spring Boot app
```

(Note: the base package is `org.learingspring.todoapp` — yes, "learingspring" instead of "learningspring." A small typo from early on that I've just kept consistent throughout the project rather than going back and renaming everything.)

---

## Tech stack

- **Java**
- **Spring Boot** (Web)
- **Lombok** — to cut down on boilerplate getters/setters/constructors
- **Maven** (or Gradle, depending on what you used to scaffold it) for build/dependency management

---

## API Endpoints

Base URL: `/todos`

| Method | Endpoint              | Description                          | Request Body         |
|--------|-----------------------|---------------------------------------|------------------------|
| POST   | `/todos`               | Create a new to-do                   | `{ "title": "string" }` |
| GET    | `/todos`               | Get all to-dos                       | —                      |
| GET    | `/todos/{id}`          | Get a single to-do by ID             | —                      |
| PUT    | `/todos/{id}`          | Update a to-do's title/completed status | `{ "title": "string", "completed": boolean }` |
| PATCH  | `/todos/{id}/complete` | Mark a to-do as completed            | —                      |
| DELETE | `/todos/{id}`          | Delete a to-do by ID                 | —                      |

### Example: Creating a to-do

**Request**
```http
POST /todos
Content-Type: application/json

{
  "title": "Finish Spring Boot project"
}
```

**Response**
```json
{
  "id": 1,
  "title": "Finish Spring Boot project",
  "completed": false
}
```

### Example: Marking a to-do as complete

```http
PATCH /todos/1/complete
```

Returns the updated to-do with `completed: true`.

---

## Things I'm aware of and plan to improve

Being upfront about this since it's a learning project and not a finished product:

- **No persistence** — everything is stored in an in-memory `ArrayList`, so data is lost on every restart. Adding Spring Data JPA with an actual database is the natural next step.
- **No input validation annotations yet** — e.g. `@NotBlank` on the title field. Right now, empty-title checks are done manually in the service layer.
- **Error handling is basic** — invalid requests currently throw a generic `RuntimeException` instead of returning proper HTTP status codes (like a `404` for "not found"). A global `@ControllerAdvice` exception handler is on my to-do list (pun intended).
- **`PUT /todos/{id}` always resets `completed` back to `false`**, even if the request body says otherwise — this is current behavior, not a typo in this README, and it's something I want to revisit since it could be confusing for anyone consuming the API.
- **No tests yet** — unit and integration tests (with JUnit/Mockito) are planned.
- **No authentication/authorization** — this is a fully open API right now, fine for local learning purposes only.

---

## How to run it locally

1. Clone the repo
   ```bash
   git clone <your-repo-url>
   cd todoapp
   ```

2. Run it using your build tool of choice:
   ```bash
   # Maven
   ./mvnw spring-boot:run

   # or Gradle
   ./gradlew bootRun
   ```

3. The app will start on the default port (usually `http://localhost:8080`).

4. Test the endpoints using Postman, curl, or any REST client of your choice.

---

## A note on why this project exists

This isn't meant to be a production-grade to-do app — there are a thousand of those already. It's a hands-on way for me to internalize how a Spring Boot application is actually structured in practice: how requests flow from controller to service to repository, why DTOs matter, how dependency injection ties everything together, and what a clean separation of concerns looks like in real code rather than just in a diagram.

If you're looking at this as a fellow learner — feel free to fork it, break it, and extend it. That's exactly what I plan to keep doing with it too.
