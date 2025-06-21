package chess.engine.moves.castle;

import chess.engine.board.Board;
import chess.engine.pieces.Piece;

/**
 * This class represents the Queen-side castle.
 */
public final class QueenSideCastleMove extends CastleMove {

    /**
     * Constructor for a QueenSideCastleMove object.
     *
     * @param board               where the move takes place
     * @param movedPiece          the piece to move
     * @param destinationPosition where the piece wants to move to
     */
    public QueenSideCastleMove(final Board board, final Piece movedPiece, final int destinationPosition) {
        super(board, movedPiece, destinationPosition);
    }
}
