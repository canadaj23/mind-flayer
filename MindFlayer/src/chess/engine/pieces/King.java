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

import static chess.engine.pieces.Piece.PieceType.KING;
import static chess.engine.pieces.PieceUtils.*;
import static chess.engine.utils.Constants.PieceConstants.QUEEN_KING_OFFSETS;

/**
 * This class represents the King chess piece.
 */
public class King extends Piece {
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a King object.
     *
     * @param pieceAlliance White/Black
     * @param piecePosition where the King is on the board
     */
    public King(final Alliance pieceAlliance, final int piecePosition) {
        super(pieceAlliance, piecePosition);
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Calculates all legal moves for the King.
     *
     * @param board where the King will make a move
     * @return a list of all the King's legal moves
     */
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        // Iterate through all the offsets to determine the King's legal moves
        for (final int currentOffset : QUEEN_KING_OFFSETS) {
            // Calculate the destination position
            final int destinationPosition = this.piecePosition + currentOffset;
            // Determine if the destination position is on the board
            if (IsDestinationPositionValid(destinationPosition)) {
                // Determine whether the King is on the 1st or 8th file
                if (AnyKingFileExclusions(this.piecePosition)) {
                    // The current offset will break the King's movement, so move to the next offset
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
     * @param currentPosition where the King is on the board
     * @return whether the King is on the first or eighth file with a faulty offset
     */
    private static boolean AnyKingFileExclusions(final int currentPosition) {
        // Calculate the current file
        final int currentFile = (currentPosition % 8) + 1;

        return currentFile == 1 || currentFile == 8;
    }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------- Special Overridden Methods ---------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return the King's first initial
     */
    @Override
    public String toString() {
        return KING.toString();
    }
}
