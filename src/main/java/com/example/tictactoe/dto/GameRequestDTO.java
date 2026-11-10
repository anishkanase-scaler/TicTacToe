package com.example.tictactoe.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;


public record GameRequestDTO(@NotBlank(message = "Give UUID") UUID playerId, int size) {
    public GameRequestDTO {
        if(size == 0) {
            size = 3;
        }
    }
}
