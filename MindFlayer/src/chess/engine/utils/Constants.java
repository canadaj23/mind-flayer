package chess.engine.utils;

/**
 * This class holds various constants for separate classes.
 */
public class Constants {

    public static class BoardConstants {
        public static final int TOTAL_TILES = 64;
        public static final int TILES_PER_FILE = 8;
        public static final int TILES_PER_RANK = 8;
    }

    public static class PieceConstants {
        public final static int[] KNIGHT_OFFSETS = { -17, -15, -10, -6, 6, 10, -15, -17 };
        public final static int[] BISHOP_OFFSETS = { -9, -7, 7, 9 };
        public final static int[] ROOK_OFFSETS = { -8, -1, 1, 8 };
        public final static int[] QUEEN_KING_OFFSETS = { -9, -8, -7, -1, 1, 7, 8, 9 };
    }
}
