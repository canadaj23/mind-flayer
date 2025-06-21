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

import static chess.engine.pieces.Piece.PieceType.QUEEN;
import static chess.engine.pieces.PieceUtils.*;
import static chess.engine.utils.Constants.PieceConstants.QUEEN_KING_OFFSETS;

/**
 * This class represents the Queen chess piece.
 */
public class Queen extends Piece {
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a Queen object.
     *
     * @param pieceAlliance White/Black
     * @param piecePosition where the Queen is on the board
     */
    public Queen(final Alliance pieceAlliance, final int piecePosition) {
        super(QUEEN, pieceAlliance, piecePosition);
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Calculates all legal moves for the Queen.
     *
     * @param board where the Queen will make a move
     * @return a list of all the Queen's legal moves
     */
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        // Iterate through all the offsets to determine the Queen's legal moves
        for (final int currentOffset : QUEEN_KING_OFFSETS) {
            // Calculate the destination position
            int destinationPosition = this.piecePosition;
            // Determine if the destination position is on the board
            while (IsDestinationPositionValid(destinationPosition)) {
                // Determine whether the Queen will be on the 1st or 8th file
                if (AnyQueenFileExclusions(this.piecePosition, currentOffset)) {
                    // The current offset will break the Queen's movement, so move to the next offset
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
    public Queen movePiece(final Move move) {
        return new Queen(move.getMovedPiece().getPieceAlliance(), move.getDestinationPosition());
    }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------- Helper Methods ---------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @param currentPosition where the Queen is on the board
     * @param currentOffset   the current offset used for calculating the Queen's destination position
     * @return whether the Queen is on the first or eighth file with a faulty offset
     */
    private static boolean AnyQueenFileExclusions(final int currentPosition, final int currentOffset) {
        return (FIRST_FILE[currentPosition] && (currentOffset == -9 || currentOffset == -1 || currentOffset == 7))  ||
               (EIGHTH_FILE[currentPosition] && (currentOffset == -7|| currentOffset == 1 || currentOffset == 9));
    }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------- Special Overridden Methods ---------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return the Queen's first initial
     */
    @Override
    public String toString() {
        return QUEEN.toString();
    }
}
