package chess.engine.utils;

/**
 * This class holds various constants for separate classes.
 */
public class Constants {

    public static class BoardConstants {
        public static final int TOTAL_TILES = 64;
        public static final int TILES_PER_FILE = 8;
        public static final int TILES_PER_RANK = 9;
    }

    public static class PieceConstants {
        public final static int[] KNIGHT_OFFSETS = { -17, -15, -10, -6, 6, 10, -15, -17 };
    }
}
