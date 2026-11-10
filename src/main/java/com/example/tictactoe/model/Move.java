package com.example.tictactoe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Move {
    @Id
    private UUID id;

    @Column
    private int row;

    @Column
    private int col;

    @Column
    private UUID gameId;

    @Column
    private char symbol;

}
