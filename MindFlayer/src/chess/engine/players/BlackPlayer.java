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
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,
                                                    final Collection<Move> opponentLegals) {
        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            /* Black's King-side castle */
            // Determine whether the two tiles to the right of the King are empty
            if (!this.board.getTile(5).isTileOccupied() &&
                !this.board.getTile(6).isTileOccupied()) {
                // Obtain the tile the King-side Rook should be on
                final Tile rookTile = this.board.getTile(7);
                // Determine if the tile is occupied and it is the Rook's first move
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    // Determine if there are no attacks on the empty tiles and that the piece is a Rook
                    if (CalculateAttacksOnTile(5, opponentLegals).isEmpty() &&
                        CalculateAttacksOnTile(6, opponentLegals).isEmpty() &&
                        rookTile.getPiece().getPieceType().isRook()) {
                        // The move is a King-side castle
                        kingCastles.add(new KingSideCastleMove(this.board,
                                                               this.playerKing,
                                                               6,
                                                               (Rook) rookTile.getPiece(),
                                                               rookTile.getTilePosition(),
                                                               5));
                    }
                }
            }
            /* Black's Queen-side castle */
            // Determine whether the three tiles to the left of the King are empty
            if (!this.board.getTile(1).isTileOccupied() &&
                !this.board.getTile(2).isTileOccupied() &&
                !this.board.getTile(3).isTileOccupied()) {
                // Obtain the tile the Queen-side Rook should be on
                final Tile rookTile = this.board.getTile(0);
                // Determine if the tile is occupied and it is the Rook's first move
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    // Determine if there are no attacks on the empty tiles and that the piece is a Rook
                    if (CalculateAttacksOnTile(1, opponentLegals).isEmpty() &&
                        CalculateAttacksOnTile(2, opponentLegals).isEmpty() &&
                        CalculateAttacksOnTile(3, opponentLegals).isEmpty() &&
                        rookTile.getPiece().getPieceType().isRook()) {
                        // The move is a Queen-side castle
                        kingCastles.add(new QueenSideCastleMove(this.board,
                                                                this.playerKing,
                                                                2,
                                                                (Rook) rookTile.getPiece(),
                                                                rookTile.getTilePosition(),
                                                                3));
                    }
                }
            }
        }

        return ImmutableList.copyOf(kingCastles);
    }
}
