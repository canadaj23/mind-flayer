package chess.engine.moves.pawn;

import chess.engine.board.Board;
import chess.engine.pieces.Piece;

public final class PawnEnPassantAttackMove extends PawnAttackMove {

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
}
