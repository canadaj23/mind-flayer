package com.chess.engine.moves.castle;

import com.chess.engine.board.Board;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

/**
 * This class represents the King-side castle.
 */
public final class KingSideCastleMove extends CastleMove {

    /**
     * Constructor for a KingSideCastleMove object.
     *
     * @param board               where the move takes place
     * @param movedPiece          the piece to move
     * @param destinationPosition where the piece wants to move to
     */
    public KingSideCastleMove(
            final Board board,
            final Piece movedPiece,
            final int destinationPosition,
            final Rook castleRook,
            final int castleRookStart,
            final int castleRookEnd) {
        super(board, movedPiece, destinationPosition, castleRook, castleRookStart, castleRookEnd);
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
        return this == other || (other instanceof KingSideCastleMove && super.equals(other));
    }

    /**
     * @return the string version of the King-side castle
     */
    @Override
    public String toString() {
        return "O-O";
    }
}
