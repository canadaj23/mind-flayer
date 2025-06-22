package com.chess.engine.moves.other;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
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
//--------------------------------------------- Special Overridden Methods ---------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Checks for object equality on top of reference equality.
     *
     * @param other the other possible move
     * @return whether the two objects are the same
     */
    @Override
    public boolean equals(final Object other) {
        return this == other || (other instanceof MajorMove && super.equals(other));
    }

    @Override
    public String toString() {
        return movedPiece.getPieceType().toString() + BoardUtils.GetPositionStringAtPosition(this.destinationPosition);
    }
}
