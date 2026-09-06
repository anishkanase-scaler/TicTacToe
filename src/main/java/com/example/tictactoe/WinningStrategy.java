package com.example.tictactoe;

interface WinningStrategy {
    boolean hasWon(Board board, char symbol, int row, int col);
}
