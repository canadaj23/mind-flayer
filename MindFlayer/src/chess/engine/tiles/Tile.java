package chess.engine.tiles;

import chess.engine.pieces.Piece;

import java.util.Map;

import static chess.engine.tiles.TileUtils.CreateAllPossibleEmptyTiles;

/**
 * This class serves as a blueprint for EmptyTile and OccupiedTile.
 */
public abstract class Tile {
    protected final int tilePosition;
    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = CreateAllPossibleEmptyTiles();
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a Tile object.
     *
     * @param tilePosition the position of the tile
     */
    protected Tile(final int tilePosition) {
        this.tilePosition = tilePosition;
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Creates an individual tile based on occupancy.
     *
     * @param tilePosition where the tile is on the board
     * @param piece        the piece on the board
     * @return either an EmptyTile or OccupiedTile
     */
    public static Tile CreateTile(final int tilePosition, final Piece piece) {
        return piece != null ? new OccupiedTile(tilePosition, piece) : EMPTY_TILES_CACHE.get(tilePosition);
    }
//----------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------- Abstract Methods --------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return whether the tile is occupied
     */
    public abstract boolean isTileOccupied();

    /**
     * @return the piece on the tile
     */
    public abstract Piece getPiece();
}
