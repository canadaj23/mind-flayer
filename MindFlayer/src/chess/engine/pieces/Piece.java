package chess.engine.pieces;

import chess.engine.board.Board;
import chess.engine.moves.Move;

import java.util.Collection;

/**
 * This class serves as a blueprint for all the chess pieces.
 */
public abstract class Piece {
    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean firstMove;
    protected final int cachedHashCode;

//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------

    /**
     * Constructor for a Piece object.
     *
     * @param pieceAlliance White/Black
     * @param piecePosition where the piece is on the board
     */
    protected Piece(final PieceType pieceType, final Alliance pieceAlliance, final int piecePosition) {
        this.pieceType = pieceType;
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
        this.firstMove = false;
        this.cachedHashCode = computeHashCode();
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------

    /**
     * @return the piece's alliance
     */
    public Alliance getAlliance() {
        return this.pieceAlliance;
    }

    /**
     * @return whether is it the piece's first move
     */
    public boolean isFirstMove() {
        return this.firstMove;
    }

    /**
     * @return the piece's current position
     */
    public int getPiecePosition() {
        return this.piecePosition;
    }

    /**
     * @return the type of piece
     */
    public PieceType getPieceType() {
        return this.pieceType;
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

    /**
     * @param move what is forcing an updated piece to be made
     * @return the moved piece at its destination position after a move is made
     */
    public abstract Piece movePiece(final Move move);
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------- Special Overridden Methods ---------------------------------------------
//----------------------------------------------------------------------------------------------------------------------

    /**
     * Checks for object equality on top of reference equality.
     *
     * @param other the other possible piece
     * @return whether the two objects are the same
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) other;

        return piecePosition == otherPiece.getPiecePosition() &&
                pieceType == otherPiece.getPieceType() &&
                pieceAlliance == otherPiece.getAlliance() &&
                firstMove == otherPiece.isFirstMove();
    }

    /**
     * @return a special hashcode for pieces
     */
    @Override
    public int hashCode() {
        return this.cachedHashCode;
    }

    /**
     * @return a unique hashcode for a piece
     */
    private int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + pieceAlliance.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove() ? 1 : 0);

        return result;
    }

    //######################################################################################################################
//###################################################### PieceType #####################################################
//######################################################################################################################
    public enum PieceType {
        PAWN("P") {
            /**
             * @return whether the piece is a King
             */
            @Override
            public boolean isKing() {
                return false;
            }

            /**
             * @return whether the piece is a Rook
             */
            @Override
            public boolean isRook() {
                return false;
            }
        },
        ROOK("R") {
            /**
             * @return whether the piece is a King
             */
            @Override
            public boolean isKing() {
                return false;
            }

            /**
             * @return whether the piece is a Rook
             */
            @Override
            public boolean isRook() {
                return true;
            }
        },
        KNIGHT("N") {
            /**
             * @return whether the piece is a King
             */
            @Override
            public boolean isKing() {
                return false;
            }

            /**
             * @return whether the piece is a Rook
             */
            @Override
            public boolean isRook() {
                return false;
            }
        },
        BISHOP("B") {
            /**
             * @return whether the piece is a King
             */
            @Override
            public boolean isKing() {
                return false;
            }

            /**
             * @return whether the piece is a Rook
             */
            @Override
            public boolean isRook() {
                return false;
            }
        },
        QUEEN("Q") {
            /**
             * @return whether the piece is a King
             */
            @Override
            public boolean isKing() {
                return false;
            }

            /**
             * @return whether the piece is a Rook
             */
            @Override
            public boolean isRook() {
                return false;
            }
        },
        KING("K") {
            /**
             * @return whether the piece is a King
             */
            @Override
            public boolean isKing() {
                return true;
            }

            /**
             * @return whether the piece is a Rook
             */
            @Override
            public boolean isRook() {
                return false;
            }
        };

        private final String pieceInitial;
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------

        /**
         * Constructor for a PieceType object.
         *
         * @param pieceInitial the piece's first initial
         */
        PieceType(final String pieceInitial) {
            this.pieceInitial = pieceInitial;
        }
//----------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------- Abstract Methods --------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------

        /**
         * @return whether the piece is a King
         */
        public abstract boolean isKing();

        /**
         * @return whether the piece is a Rook
         */
        public abstract boolean isRook();
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
