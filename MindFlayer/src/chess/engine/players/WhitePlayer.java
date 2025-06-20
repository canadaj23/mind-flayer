package chess.engine.players;

import chess.engine.board.Board;
import chess.engine.moves.Move;
import chess.engine.pieces.Alliance;
import chess.engine.pieces.Piece;

import java.util.Collection;

import static chess.engine.pieces.Alliance.WHITE;

public class WhitePlayer extends Player {
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a Player object.
     *
     * @param board                   the chess board
     * @param whiteStandardLegalMoves White's legal moves
     * @param blackStandardLegalMoves Black's legal moves
     */
    public WhitePlayer(final Board board,
                       final Collection<Move> whiteStandardLegalMoves,
                       final Collection<Move> blackStandardLegalMoves) {
        super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return White's active pieces
     */
    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getPlayerActivePieces(WHITE);
    }

    /**
     * @return the player's alliance
     */
    @Override
    public Alliance getPlayerAlliance() {
        return WHITE;
    }

    /**
     * @return the player's opponent
     */
    @Override
    public Player getOpponent() {
        return this.board.getOpponent(WHITE);
    }
}
