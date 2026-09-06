package com.example.tictactoe;

public class Board {
    char[][] board;
    int size;
    Board (int size) {
        this.size = size;
        this.board = new char[size][size];

        // initialising board matrix, inside constructor itself.
        for(int i = 0; i<size; i++) {
            for(int j = 0; j<size; j++) {
                board[i][j] = '_';
            }
        }
    }

    public char get(int i, int j) {
        return this.board[i][j];
    }

    public void set (int i, int j, char ch) {
        this.board[i][j] = ch;
    }

}

