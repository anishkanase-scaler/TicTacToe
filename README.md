# Tic Tac Toe

A simple tic tac toe game: Spring Boot REST API + React frontend.
Two players share the same board (X always starts).

## Project layout

```
src/main/java/com/example/tictactoe   Spring Boot backend
frontend/                             React app (Vite)
```

## Running it

Run both at once with `./run.sh`, or start them separately in two terminals.

### 1. Backend (port 8080)

```bash
./mvnw spring-boot:run
```

It uses an in-memory H2 database, so there is nothing to install or set up.
Games are lost when the server restarts. To use Postgres instead, see the
commented-out lines in `src/main/resources/application.properties`.

### 2. Frontend (port 3000)

```bash
cd frontend
npm install
npm run dev
```

Then open http://localhost:3000

## Running with Docker

One image contains everything: the API and the React page, both on port 8080.

```bash
docker build -t tictactoe .
docker run -p 8080:8080 tictactoe
```

Then open http://localhost:8080

The build has three stages. The first builds the React app with Node, the second
builds the jar with Maven, and the third keeps only the jar on a small Java
runtime image. The React build is copied into `src/main/resources/static` before
packaging, so it ends up inside the jar and Spring Boot serves it from the
classpath.

## API

| Method | Path                          | What it does                        |
| ------ | ----------------------------- | ----------------------------------- |
| POST   | `/api/games?size=3`           | Creates a new game                  |
| GET    | `/api/games/{gameId}`         | Returns the current game            |
| POST   | `/api/games/{gameId}/moves`   | Plays a move                        |

Move request body:

```json
{ "row": 0, "col": 0, "symbol": "X" }
```

Game response:

```json
{
  "id": "f8b243fb-a978-439d-a05f-e472eb079c97",
  "grid": ["___", "___", "___"],
  "currTurn": "X",
  "status": "IN_PROGRESS"
}
```

`status` is one of `IN_PROGRESS`, `P1` (X won), `P2` (O won) or `DRAW`.
Invalid moves come back as `{ "message": "..." }` with a 4xx status.
