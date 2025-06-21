package chess.engine.players;

import chess.engine.board.Board;
import chess.engine.moves.Move;
import chess.engine.moves.castle.KingSideCastleMove;
import chess.engine.moves.castle.QueenSideCastleMove;
import chess.engine.pieces.Alliance;
import chess.engine.pieces.Piece;
import chess.engine.pieces.Rook;
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
        // Non-PlayerUtils version
        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            /* White's King-side castle */
            // Determine whether the two tiles to the right of the King are empty
            if (!this.board.getTile(61).isTileOccupied() &&
                !this.board.getTile(62).isTileOccupied()) {
                // Obtain the tile the King-side Rook should be on
                final Tile rookTile = this.board.getTile(63);
                // Determine if the tile is occupied and it is the Rook's first move
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    // Determine if there are no attacks on the empty tiles and that the piece is a Rook
                    if (CalculateAttacksOnTile(61, opponentLegals).isEmpty() &&
                        CalculateAttacksOnTile(62, opponentLegals).isEmpty() &&
                        rookTile.getPiece().getPieceType().isRook()) {
                        // The move is a King-side castle
                        kingCastles.add(new KingSideCastleMove(this.board,
                                                               this.playerKing,
                                                               62,
                                                               (Rook) rookTile.getPiece(),
                                                               rookTile.getTilePosition(),
                                                               61));
                    }
                }
            }
            /* White's Queen-side castle */
            // Determine whether the three tiles to the left of the King are empty
            if (!this.board.getTile(57).isTileOccupied() &&
                !this.board.getTile(58).isTileOccupied() &&
                !this.board.getTile(59).isTileOccupied()) {
                // Obtain the tile the Queen-side Rook should be on
                final Tile rookTile = this.board.getTile(56);
                // Determine if the tile is occupied and it is the Rook's first move
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    // Determine if there are no attacks on the empty tiles and that the piece is a Rook
                    if (CalculateAttacksOnTile(57, opponentLegals).isEmpty() &&
                        CalculateAttacksOnTile(58, opponentLegals).isEmpty() &&
                        CalculateAttacksOnTile(59, opponentLegals).isEmpty() &&
                        rookTile.getPiece().getPieceType().isRook()) {
                        // The move is a Queen-side castle
                        kingCastles.add(new QueenSideCastleMove(this.board,
                                                                this.playerKing,
                                                                58,
                                                                (Rook) rookTile.getPiece(),
                                                                rookTile.getTilePosition(),
                                                                59));
                    }
                }
            }
        }

        return ImmutableList.copyOf(kingCastles);
    }
}
