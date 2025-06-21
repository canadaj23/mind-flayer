package com.chess.engine.tiles;

import com.chess.engine.pieces.Piece;

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
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------- Special Overridden Methods ---------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return the String representation of an occupied tile
     */
    @Override
    public String toString() {
        return getPiece().getAlliance().isBlack() ? getPiece().toString().toLowerCase() : getPiece().toString();
    }
}
