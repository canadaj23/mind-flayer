package com.chess.engine.moves.castle;

import com.chess.engine.board.Board;
import com.chess.engine.board.Board.Builder;
import com.chess.engine.moves.Move;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

/**
 * This class serves as a blueprint for the two castling moves.
 */
abstract class CastleMove extends Move {
    protected final Rook castleRook;
    protected final int castleRookStart, castleRookEnd;
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a CastleMove object.
     *
     * @param board               where the move takes place
     * @param movedPiece          the piece to move
     * @param destinationPosition where the piece wants to move to
     */
    public CastleMove(
            final Board board,
            final Piece movedPiece,
            final int destinationPosition,
            final Rook castleRook,
            final int castleRookStart,
            final int castleRookEnd) {
        super(board, movedPiece, destinationPosition);
        this.castleRook = castleRook;
        this.castleRookStart = castleRookStart;
        this.castleRookEnd = castleRookEnd;
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return the castle Rook
     */
    public Rook getCastleRook() {
        return this.castleRook;
    }

    /**
     * @return whether the move is a castle
     */
    @Override
    public boolean isCastlingMove() {
        return true;
    }

    /**
     * @return a new board after the move is made (not mutating the current board!)
     */
    @Override
    public Board execute() {
        final Builder builder = new Builder();
        // Set all the player's pieces on the same tiles aside from the moving Rook
        for (final Piece piece : this.board.getCurrentPlayer().getActivePieces()) {
            if (!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        // Set all the opponent's pieces on the same tiles
        for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        // Move the moved King
        builder.setPiece(this.movedPiece.movePiece(this));
        // TODO: look into the first move of normal pieces
        // Move the moved Rook
        builder.setPiece(new Rook(this.castleRook.getPieceAlliance(), this.castleRookEnd));
        // Set the next move maker to be the opponent
        builder.setMoveMaker(this.board.getCurrentPlayer().getOpponent().getAlliance());

        return builder.build();
    }
}
