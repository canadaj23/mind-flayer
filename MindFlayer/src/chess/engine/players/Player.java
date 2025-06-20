package chess.engine.players;

import chess.engine.board.Board;
import chess.engine.moves.Move;
import chess.engine.pieces.King;
import chess.engine.pieces.Piece;

import java.util.Collection;

/**
 * This class serves as a blueprint for White and Black players.
 */
public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a Player object.
     *
     * @param board         the chess board
     * @param legalMoves    the current player's legal moves
     * @param opponentMoves the opponent's legal moves
     */
    public Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves) {
        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = legalMoves;
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Ensures that the player has a King.
     *
     * @return a King for the player
     */
    private King establishKing() {
        for (final Piece currentPiece : getActivePieces()) {
            if (currentPiece.getPieceType().isKing()) {
                return (King) currentPiece;
            }
        }

        throw new RuntimeException("Each player must have a King!");
    }
//----------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------- Abstract Methods --------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return the player's active pieces
     */
    public abstract Collection<Piece> getActivePieces();
}
