package com.chess.engine.moves;

import com.chess.engine.board.Board;
import com.chess.engine.moves.other.NullMove;
import com.chess.engine.pieces.Piece;

/**
 * This class serves as a blueprint for all then moves in chess.
 */
public abstract class Move {
    protected final Board board;
    protected final Piece movedPiece;
    protected final int destinationPosition;
    public static final Move NULL_MOVE = new NullMove();
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a Move object.
     *
     * @param board               where the move takes place
     * @param movedPiece          the piece to move
     * @param destinationPosition where the piece wants to move to
     */
    protected Move(final Board board, final Piece movedPiece, final int destinationPosition) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationPosition = destinationPosition;
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return the destination position affiliated with the move
     */
    public int getDestinationPosition() {
        return  this.destinationPosition;
    }

    /**
     * @return the moved piece
     */
    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    /**
     * @return the moved piece's current position
     */
    public int getCurrentPosition() {
        return this.movedPiece.getPiecePosition();
    }

    /**
     * @return whether the move is an attack
     */
    public boolean isAttack() {
        return false;
    }

    /**
     * @return whether the move is a castle
     */
    public boolean isCastlingMove() {
        return false;
    }

    /**
     * @return the piece under attack
     */
    public Piece getAttackedPiece() {
        return null;
    }

    /**
     * @return a new board after the move is made (not mutating the current board!)
     */
    public Board execute() {
        final Board.Builder builder = new Board.Builder();

        // Create the pieces for the player
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            if (!this.movedPiece.equals(piece)) {
                // Set all the player's non-moving pieces
                builder.setPiece(piece);
            }
        }
        // Create the pieces for the opponent
        for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            // TODO: override hashcode and equals methods for all the pieces
            // Set all the opponent's pieces (none should have moved)
            builder.setPiece(piece);
        }
        // Place the moved piece on the new board
        builder.setPiece(this.movedPiece.movePiece(this));
        // Set the opponent as the next move maker
        builder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());

        return builder.build();
    }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------- Special Overridden Methods ---------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Checks for object equality on top of reference equality.
     *
     * @param other the other possible move
     * @return whether the two objects are the same
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Move)) {
            return false;
        }
        final Move otherMove = (Move) other;

        return getDestinationPosition() == otherMove.getDestinationPosition() &&
               getMovedPiece().equals(otherMove.getMovedPiece());
    }

    /**
     * @return a special hashcode for moves
     */
    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + this.destinationPosition;
        result = 31 * result + this.movedPiece.hashCode();
        return result;
    }
//----------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------- Abstract Methods --------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
//######################################################################################################################
//#################################################### Move Factory ####################################################
//######################################################################################################################
    /**
     * This subclass will get/create a move to be used.
     */
    public static class MoveFactory {
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
        /**
         * A constructor for a MoveFactory object (not really).
         */
        public MoveFactory() {
            throw new RuntimeException("MoveFactory cannot be instantiated!");
        }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
        /**
         * @param board               where the move will take place
         * @param currentCoordinate   where the piece currently is
         * @param destinationPosition where the piece wants to move to
         * @return a move based on the parameters
         */
        public static Move CreateMove(final Board board, final int currentCoordinate, final int destinationPosition) {
            // Iterate through all the legal moves for both players
            for (final Move move : board.getAllLegalMoves()) {
                // Determine whether the move's current and destination positions are valid
                if (move.getCurrentPosition() == currentCoordinate &&
                    move.getDestinationPosition() == destinationPosition) {
                    // Use this move
                    return move;
                }
            }
            // A standard move cannot be made
            return NULL_MOVE;
        }
    }
}
