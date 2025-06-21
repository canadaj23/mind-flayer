package chess.engine.moves.castle;

import chess.engine.board.Board;
import chess.engine.pieces.Piece;

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
    public KingSideCastleMove(final Board board, final Piece movedPiece, final int destinationPosition) {
        super(board, movedPiece, destinationPosition);
    }
}
