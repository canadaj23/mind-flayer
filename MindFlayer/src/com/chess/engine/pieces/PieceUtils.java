package com.chess.engine.pieces;

import static com.chess.engine.utils.Constants.BoardConstants.TILES_PER_FILE;
import static com.chess.engine.utils.Constants.BoardConstants.TOTAL_TILES;

/**
 * This class provides helpful methods for the Piece classes.
 */
public class PieceUtils {

    /**
     * Determines if the destination position is within the bounds of the board.
     *
     * @param destinationPosition where the piece wants to move to
     * @return whether the destination position is valid
     */
    protected static boolean IsDestinationPositionValid(final int destinationPosition) {
        return destinationPosition >= 0 && destinationPosition < TOTAL_TILES;
    }

    protected static final boolean[] FIRST_FILE = initFile(0);
    protected static final boolean[] SECOND_FILE = initFile(1);
    protected static final boolean[] SEVENTH_FILE = initFile(6);
    protected static final boolean[] EIGHTH_FILE = initFile(7);

    /**
     * Creates an array with certain file entries set to true.
     *
     * @param fileNumber what the file is
     * @return an array of true/false entries
     */
    private static boolean[] initFile(int fileNumber) {
        final boolean[] file = new boolean[TOTAL_TILES];

        do {
            file[fileNumber] = true;
            fileNumber += TILES_PER_FILE;
        } while (fileNumber < TOTAL_TILES);

        return file;
    }
}
