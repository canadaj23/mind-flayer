package chess.engine.players;

import chess.engine.board.Board;
import chess.engine.moves.Move;
import chess.engine.moves.MoveTransition;
import chess.engine.pieces.Alliance;
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

    /**
     * @param move the player's move
     * @return whether the player's move is legal
     */
    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    // TODO: implement these methods
    /**
     * @return whether the player's King is in a check
     */
    public boolean isInCheck() {
        return false;
    }

    /**
     * @return whether the player's King is in a checkmate
     */
    public boolean isInCheckmate() {
        return false;
    }

    /**
     * @return whether the player's King is in a stalemate
     */
    public boolean isInStalemate() {
        return false;
    }

    /**
     * @return whether the player has castled
     */
    public boolean isInCastled() {
        return false;
    }

    /**
     * @param move the move to be performed
     * @return the status of the move
     */
    public MoveTransition makeMove(final Move move) {
        return null;
    }
//----------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------- Abstract Methods --------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return the player's active pieces
     */
    public abstract Collection<Piece> getActivePieces();

    /**
     * @return the player's alliance
     */
    public abstract Alliance getPlayerAlliance();

    /**
     * @return the player's opponent
     */
    public abstract Player getOpponent();

}
