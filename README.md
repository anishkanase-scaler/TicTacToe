# Tic Tac Toe

A simple tic tac toe game: Spring Boot REST API + React frontend.
Two players share the same board (X always starts).

## Project layout

```
src/main/java/com/example/tictactoe   Spring Boot backend
frontend/                             React app (Vite)
```

## Running it

You need two terminals.

### 1. Backend (port 8080)

```bash
./mvnw spring-boot:run
```

It uses an in-memory H2 database, so there is nothing to install or set up.
Games are lost when the server restarts. To use Postgres instead, see the
commented-out lines in `src/main/resources/application.properties`.

### 2. Frontend (port 5173)

```bash
cd frontend
npm install
npm run dev
```

Then open http://localhost:5173

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
