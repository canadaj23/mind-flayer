package main;

import chess.engine.board.Board;

/**
 * This starts the chess game.
 */
public class Main {
    public static void main(String[] args) {
        Board board = Board.createStandardBoard();

        System.out.println(board);
    }
}
