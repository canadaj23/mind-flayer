package com.chess.engine.moves.pawn;

import com.chess.engine.board.Board;
import com.chess.engine.moves.Move;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;

import static com.chess.engine.board.Board.*;
import static com.chess.engine.board.BoardUtils.GetPositionStringAtPosition;

/**
 * This class represents the Pawn two-tile advance.
 */
public final class PawnJump extends Move {
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a PawnJump object.
     *
     * @param board               where the move takes place
     * @param movedPiece          the piece to move
     * @param destinationPosition where the piece wants to move to
     */
    public PawnJump(final Board board, final Piece movedPiece, final int destinationPosition) {
        super(board, movedPiece, destinationPosition);
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return a new board after the move is made (not mutating the current board!)
     */
    @Override
    public Board execute() {
        final Builder builder = new Builder();
        // Set all the player's pieces on the same tiles except for the moved piece
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            // Determine whether the piece is not the moved piece
            if (!this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        // Set all the opponent's pieces on the same tiles (no moved pieces)
        for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        // Determine the moved Pawn
        final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
        // Place the moved piece on the new board
        builder.setPiece(movedPawn);
        // Set the Pawn as able to be attacked En Passant since its first move was a two-tile advance
        builder.setEnPassantPawn(movedPawn);
        // Set the opponent as the next move maker
        builder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());

        return builder.build();
    }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------- Special Overridden Methods ---------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return the String version of the Pawn two-tile advance in PGN format
     */
    @Override
    public String toString() {
        return GetPositionStringAtPosition(this.destinationPosition);
    }
}
