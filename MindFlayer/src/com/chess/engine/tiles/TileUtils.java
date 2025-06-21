package com.chess.engine.tiles;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

import static com.chess.engine.utils.Constants.BoardConstants.TOTAL_TILES;

/**
 * This class provides methods useful for the Tile classes.
 */
public class TileUtils {

    /**
     * Produces a Map of cached empty tiles.
     *
     * @return a map containing all 64 cached empty tiles
     */
    public static Map<Integer, EmptyTile> CreateAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for (int i = 0; i < TOTAL_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }

        return ImmutableMap.copyOf(emptyTileMap);
    }
}
