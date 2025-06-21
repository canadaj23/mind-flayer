package chess.engine.moves.castle;

import chess.engine.board.Board;
import chess.engine.moves.Move;
import chess.engine.pieces.Piece;

/**
 * This class serves as a blueprint for the two castling moves.
 */
abstract class CastleMove extends Move {

    /**
     * Constructor for a CastleMove object.
     *
     * @param board               where the move takes place
     * @param movedPiece          the piece to move
     * @param destinationPosition where the piece wants to move to
     */
    public CastleMove(final Board board, final Piece movedPiece, final int destinationPosition) {
        super(board, movedPiece, destinationPosition);
    }
}
