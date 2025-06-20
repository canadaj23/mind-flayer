package chess.engine.moves;

import chess.engine.board.Board;

/**
 * A move transition represents the transition from one board to another based on the move.
 * All relevant information will be passed along.
 */
public class MoveTransition {
    private final Board transitionBoard;
    private final Move move;
    private final MoveStatus moveStatus;

    public MoveTransition(final Board transitionBoard, final Move move, final MoveStatus moveStatus) {
        this.transitionBoard = transitionBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }
}
