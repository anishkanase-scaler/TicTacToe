package com.example.tictactoe.dto;

import com.example.tictactoe.enums.GameStatus;
import com.example.tictactoe.model.Game;

import java.util.UUID;

public record GameResponseDTO(UUID id, char[][] grid, char currTurn, GameStatus status) {
    public static GameResponseDTO fromEntity(Game game) {
        return new GameResponseDTO(game.getId(), game.getGrid(), game.getCurrTurn(), game.getGameStatus());
    }
}

