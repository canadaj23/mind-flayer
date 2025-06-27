package com.chess.engine.moves.pawn;

import com.chess.engine.board.Board;
import com.chess.engine.moves.other.AttackMove;
import com.chess.engine.pieces.Piece;

import static com.chess.engine.board.BoardUtils.GetPositionStringAtPosition;

/**
 * This class represents/ serves as a blueprint for Pawn attacks.
 */
public class PawnAttackMove extends AttackMove {
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a PawnAttackMove object.
     *
     * @param board               where the move takes place
     * @param movedPiece          the piece to move
     * @param destinationPosition where the piece wants to move to
     * @param attackedPiece       the piece under attack
     */
    public PawnAttackMove(
            final Board board,
            final Piece movedPiece,
            final int destinationPosition,
            final Piece attackedPiece) {
        super(board, movedPiece, destinationPosition, attackedPiece);
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------

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
        return this == other || (other instanceof PawnAttackMove && super.equals(other));
    }

    /**
     * @return the String version of a Pawn attack in PGN format
     */
    @Override
    public String toString() {
        return GetPositionStringAtPosition(this.movedPiece.getPiecePosition()).charAt(0) +
               "x" +
               GetPositionStringAtPosition(this.destinationPosition);
    }
}
