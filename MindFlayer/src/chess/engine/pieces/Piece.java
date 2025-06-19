package chess.engine.pieces;

import chess.engine.board.Board;
import chess.engine.moves.Move;

import java.util.Collection;

/**
 * This class serves as a blueprint for all the chess pieces.
 */
public abstract class Piece {
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a Piece object.
     *
     * @param pieceAlliance White/Black
     * @param piecePosition where the piece is on the board
     */
    public Piece(final Alliance pieceAlliance, final int piecePosition) {
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
    }

    /**
     * @return the piece's alliance
     */
    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }
//----------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------- Abstract Methods --------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Calculates all legal moves for the piece.
     *
     * @param board where the piece will make a move
     * @return a list of all the piece's legal moves
     */
    public abstract Collection<Move> calculateLegalMoves(final Board board);
}
