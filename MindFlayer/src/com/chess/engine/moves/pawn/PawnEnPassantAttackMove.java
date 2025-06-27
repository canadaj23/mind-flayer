package com.chess.engine.moves.pawn;

import com.chess.engine.board.Board;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;

import static com.chess.engine.board.BoardUtils.GetPositionStringAtPosition;

public final class PawnEnPassantAttackMove extends PawnAttackMove {
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------- Constructor ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a PawnEnPassantAttackMove object.
     *
     * @param board               where the move takes place
     * @param movedPiece          the piece to move
     * @param destinationPosition where the piece wants to move to
     * @param attackedPiece       the piece under attack
     */
    public PawnEnPassantAttackMove(
            final Board board,
            final Piece movedPiece,
            final int destinationPosition,
            final Piece attackedPiece) {
        super(board, movedPiece, destinationPosition, attackedPiece);
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return a new board after the En Passant move was made
     */
    @Override
    public Board execute() {
        final Board.Builder builder = new Board.Builder();
        // Set all the player's pieces on the same tiles except for the moved piece
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            // Determine whether the piece is not the moved piece
            if (!this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        // Set all the opponent's pieces on the same tiles (no moved pieces)
        for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            if (!piece.equals(this.getAttackedPiece())) {
                builder.setPiece(piece);
            }
        }
        // Move the En Passant Pawn
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
        return this == other || (other instanceof PawnEnPassantAttackMove && super.equals(other));
    }

    /**
     * @return the String version of a Pawn attack in PGN format
     */
    @Override
    public String toString() {
        return GetPositionStringAtPosition(this.movedPiece.getPiecePosition()).charAt(0) +
                "x" +
                GetPositionStringAtPosition(this.destinationPosition);
    }
}
