package chess.engine.tiles;

import chess.engine.pieces.Piece;

/**
 * This class represents an occupied tile.
 */
public final class OccupiedTile extends Tile {
    private final Piece pieceOnTile;
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for an OccupiedTile object.
     *
     * @param tilePosition the position of the tile
     */
    public OccupiedTile(final int tilePosition, final Piece pieceOnTile) {
        super(tilePosition);
        this.pieceOnTile = pieceOnTile;
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return whether the tile is occupied
     */
    @Override
    public boolean isTileOccupied() {
        return true;
    }

    /**
     * @return the piece on the tile
     */
    @Override
    public Piece getPiece() {
        return this.pieceOnTile;
    }
}
