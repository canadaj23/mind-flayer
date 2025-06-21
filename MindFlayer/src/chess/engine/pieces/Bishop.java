package chess.engine.pieces;

import chess.engine.board.Board;
import chess.engine.moves.other.AttackMove;
import chess.engine.moves.other.MajorMove;
import chess.engine.moves.Move;
import chess.engine.tiles.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static chess.engine.pieces.Piece.PieceType.BISHOP;
import static chess.engine.pieces.PieceUtils.*;
import static chess.engine.utils.Constants.PieceConstants.BISHOP_OFFSETS;

/**
 * This class represents the Bishop chess piece.
 */
public class Bishop extends Piece {
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a Bishop object.
     *
     * @param pieceAlliance White/Black
     * @param piecePosition where the Bishop is on the board
     */
    public Bishop(final Alliance pieceAlliance, final int piecePosition) {
        super(BISHOP, pieceAlliance, piecePosition);
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Calculates all legal moves for the Bishop.
     *
     * @param board where the Bishop will make a move
     * @return a list of all the Bishop's legal moves
     */
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        // Iterate through all the offsets to determine the Bishop's legal moves
        for (final int currentOffset : BISHOP_OFFSETS) {
            // Calculate the destination position
            int destinationPosition = this.piecePosition;
            // Determine if the destination position is on the board
            while (IsDestinationPositionValid(destinationPosition)) {
                // Determine whether the Bishop is on the 1st or 8th file
                if (AnyBishopFileExclusions(this.piecePosition, currentOffset)) {
                    // The current offset will break the Bishop's movement, so move to the next offset
                    break;
                }
                // Increment the destination position using the current offset
                destinationPosition += currentOffset;
                // Determine whether the destination position is valid
                if (IsDestinationPositionValid(destinationPosition)) {
                    // Obtain the destination tile
                    final Tile destinationTile = board.getTile(destinationPosition);
                    if (!destinationTile.isTileOccupied()) {
                        // The move counts as moving to an empty tile
                        legalMoves.add(new MajorMove(board, this, destinationPosition));
                    } else {
                        // Determine the piece on the occupied tile
                        final Piece pieceOnTile = board.getTile(destinationPosition).getPiece();
                        // Determine whether the piece is the opponent's
                        if (this.pieceAlliance != pieceOnTile.getAlliance()) {
                            // The move counts as attacking the opponent's piece
                            legalMoves.add(new AttackMove(board, this, destinationPosition, pieceOnTile));
                        }
                        break;
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    /**
     * @param move what is forcing an updated piece to be made
     * @return the moved piece at its destination position after a move is made
     */
    @Override
    public Bishop movePiece(final Move move) {
        return new Bishop(move.getMovedPiece().getAlliance(), move.getDestinationPosition());
    }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------- Helper Methods ---------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @param currentPosition where the Bishop is on the board
     * @param currentOffset   the current offset used for calculating the Bishop's destination position
     * @return whether the Bishop is on the first or eighth file with a faulty offset
     */
    private static boolean AnyBishopFileExclusions(final int currentPosition, final int currentOffset) {
        return (FIRST_FILE[currentPosition] && (currentOffset == -9 || currentOffset == 7))  ||
               (EIGHTH_FILE[currentPosition] && (currentOffset == -7 || currentOffset == 9));
    }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------- Special Overridden Methods ---------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return the Bishop's first initial
     */
    @Override
    public String toString() {
        return BISHOP.toString();
    }
}
