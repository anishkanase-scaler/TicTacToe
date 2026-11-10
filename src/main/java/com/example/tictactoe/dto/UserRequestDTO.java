package com.example.tictactoe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        String name,

        @Email(message="Invalid email :(")
        @NotBlank(message = "Pls give email noo")
        String email,

        @NotBlank(message = "Pls give password noo")
        @Size(min = 4, max = 20, message = "Pls give password noo")
        String password
) {

}
