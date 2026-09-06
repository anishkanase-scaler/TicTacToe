package com.example.tictactoe;

class DefaultWinningStrategy implements WinningStrategy {
    @Override
    public boolean hasWon(Board board, char symbol, int row, int col) {
        if(checkRows(board, symbol, row) || checkCols(board, symbol, col)
                // || checkDiag1(board, symbol) || checkDiag2(board, symbol)) return true;
                || checkDiags(board, symbol, row, col)) return true;

        return false;
    }

    // check rows
    private boolean checkRows(Board board, char symbol, int row) {
        if(checkRow(row, board, symbol)) return true;
        return false;
    }

    private boolean checkRow(int row, Board board, char symbol) {
        int n = board.size;
        for(int i = 0; i<n; i++) {
            if(board.get(row, i) != symbol) return false;
        }
        return true;
    }


    // check cols
    private boolean checkCols(Board board, char symbol, int col) {
        // int n = board.size;
        // for(int col = 0; col<n; col++) {
        if(checkCol(col, board, symbol)) return true;
        // }
        return false;
    }

    private boolean checkCol(int col, Board board, char symbol) {
        int n = board.size;
        for(int i = 0; i<n; i++) {
            if(board.get(i, col) != symbol) return false;
        }
        return true;
    }

    // check diags
    boolean checkDiags(Board board, char symbol, int row, int col) {
        int n = board.size;
        if(row == col) {
            if(checkDiag1(board, symbol)) return true;
        }
        if(row + col == n-1) {
            if(checkDiag2(board, symbol)) return true;
        }
        return false;
    }

    private boolean checkDiag1(Board board, char symbol) {
        int n = board.size;

        for(int i = 0; i<n; i++) {
            if(board.get(i, i) != symbol) return false;
        }
        return true;
    }

    private boolean checkDiag2(Board board, char symbol) {
        int n = board.size;

        for(int i = 0; i<n; i++) {
            if(board.get(i, n-i-1) != symbol) return false;
        }
        return true;
    }
}

