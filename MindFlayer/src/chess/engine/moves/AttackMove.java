package chess.engine.moves;

import chess.engine.board.Board;
import chess.engine.pieces.Piece;

/**
 * This class represents an attack move.
 */
public class AttackMove extends Move {
    private final Piece attackedPiece;
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for an AttackMove object.
     *
     * @param board               where the move takes place
     * @param movedPiece          the piece to move
     * @param destinationPosition where the piece wants to move to
     */
    public AttackMove(
            final Board board,
            final Piece movedPiece,
            final int destinationPosition,
            final Piece attackedPiece) {
        super(board, movedPiece, destinationPosition);
        this.attackedPiece = attackedPiece;
    }
}
