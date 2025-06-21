package chess.engine.moves.pawn;

import chess.engine.board.Board;
import chess.engine.moves.Move;
import chess.engine.pieces.Piece;

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
