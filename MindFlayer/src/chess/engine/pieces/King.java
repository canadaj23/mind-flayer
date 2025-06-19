package chess.engine.pieces;

import chess.engine.board.Board;
import chess.engine.moves.Move;

import java.util.Collection;

/**
 * This class represents the King chess piece.
 */
public class King extends Piece {
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a King object.
     *
     * @param pieceAlliance White/Black
     * @param piecePosition where the King is on the board
     */
    public King(final Alliance pieceAlliance, final int piecePosition) {
        super(pieceAlliance, piecePosition);
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Calculates all legal moves for the King.
     *
     * @param board where the King will make a move
     * @return a list of all the King's legal moves
     */
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        return null;
    }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------- Helper Methods ---------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
}
