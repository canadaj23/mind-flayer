package com.chess.engine.moves.pawn;

import com.chess.engine.board.Board;
import com.chess.engine.moves.Move;
import com.chess.engine.pieces.Piece;

/**
 * This class represents the Pawn's one-tile advance.
 */
public final class PawnMove extends Move {

    /**
     * Constructor for a PawnMove object.
     *
     * @param board               where the move takes place
     * @param movedPiece          the piece to move
     * @param destinationPosition where the piece wants to move to
     */
    public PawnMove(final Board board, final Piece movedPiece, final int destinationPosition) {
        super(board, movedPiece, destinationPosition);
    }
}
