package com.chess.engine.moves.pawn;

import com.chess.engine.board.Board;
import com.chess.engine.board.Board.Builder;
import com.chess.engine.moves.Move;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;

/**
 * This class represents the Pawn promotion and follows the decorated pattern.
 */
public class PawnPromotion extends Move {
    final Move decoratedMove;
    final Pawn promotedPawn;
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a PawnPromotion object.
     * It will take in an object as its parameter and use the object's attributes for its own without
     * changing decoratedMove's behavior.
     *
     * @param decoratedMove the move that follows the decorator pattern
     */
    public PawnPromotion(final Move decoratedMove) {
        super(decoratedMove.getBoard(), decoratedMove.getMovedPiece(), decoratedMove.getDestinationPosition());
        this.decoratedMove = decoratedMove;
        this.promotedPawn = (Pawn) decoratedMove.getMovedPiece();
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return a board after the Pawn promotion
     */
    @Override
    public Board execute() {
        final Builder builder = new Builder();
        // Get the board when the Pawn moves
        final Board boardWithMovedPawn = this.decoratedMove.execute();
        // Set all the player's pieces aside from the promoting Pawn on their current tiles
        for (final Piece piece : boardWithMovedPawn.getCurrentPlayer().getActivePieces()) {
            if (!this.promotedPawn.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        // Set all the opponent's pieces on their current tiles
        for (final Piece piece : boardWithMovedPawn.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        // Set the promoted Pawn
        builder.setPiece(this.promotedPawn.getPromotionPiece().movePiece(this));
        // Set the move maker to the opponent
        builder.setMoveMaker(boardWithMovedPawn.getCurrentPlayer().getOpponent().getAlliance());

        return builder.build();
    }

    /**
     * @return whether the move is Pawn promotion into an attack
     */
    @Override
    public boolean isAttack() {
        return this.decoratedMove.isAttack();
    }

    /**
     * @return the piece under attack
     */
    @Override
    public Piece getAttackedPiece() {
        return this.decoratedMove.getAttackedPiece();
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
        return this == other || (other instanceof PawnPromotion && super.equals(other));
    }

    /**
     * @return a special hashcode for Pawn promotion
     */
    @Override
    public int hashCode() {
        return decoratedMove.hashCode() + (31 * promotedPawn.hashCode());
    }

    /**
     * @return a String version of the Pawn promotion
     */
    @Override
    public String toString() {
        return "";
    }
}
