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
    // TODO: more work to do here
    protected final boolean firstMove;
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------

    /**
     * Constructor for a Piece object.
     *
     * @param pieceAlliance White/Black
     * @param piecePosition where the piece is on the board
     */
    protected Piece(final Alliance pieceAlliance, final int piecePosition) {
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
        this.firstMove = false;
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------

    /**
     * @return the piece's alliance
     */
    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    /**
     * @return whether is it the piece's first move
     */
    protected boolean isFirstMove() {
        return this.firstMove;
    }

    /**
     * @return the piece's current position
     */
    public int getPiecePosition() {
        return this.piecePosition;
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

//######################################################################################################################
//###################################################### PieceType #####################################################
//######################################################################################################################
    public enum PieceType {
        PAWN("P"),
        ROOK("R"),
        KNIGHT("N"),
        BISHOP("B"),
        QUEEN("Q"),
        KING("K");

        private final String pieceInitial;
    //----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
        PieceType(final String pieceInitial) {
            this.pieceInitial = pieceInitial;
        }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------- Special Overridden Methods ---------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
        /**
         * @return the piece's initial
         */
        @Override
        public String toString() {
            return this.pieceInitial;
        }
    }
}
