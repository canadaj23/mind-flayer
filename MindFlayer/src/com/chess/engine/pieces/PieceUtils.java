package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.moves.Move;
import com.chess.engine.moves.other.MajorAttackMove;
import com.chess.engine.moves.other.MajorMove;
import com.chess.engine.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

import static com.chess.engine.pieces.Piece.*;
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

    /**
     * Determines the legal moves for a Bishop, Queen, or Rook.
     *
     * @param offsets       the sliding piece's offsets
     * @param piecePosition the sliding piece's current position
     * @param board         what the sliding piece is on
     * @param slidingPiece  the piece to have its legal moves determined
     * @param pieceAlliance the sliding piece's alliance
     * @param pieceType     what the sliding piece is
     * @return all the legal moves of the sliding piece
     */
    protected static List<Move> DetermineSlidingPieceLegalMoves(final int[] offsets,
                                                                final int piecePosition,
                                                                final Board board,
                                                                final Piece slidingPiece,
                                                                final Alliance pieceAlliance,
                                                                final PieceType pieceType) {
        final List<Move> legalMoves = new ArrayList<>();
        // Iterate through all the offsets to determine the sliding piece's legal moves
        for (final int currentOffset : offsets) {
            // Calculate the destination position
            int destinationPosition = piecePosition;
            // Determine if the destination position is on the board
            while (IsDestinationPositionValid(destinationPosition)) {
                // Determine whether the sliding piece will be on the 1st or 8th file
                if (AnySlidingPieceExclusions(piecePosition, currentOffset, pieceType)) {
                    // The current offset will break the sliding piece's movement, so move to the next offset
                    break;
                }
                // Increment the destination position with the current offset
                destinationPosition += currentOffset;
                // Determine whether the destination position is valid
                if (IsDestinationPositionValid(destinationPosition)) {
                    // Obtain the destination tile
                    final Tile destinationTile = board.getTile(destinationPosition);
                    if (!destinationTile.isTileOccupied()) {
                        // The move counts as moving to an empty tile
                        legalMoves.add(new MajorMove(board, slidingPiece, destinationPosition));
                    } else {
                        // The move counts as an attack
                        PerformAttackMove(board, destinationPosition, pieceAlliance, slidingPiece, legalMoves);
                        break;
                    }
                }
            }
        }
        return legalMoves;
    }

    /**
     * @param currentPosition where the sliding piece is on the board
     * @param currentOffset   the sliding piece's probable problematic offset
     * @param pieceType       what the piece is
     * @return whether the sliding piece is on the first or eighth file
     */
    private static boolean AnySlidingPieceExclusions(final int currentPosition,
                                                     final int currentOffset,
                                                     final PieceType pieceType) {
        // Determine which file the sliding piece is on
        int file = (currentPosition % 8) + 1;

        switch (pieceType) {
            case BISHOP -> {
                return (file == 1 && (currentOffset == -9 || currentOffset == 7)) ||
                       (file == 8 && (currentOffset == -7 || currentOffset == 9));
            }
            case QUEEN -> {
                return (file == 1 && (currentOffset == -9 || currentOffset == -1 || currentOffset == 7))  ||
                       (file == 8 && (currentOffset == -7|| currentOffset == 1 || currentOffset == 9));
            }
            case ROOK -> {
                return (file == 1 && currentOffset == -1)  ||
                       (file == 8 && currentOffset == 1);
            }
            default -> {
                return false;
            }
        }
    }

    /**
     * Make the sliding piece capture another piece.
     *
     * @param board               where the capture will occur
     * @param destinationPosition where the sliding piece wants to move to capture
     * @param pieceAlliance       the sliding piece's alliance
     * @param slidingPiece        the piece performing the attack
     * @param legalMoves          the sliding piece's legal moves
     */
    private static void PerformAttackMove(final Board board,
                                          final int destinationPosition,
                                          final Alliance pieceAlliance,
                                          final Piece slidingPiece,
                                          final List<Move> legalMoves) {
        // Determine the piece on the occupied tile
        final Piece pieceOnTile = board.getTile(destinationPosition).getPiece();
        // Determine whether the piece is the opponent's
        if (pieceAlliance != pieceOnTile.getPieceAlliance()) {
            // The move counts as attacking the opponent's piece
            legalMoves.add(new MajorAttackMove(board,
                    slidingPiece,
                    destinationPosition,
                    pieceOnTile));
        }
    }
}
