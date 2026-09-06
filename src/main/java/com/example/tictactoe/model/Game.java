package com.example.tictactoe.model;

import com.example.tictactoe.enums.GameStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

import java.util.UUID;

@Entity
@Table(name = "games")
@Getter
@Setter
public class Game {
    @Id
    private UUID id;

    @Column
    private int boardSize;

    @Column
    private String boardState;

    @Column
    private char currTurn;

    @Column
    private UUID px;

    @Column
    private UUID po;

    @Column
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    @Column
    private GameStatus gameStatus;

    public Game() {}

    public Game(int boardSize) {
        this.boardSize = boardSize;
        currTurn = 'X';
        gameStatus = GameStatus.IN_PROGRESS;
        boardState = "_".repeat(boardSize * boardSize);
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
    }

    @Transient
    public char[][] getGrid() {
        char[][] grid = new char[boardSize][boardSize];
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                grid[i][j] = boardState.charAt(i * boardSize + j);
            }
        }
        return grid;
    }

    @Transient
    public void setGrid(char[][] grid) {
        StringBuilder sb = new StringBuilder(boardSize * boardSize);
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                sb.append(grid[i][j]);
            }
        }
        this.boardState = sb.toString();
    }
}
