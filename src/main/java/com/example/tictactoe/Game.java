package com.example.tictactoe;

class Game {
    Board board;
    Player p1;
    Player p2;
    Player currPlayer;
    WinningStrategy ws;

    Game(int size, WinningStrategy ws) {
        board = new Board(size);
        p1 = new Player(('X')); // when symbol is a
        p2 = new Player(('O')); // class taking strings.
        this.ws = ws;
    }

    int turns;
    void runOptimised () {
        turns = this.board.size*this.board.size;
        this.currPlayer = p1;

        while(turns>0) {
//            board.print();
            System.out.println("Player " +currPlayer.symbol+ " make a move: ");
            Move move = currPlayer.makeMove(board);
            int row = move.row;
            int col = move.col;

            turns--;
            if(ws.hasWon(this.board, this.currPlayer.symbol, row, col)) {
                System.out.println("Winner "+ this.currPlayer.symbol + " won the game");
                break;
            }

            if(turns<=0) {
                System.out.println("Game ended in a DRAW");
                break;
            }
            if(currPlayer == p1) currPlayer = p2;
            else currPlayer = p1;
        }
    }
}




