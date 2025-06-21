package com.chess.main;

import com.chess.engine.board.Board;
import com.chess.gui.Table;

/**
 * This starts the chess game.
 */
public class Main {
    public static void main(String[] args) {
        Board board = Board.createStandardBoard();

        System.out.println(board);

        new Table();
    }
}
