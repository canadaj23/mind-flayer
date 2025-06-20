package chess.engine.board;

import chess.engine.moves.Move;
import chess.engine.pieces.*;
import chess.engine.players.BlackPlayer;
import chess.engine.players.Player;
import chess.engine.players.WhitePlayer;
import chess.engine.tiles.Tile;
import com.google.common.collect.ImmutableList;

import java.util.*;

import static chess.engine.pieces.Alliance.*;
import static chess.engine.tiles.Tile.CreateTile;
import static chess.engine.utils.Constants.BoardConstants.TILES_PER_RANK;
import static chess.engine.utils.Constants.BoardConstants.TOTAL_TILES;

/**
 * This class represents the chess board (not part of the GUI).
 * This class includes a Builder subclass that follows the Builder pattern.
 */
public class Board {
    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePieces, blackPieces;
    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a Board object.
     *
     * @param builder what will construct the board
     */
    private Board(final Builder builder) {
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = CalculateActivePieces(this.gameBoard, WHITE);
        this.blackPieces = CalculateActivePieces(this.gameBoard, BLACK);

        final Collection<Move> whiteStandardLegalMoves = CalculateLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardLegalMoves = CalculateLegalMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.blackPlayer = new BlackPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Retrieves the tile with the given position.
     *
     * @param tilePosition where the tile is on the board
     * @return the tile with the given position
     */
    public Tile getTile(final int tilePosition) {
        return gameBoard.get(tilePosition);
    }

    /**
     * Determines the active pieces for White or Black.
     *
     * @param gameBoard what the pieces are on
     * @param alliance  White/Black
     * @return a Collection of all active pieces for White/Black
     */
    private static Collection<Piece> CalculateActivePieces(final List<Tile> gameBoard, final Alliance alliance) {
        final List<Piece> activePieces = new ArrayList<>();
        // Iterate through each tile to find all the active pieces
        for (final Tile currentTile : gameBoard) {
            // Determine if the current tile is occupied
            if (currentTile.isTileOccupied()) {
                // Determine the piece on the occupied tile
                final Piece pieceOnTile = currentTile.getPiece();
                // Determine if the piece is the player's
                if (alliance == pieceOnTile.getPieceAlliance()) {
                    // Add the piece to the list if the alliances are the same
                    activePieces.add(pieceOnTile);
                }
            }
        }

        return ImmutableList.copyOf(activePieces);
    }

    /**
     * Determines which moves are currently legal.
     *
     * @param activePieces the player's active pieces
     * @return a Collection of legal moves for a player
     */
    private Collection<Move> CalculateLegalMoves(final Collection<Piece> activePieces) {
        final List<Move> legalMoves = new ArrayList<>();
        // Iterate through all the active pieces
        for (final Piece activePiece : activePieces) {
            // Add that piece's legal moves to the list of every piece's legal moves
            legalMoves.addAll(activePiece.calculateLegalMoves(this));
        }

        return ImmutableList.copyOf(legalMoves);
    }

    /**
     * @param builder what will create the tiles
     * @return a list with all the tiles (empty and occupied)
     */
    private static List<Tile> createGameBoard(final Builder builder) {
        final Tile[] tiles = new Tile[TOTAL_TILES];

        // Populate all 64 tiles with either an EmptyTile or OccupiedTile
        for (int i = 0; i < TOTAL_TILES; i++) {
            // Map a piece with its associated tile ID
            tiles[i] = CreateTile(i, builder.boardConfig.get(i));
        }

        return ImmutableList.copyOf(tiles);
    }

    /**
     * Creates the initial chess board with no moves performed.
     *
     * @return a chess board with all the pieces in their initial positions
     */
    public static Board createStandardBoard() {
        final Builder builder = new Builder();
        final int[] whitePiecesIndices = { 56, 57, 58, 59, 60, 61, 62, 63 };
        final int[] whitePawnIndices = { 48, 49, 50, 51, 52, 53, 54, 55 };
        final int[] blackPiecesIndices = { 0, 1, 2, 3, 4, 5, 6, 7 };
        final int[] blackPawnIndices = { 8, 9, 10, 11, 12, 13, 14, 15 };

        // Set Black's pieces
        setPieces(builder, blackPiecesIndices, blackPawnIndices, BLACK);
        // Set White's pieces
        setPieces(builder, whitePiecesIndices, whitePawnIndices, WHITE);

        // Set the turn-maker to White
        builder.setMoveMaker(WHITE);

        return builder.build();
    }

    /**
     * @param alliance White/Black
     * @return all the player's active pieces
     */
    public Collection<Piece> getPlayerActivePieces(final Alliance alliance) {
        return alliance.isBlack() ? this.blackPieces : this.whitePieces;
    }

    /**
     * @param alliance the player's alliance
     * @return the player's opponent
     */
    public Player getOpponent(final Alliance alliance) {
        return alliance.isBlack() ? whitePlayer : blackPlayer;
    }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------- Helper Methods ---------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Sets White's or Black's pieces.
     *
     * @param builder       what will set White or Black's pieces
     * @param piecesIndices the indices of the pieces (excluding Pawns)
     * @param pawnIndices   the indices of the Pawns
     * @param alliance      White/Black
     */
    private static void setPieces(
            final Builder builder,
            int[] piecesIndices,
            int[] pawnIndices,
            final Alliance alliance) {
        builder.setPiece(new Rook(alliance, piecesIndices[0]));
        builder.setPiece(new Knight(alliance, piecesIndices[1]));
        builder.setPiece(new Bishop(alliance, piecesIndices[2]));
        builder.setPiece(new Queen(alliance, piecesIndices[3]));
        builder.setPiece(new King(alliance, piecesIndices[4]));
        builder.setPiece(new Bishop(alliance, piecesIndices[5]));
        builder.setPiece(new Knight(alliance, piecesIndices[6]));
        builder.setPiece(new Rook(alliance, piecesIndices[7]));

        setPawns(builder, pawnIndices, alliance);
    }

    /**
     * Sets the Pawns on the board for either White or Black.
     *
     * @param builder     what will set the Pawns
     * @param pawnIndices where the Pawn should initially be
     * @param alliance    White/Black
     */
    private static void setPawns(final Builder builder, final int[] pawnIndices, final Alliance alliance) {
        for (int pawnIndex : pawnIndices) {
            builder.setPiece(new Pawn(alliance, pawnIndex));
        }
    }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------- Special Overridden Methods ---------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return a String version of the chess board
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < TOTAL_TILES; i++) {
            builder.append(String.format("%3s", this.gameBoard.get(i).toString()));
            if ((i + 1) % TILES_PER_RANK == 0) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }

//######################################################################################################################
//#################################################### Board Builder ###################################################
//######################################################################################################################
    /**
     * This subclass will do the actual board construction.
     * It will create an immutable board based off immutable attributes.
     */
    public static class Builder {
        private Map<Integer, Piece> boardConfig;
        private Alliance nextMoveMaker;
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
        /**
         * Constructor for a Builder object.
         */
        public Builder() {
            this.boardConfig = new HashMap<>();
        }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
        /**
         * @return a new board
         */
        public Board build() {
            return new Board(this);
        }

        /**
         * @param piece what will be set on the board
         * @return a Builder with a set piece
         */
        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPiecePosition(), piece);

            return this;
        }

        /**
         * @param nextMoveMaker White/Black
         * @return a Builder with the next move maker determined
         */
        public Builder setMoveMaker(final Alliance nextMoveMaker) {
            this.nextMoveMaker = nextMoveMaker;

            return this;
        }
    }
}
