package chess.engine.moves.pawn;

import chess.engine.board.Board;
import chess.engine.moves.other.AttackMove;
import chess.engine.pieces.Piece;

/**
 * This class represents/ serves as a blueprint for Pawn attacks.
 */
public class PawnAttackMove extends AttackMove {

    /**
     * Constructor for a PawnAttackMove object.
     *
     * @param board               where the move takes place
     * @param movedPiece          the piece to move
     * @param destinationPosition where the piece wants to move to
     * @param attackedPiece       the piece under attack
     */
    public PawnAttackMove(
            final Board board,
            final Piece movedPiece,
            final int destinationPosition,
            final Piece attackedPiece) {
        super(board, movedPiece, destinationPosition, attackedPiece);
    }
}
