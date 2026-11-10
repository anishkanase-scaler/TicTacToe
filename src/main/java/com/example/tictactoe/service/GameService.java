package com.example.tictactoe.service;

import com.example.tictactoe.dto.GameRequestDTO;
import com.example.tictactoe.dto.GameResponseDTO;
import com.example.tictactoe.enums.GameStatus;
import com.example.tictactoe.model.Game;
import com.example.tictactoe.repository.GameRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Transactional
    public GameResponseDTO createGame(UUID player, int size) {
        Game game = new Game(player, size);
        game = gameRepository.save(game);
        return GameResponseDTO.fromEntity(game);
    }

    @Transactional
    public GameResponseDTO getGame(UUID gameId)  {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new IllegalArgumentException("Game not found"));
        return GameResponseDTO.fromEntity(game);
    }

    @Transactional
    public GameResponseDTO playMove(UUID gameId, int row, int col, char symbol) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new IllegalArgumentException("Game not found"));
        GameStatus gameStatus = game.getGameStatus();
        if(gameStatus != GameStatus.IN_PROGRESS) {
            throw new IllegalStateException("Game already over");
        }
        if(game.getCurrTurn() != symbol) {
            throw new IllegalArgumentException("It is not "+symbol+ " 's turn");
        }
        char grid[][] = game.getGrid();
        int boardSize = game.getBoardSize();
        if(row < 0 || row >= boardSize || col < 0 || col >= boardSize) {
            throw new IllegalArgumentException("Out of bounds");
        }
        if(grid[row][col] != '_') {
            throw new IllegalArgumentException("Cell is already occupied");
        }
        grid[row][col] = symbol;
        game.setGrid(grid);

        if(checkWin(grid, row, col, symbol)) {
            game.setGameStatus(symbol == 'X' ? GameStatus.P1 : GameStatus.P2);
        }
        else {
            String str = game.getBoardState();
            boolean draw = true;
            for(int i = 0; i<str.length(); i++) {
                if(str.charAt(i) == '_') {
                    draw = false;
                    break;
                }
            }
            if(draw) game.setGameStatus(GameStatus.DRAW);
            else {
                game.setCurrTurn(symbol == 'X' ? 'O' : 'X');
            }
        }
        game = gameRepository.save(game);
        return GameResponseDTO.fromEntity(game);
    }

    private boolean checkWin(char[][] grid, int row, int col, char symbol) {
        boolean rowWin = true, colWin = true, mainDiagWin = true, antiDiagWin = true;
        for(int i = 0; i<grid.length; i++) {
            if(grid[row][i] != symbol) rowWin = false;
            if(grid[i][col] != symbol) colWin = false;
            if(grid[i][i] != symbol) mainDiagWin = false;
            if(grid[i][grid.length-1 - i] != symbol) antiDiagWin = false;
        }
        return (rowWin || colWin || mainDiagWin || antiDiagWin);
    }

    public List<GameResponseDTO> getAvailableGames(UUID userId) {
        List<Game> games = gameRepository.getAllJoinableGames(userId);
        List<GameResponseDTO> gameResponseDTOS = new ArrayList<>();
        for(Game game : games) {
            if(game.getGameStatus() != GameStatus.CREATED) continue;
            gameResponseDTOS.add(GameResponseDTO.fromEntity(game));
        }
        return gameResponseDTOS;
    }

    public GameResponseDTO setStatusInProgress(UUID gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new IllegalArgumentException("Game not found"));
        game.setGameStatus(GameStatus.IN_PROGRESS);
        game = gameRepository.save(game);
        return GameResponseDTO.fromEntity(game);
    }

    public GameResponseDTO joinGame(UUID userId, UUID gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new IllegalArgumentException("Game not found"));
        if(game.getGameStatus() != GameStatus.CREATED) {
            throw new IllegalArgumentException("Game is not joinable");
        }

        if(game.getPo() != null && game.getPx() != null) {
            throw new IllegalArgumentException("Board already full");
        }

        else if(game.getPo() == null && game.getPx() == null) {
            throw new IllegalArgumentException("No player has created this game");
        }

        if(game.getPo() == null) game.setPo(userId);
        else game.setPx(userId);

        game.setGameStatus(GameStatus.IN_PROGRESS);

        game = gameRepository.save(game);
        return GameResponseDTO.fromEntity(game);
    }
}
