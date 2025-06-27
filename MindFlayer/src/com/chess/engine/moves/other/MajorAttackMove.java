package com.chess.engine.moves.other;

import com.chess.engine.board.Board;
import com.chess.engine.moves.pawn.PawnMove;
import com.chess.engine.pieces.Piece;

import static com.chess.engine.board.BoardUtils.GetPositionStringAtPosition;

/**
 * This class represents a major piece's attack (excluding the Pawn).
 */
public class MajorAttackMove extends AttackMove {
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a MajorAttackMove object.
     *
     * @param board               where the move takes place
     * @param movedPiece          the piece to move
     * @param destinationPosition where the piece wants to move to
     * @param attackedPiece       the piece under attack
     */
    public MajorAttackMove(final Board board,
                           final Piece movedPiece,
                           final int destinationPosition,
                           final Piece attackedPiece) {
        super(board, movedPiece, destinationPosition, attackedPiece);
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
        return this == other || (other instanceof MajorAttackMove && super.equals(other));
    }

    /**
     * @return the String version of a major attack move in PGN format
     */
    @Override
    public String toString() {
        return movedPiece.getPieceType() + GetPositionStringAtPosition(this.destinationPosition);
    }
}
