package com.chess.engine.moves.other;

import com.chess.engine.board.Board;
import com.chess.engine.moves.Move;
import com.chess.engine.pieces.Piece;

/**
 * This class represents a major (non-attacking) move.
 */
public class MajorMove extends Move {
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a MajorMove object.
     *
     * @param board               where the move takes place
     * @param movedPiece          the piece to move
     * @param destinationPosition where the piece wants to move to
     */
    public MajorMove(final Board board, final Piece movedPiece, final int destinationPosition) {
        super(board, movedPiece, destinationPosition);
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
}
