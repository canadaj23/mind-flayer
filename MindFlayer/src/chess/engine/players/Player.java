package chess.engine.players;

import chess.engine.board.Board;
import chess.engine.moves.Move;
import chess.engine.moves.MoveTransition;
import chess.engine.pieces.Alliance;
import chess.engine.pieces.King;
import chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static chess.engine.moves.MoveStatus.*;

/**
 * This class serves as a blueprint for White and Black players.
 */
public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean inCheck;
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
        this.inCheck = !CalculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
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
     * Determines what moves make opponent pieces attack the player's King.
     *
     * @param piecePosition where the player's King is
     * @param moves         the Collection of the opponent's legal moves
     * @return a Collection of all the attacks on the player's King
     */
    private Collection<Move> CalculateAttacksOnTile(final int piecePosition, final Collection<Move> moves) {
        final List<Move> attackMoves = new ArrayList<>();
        // Iterate through all the moves to determine attack moves
        for (final Move move : moves) {
            // Determine if the current position is the position affiliated with the opponent move
            if (piecePosition == move.getDestinationPosition()) {
                // This is an attack on the player's King
                attackMoves.add(move);
            }
        }

        return ImmutableList.copyOf(attackMoves);
    }

    /**
     * @param move the player's move
     * @return whether the player's move is legal
     */
    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }
    /**
     * @return whether the player's King is in a check
     */
    public boolean isInCheck() {
        return this.inCheck;
    }

    // TODO: implement these methods
    /**
     * @return whether the player's King is in a checkmate
     */
    public boolean isInCheckmate() {
        return this.isInCheck() && !hasEscapeMoves();
    }

    /**
     * @return whether the player's King is in a stalemate
     */
    public boolean isInStalemate() {
        return !this.isInCheck() && !hasEscapeMoves();
    }

    // TODO: work on the below main methods
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
        // Determine if the move is illegal
        if (!isMoveLegal(move)) {
            // The move is deemed illegal and no move is made (no new board)
            return new MoveTransition(this.board, move, ILLEGAL_MOVE);
        }
        // Make the transition board based off the move (not mutating the current board!)
        final Board transitionBoard = move.execute();
        /* Determine the attacks on the player's King
         * getCurrentPlayer().getOpponent() is used because the next board makes it the player's opponent's turn */
        final Collection<Move> kingAttacks = CalculateAttacksOnTile(
                                    transitionBoard.getCurrentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                                    transitionBoard.getCurrentPlayer().getLegalMoves());
        // Determine if there are any attacks on the player's King
        if (!kingAttacks.isEmpty()) {
            // The move is deemed a check move, and no move is made (no new board)
            return new MoveTransition(this.board, move, LEAVES_PLAYER_IN_CHECK);
        }
        // The move is done after all the checks
        return new MoveTransition(transitionBoard, move, DONE);
    }

    /**
     * @return the player's King
     */
    private King getPlayerKing() {
        return this.playerKing;
    }

    /**
     * @return the player's legal moves
     */
    private Collection<Move> getLegalMoves() {
        return this.legalMoves;
    }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------- Helper Methods ---------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------

    /**
     * @return whether the player's King can escape from being in check
     */
    protected boolean hasEscapeMoves() {
        // Iterate through all the legal moves
        for (final Move move : this.legalMoves) {
            // Make a theoretical move
            final MoveTransition transition = makeMove(move);
            // Determine whether the move was successful and thus done
            if (transition.getMoveStatus().isDone()) {
                return true;
            }
        }

        return false;
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
