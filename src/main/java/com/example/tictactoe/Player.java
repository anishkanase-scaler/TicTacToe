package com.example.tictactoe;

import java.util.Scanner;

public class Player {
    char symbol;
    static Scanner sc = new Scanner(System.in);
    Player(char symbol) {
        this.symbol = symbol;
    }
    public Move makeMove(Board board) {
        int i = sc.nextInt();
        int j = sc.nextInt();

        Move move = new Move(i, j);

        int n = board.size;
        if(i<n && j<n && i>=0 && j>=0 && board.get(i, j) == '_') {
            board.set(i, j, this.symbol);
            return move;
        }
        else {
            System.out.println("Provide valid inputs and try again");
            return this.makeMove(board);

        }
    }
}

