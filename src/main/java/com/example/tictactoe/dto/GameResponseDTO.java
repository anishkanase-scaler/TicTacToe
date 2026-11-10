package com.example.tictactoe.dto;

import com.example.tictactoe.enums.GameStatus;
import com.example.tictactoe.model.Game;

import java.util.UUID;

public record GameResponseDTO(UUID id, char[][] grid, char currTurn, GameStatus status) {
    public static GameResponseDTO fromEntity(Game game) {
        return new GameResponseDTO(game.getId(), game.getGrid(), game.getCurrTurn(), game.getGameStatus());
    }
}

// Record is basically like similar to interface, class, enum etc. It is immutable, and we all fields are private final,
// and also it will be initialized once in constructor only once while declaration, no setters and getters.
// return new GameResponseDTO => this is how we are using constructor, and setting all values like UUID id => game.getId() etc.
// And we did not have to manually create a constructor.

// We always have to take a Game DTO from frontend and return response DTO to frontend, Game is collection and a single
// entity, and GameResponse is collection of terms needed for frontend
// we cannot pass objects directly to fe and be, so we use DTOs.
// Purpose of DTOs are: returns only things needed by frontend, size is less, safety.
