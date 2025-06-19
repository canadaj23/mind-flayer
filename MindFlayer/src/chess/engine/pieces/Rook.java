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
import static chess.engine.utils.Constants.PieceConstants.ROOK_OFFSETS;

/**
 * This class represents the Rook chess piece.
 */
public class Rook extends Piece {
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a Rook object.
     *
     * @param pieceAlliance White/Black
     * @param piecePosition where the Rook is on the board
     */
    public Rook(final Alliance pieceAlliance, final int piecePosition) {
        super(pieceAlliance, piecePosition);
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Calculates all legal moves for the Rook.
     *
     * @param board where the Rook will make a move
     * @return a list of all the Rook's legal moves
     */
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        // Iterate through all the offsets to determine the Rook's legal moves
        for (final int currentOffset : ROOK_OFFSETS) {
            // Calculate the destination position
            int destinationPosition = this.piecePosition + currentOffset;
            // Determine if the destination position is on the board
            while (IsDestinationPositionValid(destinationPosition)) {
                // Determine whether the Rook is on the 1st, 2nd, 7th, or 8th file
                if (AnyRookFileExclusions(this.piecePosition, currentOffset)) {
                    // The current offset will break the Rook's movement, so move to the next offset
                    break;
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
                    break;
                }
                destinationPosition += currentOffset;
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------- Helper Methods ---------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @param currentPosition where the Rook is on the board
     * @param currentOffset   the current offset used for calculating the Rook's destination position
     * @return whether the Rook is on the first file or eighth file with a faulty offset
     */
    private static boolean AnyRookFileExclusions(final int currentPosition, final int currentOffset) {
        return (FIRST_FILE[currentPosition] && currentOffset == -1) ||
               (EIGHTH_FILE[currentPosition] && currentOffset == 1);
    }
}
