package chess.engine.moves.castle;

import chess.engine.board.Board;
import chess.engine.pieces.Piece;
import chess.engine.pieces.Rook;

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
    public QueenSideCastleMove(
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
     * @return a special hashcode for moves
     */
    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * @return the string version of the Queen-side castle
     */
    @Override
    public String toString() {
        return "O-O-O";
    }
}
