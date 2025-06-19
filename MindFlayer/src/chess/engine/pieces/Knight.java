package chess.engine.pieces;

import chess.engine.board.Board;
import chess.engine.moves.AttackMove;
import chess.engine.moves.MajorMove;
import chess.engine.moves.Move;
import chess.engine.tiles.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static chess.engine.pieces.PieceUtils.*;
import static chess.engine.utils.Constants.PieceConstants.KNIGHT_OFFSETS;

public class Knight extends Piece {
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a Knight object.
     *
     * @param pieceAlliance White/Black
     * @param piecePosition where the Knight is on the board
     */
    public Knight(final Alliance pieceAlliance, final int piecePosition) {
        super(pieceAlliance, piecePosition);
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Calculates all legal moves for the Knight.
     *
     * @param board where the Knight will make a move
     * @return a Collection of all the Knight's legal moves
     */
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        // Iterate through all the offsets to determine the Knight's legal moves
        for (final int currentOffset : KNIGHT_OFFSETS) {
            // Calculate the destination position
            final int destinationPosition = this.piecePosition + currentOffset;
            // Determine if the destination position is on the board
            if (IsDestinationPositionValid(destinationPosition)) {
                // Determine whether the Knight is on the 1st, 2nd, 7th, or 8th file
                if (AnyKnightFileExclusions(this.piecePosition, destinationPosition)) {
                    // The current offset will break the Knight's movement, so move to the next offset
                    continue;
                }
                // Obtain the destination tile
                final Tile destinationTile = board.getTile(destinationPosition);
                if (!destinationTile.isTileOccupied()) {
                    // The move counts as moving to an empty tile
                    legalMoves.add(new MajorMove(board, this, destinationPosition));
                } else {
                    // Determine the piece on the occupied tile
                    final Piece pieceOnTile = board.getTile(destinationPosition).getPiece();
                    // Determine whether the piece is the opponent's
                    if (this.pieceAlliance != pieceOnTile.getPieceAlliance()) {
                        // The move counts as attacking the opponent's piece
                        legalMoves.add(new AttackMove(board, this, destinationPosition, pieceOnTile));
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------- Helper Methods ---------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @param currentPosition where the Knight is on the board
     * @param currentOffset   the current offset used for calculating the Knight's destination position
     * @return whether the Knight is on the first file with a faulty offset
     */
    private static boolean IsFirstFileExclusion(final int currentPosition, final int currentOffset) {
        return FIRST_FILE[currentPosition] &&
               (currentOffset == -17 ||
                currentOffset == -10 ||
                currentOffset == 6 ||
                currentOffset == 15);
    }

    /**
     * @param currentPosition where the Knight is on the board
     * @param currentOffset   the current offset used for calculating the Knight's destination position
     * @return whether the Knight is on the second file with a faulty offset
     */
    private static boolean IsSecondFileExclusion(final int currentPosition, final int currentOffset) {
        return SECOND_FILE[currentPosition] &&
                (currentOffset == -17 ||
                 currentOffset == -10);
    }

    /**
     * @param currentPosition where the Knight is on the board
     * @param currentOffset   the current offset used for calculating the Knight's destination position
     * @return whether the Knight is on the seventh file with a faulty offset
     */
    private static boolean IsSeventhFileExclusion(final int currentPosition, final int currentOffset) {
        return SEVENTH_FILE[currentPosition] &&
                (currentOffset == -17 ||
                 currentOffset == -10);
    }

    /**
     * @param currentPosition where the Knight is on the board
     * @param currentOffset   the current offset used for calculating the Knight's destination position
     * @return whether the Knight is on the eighth file with a faulty offset
     */
    private static boolean IsEighthFileExclusion(final int currentPosition, final int currentOffset) {
        return EIGHTH_FILE[currentPosition] &&
                (currentOffset == -15 ||
                 currentOffset == -6 ||
                 currentOffset == 10 ||
                 currentOffset == 17);
    }

    /**
     * A simpler way to determine whether the Knight is on the first, second, seventh, or eighth file.
     * For a Knight, a move cannot happen more than two files away.
     *
     * @param currentPosition     where the Knight currently is
     * @param destinationPosition where the Knight wants to move to
     * @return whether there are any exclusions for the Knight
     */
    private static boolean AnyKnightFileExclusions(final int currentPosition, final int destinationPosition) {
        // Calculate the current and destination files
        final int currentFile = (currentPosition % 8) + 1, destinationFile = (destinationPosition % 8) + 1;

        return Math.abs(currentFile - destinationFile) > 2;
    }
}
