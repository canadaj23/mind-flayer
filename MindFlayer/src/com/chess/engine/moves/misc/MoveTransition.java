package com.chess.engine.moves.misc;

import com.chess.engine.board.Board;
import com.chess.engine.moves.Move;

/**
 * A move transition represents the transition from one board to another based on the move.
 * All relevant information will be passed along.
 */
public class MoveTransition {
    private final Board transitionBoard;
    private final Move move;
    private final MoveStatus moveStatus;
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    public MoveTransition(final Board transitionBoard, final Move move, final MoveStatus moveStatus) {
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return the move's status
     */
    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }

    /**
     * @return the transition board (subsequent board after a move is made)
     */
    public Board getTransitionBoard() {
        return this.transitionBoard;
    }
}
