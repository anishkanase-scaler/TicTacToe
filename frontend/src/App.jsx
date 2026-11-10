import { useEffect, useState } from 'react'
import { createGame, playMove } from './api'

// Backend statuses: IN_PROGRESS, P1 (X won), P2 (O won), DRAW
function getStatusText(game) {
  if (game.status === 'P1') return 'X wins! 🎉'
  if (game.status === 'P2') return 'O wins! 🎉'
  if (game.status === 'DRAW') return "It's a draw! 🤝"
  return `${game.currTurn}'s turn ✨`
}

// Each player gets their own colour, set in index.css.
function getCellClass(cell) {
  if (cell === 'X') return 'cell cell-x'
  if (cell === 'O') return 'cell cell-o'
  return 'cell'
}

export default function App() {
  const [game, setGame] = useState(null)
  const [error, setError] = useState('')

  async function startNewGame() {
    setError('')
    try {
      setGame(await createGame(3))
    } catch (err) {
      setError(err.message)
    }
  }

  // Start a game as soon as the page loads.
  useEffect(() => {
    startNewGame()
  }, [])

  async function handleCellClick(row, col) {
    setError('')
    try {
      setGame(await playMove(game.id, row, col, game.currTurn))
    } catch (err) {
      setError(err.message)
    }
  }

  if (!game) {
    return (
      <main className="app">
        <h1>Tic Tac Toe</h1>
        <p className="subtitle">two players · one board</p>
        {error ? <p className="error">{error}</p> : <p className="status">Loading...</p>}
        <div>
          <button className="new-game" onClick={startNewGame}>Try again</button>
        </div>
      </main>
    )
  }

  const isOver = game.status !== 'IN_PROGRESS'

  return (
    <main className="app">
      <h1>Tic Tac Toe</h1>
      <p className="subtitle">two players · one board</p>

      <div>
        <p className={isOver ? 'status status-over' : 'status'}>{getStatusText(game)}</p>
      </div>

      <div
        className="board"
        style={{ gridTemplateColumns: `repeat(${game.grid.length}, 1fr)` }}
      >
        {game.grid.map((rowString, row) =>
          [...rowString].map((cell, col) => (
            <button
              key={`${row}-${col}`}
              className={getCellClass(cell)}
              onClick={() => handleCellClick(row, col)}
              disabled={isOver || cell !== '_'}
            >
              {cell === '_' ? '' : cell}
            </button>
          ))
        )}
      </div>

      {error && <p className="error">{error}</p>}

      <div>
        <button className="new-game" onClick={startNewGame}>New Game</button>
      </div>
    </main>
  )
}
