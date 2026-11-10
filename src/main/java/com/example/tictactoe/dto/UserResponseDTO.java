package com.example.tictactoe.dto;

import java.util.UUID;

public record UserResponseDTO(UUID id, String name, String email) {
}

