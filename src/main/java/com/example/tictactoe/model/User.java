package com.example.tictactoe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.util.UUID;

public class User {
    @Id
    private UUID id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String password;
}
