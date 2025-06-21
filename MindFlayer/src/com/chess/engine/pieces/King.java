package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.moves.other.AttackMove;
import com.chess.engine.moves.other.MajorMove;
import com.chess.engine.moves.Move;
import com.chess.engine.tiles.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.pieces.Piece.PieceType.KING;
import static com.chess.engine.pieces.PieceUtils.*;
import static com.chess.engine.utils.Constants.PieceConstants.QUEEN_KING_OFFSETS;

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
        super(KING, pieceAlliance, piecePosition);
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
                // Determine whether the King will be on the 1st or 8th file
                if (AnyKingFileExclusions(this.piecePosition, currentOffset)) {
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
                    if (this.pieceAlliance != pieceOnTile.getAlliance()) {
                        // The move counts as attacking the opponent's piece
                        legalMoves.add(new AttackMove(board, this, destinationPosition, pieceOnTile));
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
    public King movePiece(final Move move) {
        return new King(move.getMovedPiece().getAlliance(), move.getDestinationPosition());
    }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------- Helper Methods ---------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @param currentPosition where the King is on the board
     * @param currentOffset   the current offset used for calculating the King's destination position
     * @return whether the King is on the first or eighth file with a faulty offset
     */
    private static boolean AnyKingFileExclusions(final int currentPosition, final int currentOffset) {
        return (FIRST_FILE[currentPosition] && (currentOffset == -9 || currentOffset == -1 || currentOffset == 7))  ||
               (EIGHTH_FILE[currentPosition] && (currentOffset == -7|| currentOffset == 1 || currentOffset == 9));
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
