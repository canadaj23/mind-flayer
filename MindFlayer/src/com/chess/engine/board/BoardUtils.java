package com.chess.engine.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.chess.engine.utils.Constants.BoardConstants.TILES_PER_RANK;
import static com.chess.engine.utils.Constants.BoardConstants.TOTAL_TILES;

/**
 * This class provides useful methods for the Board class.
 */
public class BoardUtils {
    public static final String[] INT_POSITION_TO_STRING_POSITION = initAlgebraicNotation();
    public static final Map<String, Integer> STRING_POSITION_TO_INT_POSITION = initPGNMap();

    /**
     * @return an array with all tiles in PGN notation
     */
    private static String[] initAlgebraicNotation() {
        final List<String > algebraicNotations = new ArrayList<>();

        for (int i = TILES_PER_RANK; i > 0; i--) {
            for (char j = 'a'; j < 'a' + TILES_PER_RANK; j++) {
                algebraicNotations.add("" + j + i);
            }
        }

        return algebraicNotations.toArray(new String[0]);
    }

    /**
     * @return a map with each tile position
     */
    private static Map<String, Integer> initPGNMap() {
        final Map<String, Integer> stringIntegerMap = new HashMap<>();

        for (int i = 0; i < TOTAL_TILES; i++) {
            stringIntegerMap.put(INT_POSITION_TO_STRING_POSITION[i], i);
        }

        return stringIntegerMap;
    }

    /**
     * @param position where the piece is on the board
     * @return the position in tile number notation (a8 to 0)
     */
    public static int GetPositionIntAtPosition(final String position) {
        return STRING_POSITION_TO_INT_POSITION.get(position);
    }

    /**
     * @param position where the piece is on the board
     * @return the position in PGN (algebraic) notation (0 to a8)
     */
    public static String GetPositionStringAtPosition(final int position) {
        return INT_POSITION_TO_STRING_POSITION[position];
    }
}
