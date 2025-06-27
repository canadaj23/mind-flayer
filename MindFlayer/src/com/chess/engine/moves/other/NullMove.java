package com.chess.engine.moves.other;

import com.chess.engine.board.Board;
import com.chess.engine.moves.Move;

/**
 * This class represents the null move, a move that cannot be found.
 */
public final class NullMove extends Move {
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a NullMove object.
     * The destination position is not on the board.
     */
    public NullMove() {
        super(null, 65);
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return nothing (since the move is null)
     */
    @Override
    public Board execute() {
        throw new RuntimeException("There is nothing to do since the move is null!");
    }

    /**
     * The current position is not on the board.
     *
     * @return the position for a null move
     */
    @Override
    public int getCurrentPosition() {
        return -1;
    }
}
