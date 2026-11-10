package com.example.tictactoe.dto;

import java.util.UUID;

public record GameRequestDTO(UUID playerId, int size) {
    public GameRequestDTO {
        if(size == 0) {
            size = 3;
        }
    }
}
