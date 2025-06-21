package com.chess.engine.moves.other;

import com.chess.engine.board.Board;
import com.chess.engine.moves.Move;
import com.chess.engine.pieces.Piece;

/**
 * This class represents an attack move.
 */
public class AttackMove extends Move {
    private final Piece attackedPiece;
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for an AttackMove object.
     *
     * @param board               where the move takes place
     * @param movedPiece          the piece to move
     * @param destinationPosition where the piece wants to move to
     * @param attackedPiece       the piece under attack
     */
    public AttackMove(
            final Board board,
            final Piece movedPiece,
            final int destinationPosition,
            final Piece attackedPiece) {
        super(board, movedPiece, destinationPosition);
        this.attackedPiece = attackedPiece;
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return a new board after the move is made
     */
    @Override
    public Board execute() {
        return null;
    }

    /**
     * @return whether the move is an attack
     */
    @Override
    public boolean isAttack() {
        return true;
    }

    /**
     * @return the piece under attack
     */
    @Override
    public Piece getAttackedPiece() {
        return this.attackedPiece;
    }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------- Special Overridden Methods ---------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Checks for object equality on top of reference equality.
     *
     * @param other the other possible AttackMove
     * @return whether the two objects are the same
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof AttackMove)) {
            return false;
        }
        final AttackMove otherAttackMove = (AttackMove) other;

        return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
    }

    /**
     * @return a special hashcode for moves
     */
    @Override
    public int hashCode() {
        return this.attackedPiece.hashCode() + super.hashCode();
    }
}
