package chess.engine.players;

import chess.engine.board.Board;
import chess.engine.moves.Move;
import chess.engine.pieces.Alliance;
import chess.engine.pieces.Piece;
import chess.engine.tiles.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    public Alliance getAlliance() {
        return WHITE;
    }



    /**
     * @return the player's opponent
     */
    @Override
    public Player getOpponent() {
        return this.board.getOpponent(WHITE);
    }

    /**
     * @param playerLegals   the player's legal moves
     * @param opponentLegals the opponent's legal moves
     * @return a Collection of all of White's available castles
     */
    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,
                                                    final Collection<Move> opponentLegals) {
        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            /* White's King-side castle */
            // Determine whether the two tiles to the left of the King are empty
            if (!this.board.getTile(61).isTileOccupied() &&
                !this.board.getTile(62).isTileOccupied()) {
                // Obtain the tile the King-side Rook should be on
                final Tile rookTile = this.board.getTile(63);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (CalculateAttacksOnTile(61, opponentLegals).isEmpty() &&
                        CalculateAttacksOnTile(62, opponentLegals).isEmpty() &&
                        rookTile.getPiece().getPieceType().isRook()) {
                        // TODO: add King-side castle
                        kingCastles.add(null);
                    }
                }
            }
            /* Queen-side castle */
            if (!this.board.getTile(1).isTileOccupied() &&
                    !this.board.getTile(2).isTileOccupied() &&
                    !this.board.getTile(3).isTileOccupied()) {
                // Obtain the tile the King-side Rook should be on
                final Tile rookTile = this.board.getTile(0);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (CalculateAttacksOnTile(1, opponentLegals).isEmpty() &&
                            CalculateAttacksOnTile(2, opponentLegals).isEmpty() &&
                            CalculateAttacksOnTile(3, opponentLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                        // TODO: add Queen-side castle
                        kingCastles.add(null);
                    }
                }
            }
        }

        return ImmutableList.copyOf(kingCastles);
    }
}
