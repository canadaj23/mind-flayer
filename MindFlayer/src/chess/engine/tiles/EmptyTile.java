package chess.engine.tiles;

import chess.engine.pieces.Piece;

/**
 * This class represents an empty tile.
 */
public final class EmptyTile extends Tile {
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for an EmptyTile object.
     *
     * @param tilePosition the position of the tile
     */
    public EmptyTile(int tilePosition) {
        super(tilePosition);
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return whether the tile is occupied
     */
    @Override
    public boolean isTileOccupied() {
        return false;
    }

    /**
     * @return the piece on the tile
     */
    @Override
    public Piece getPiece() {
        return null;
    }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------- Special Overridden Methods ---------------------------------------------
//----------------------------------------------------------------------------------------------------------------------

    /**
     * @return the String representation of an empty tile
     */
    @Override
    public String toString() {
        return "-";
    }
}
