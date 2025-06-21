package chess.engine.players;

import chess.engine.board.Board;
import chess.engine.moves.Move;
import chess.engine.pieces.Alliance;
import chess.engine.pieces.Piece;

import java.util.Collection;

import static chess.engine.pieces.Alliance.BLACK;

public class BlackPlayer extends Player {
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a BlackPlayer object.
     *
     * @param board                   the chess board
     * @param whiteStandardLegalMoves White's legal moves
     * @param blackStandardLegalMoves Black's legal moves
     */
    public BlackPlayer(final Board board,
                       final Collection<Move> whiteStandardLegalMoves,
                       final Collection<Move> blackStandardLegalMoves) {
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return Black's active pieces
     */
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getPlayerActivePieces(BLACK);
    }

    /**
     * @return the player's alliance
     */
    @Override
    public Alliance getAlliance() {
        return BLACK;
    }

    /**
     * @return the player's opponent
     */
    @Override
    public Player getOpponent() {
        return this.board.getOpponent(BLACK);
    }

    /**
     * @param playerLegals   the player's legal moves
     * @param opponentLegals the opponent's legal moves
     * @return a Collection of all of Black's available castles
     */
    @Override
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals) {
        return null;
    }
}
