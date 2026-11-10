// All calls to the Spring Boot backend live here.
// Relative path, so it works both in dev (Vite proxies it, see vite.config.js)
// and in Docker (Spring Boot serves the page and the API from the same port).
const BASE_URL = '/api/games'

// The backend sends { "message": "..." } when something is wrong.
async function readResponse(response) {
  const data = await response.json()
  if (!response.ok) {
    throw new Error(data.message || 'Something went wrong')
  }
  return data
}

export function createGame(size = 3) {
  return fetch(`${BASE_URL}?size=${size}`, { method: 'POST' }).then(readResponse)
}

export function getGame(gameId) {
  return fetch(`${BASE_URL}/${gameId}`).then(readResponse)
}

export function playMove(gameId, row, col, symbol) {
  return fetch(`${BASE_URL}/${gameId}/moves`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ row, col, symbol }),
  }).then(readResponse)
}
