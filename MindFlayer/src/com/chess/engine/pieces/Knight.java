package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.moves.other.AttackMove;
import com.chess.engine.moves.other.MajorAttackMove;
import com.chess.engine.moves.other.MajorMove;
import com.chess.engine.moves.Move;
import com.chess.engine.tiles.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.pieces.Piece.PieceType.KNIGHT;
import static com.chess.engine.pieces.PieceUtils.*;
import static com.chess.engine.utils.Constants.PieceConstants.KNIGHT_OFFSETS;

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
        super(KNIGHT, pieceAlliance, piecePosition, true);
    }

    /**
     * Constructor for a Knight object.
     *
     * @param pieceAlliance White/Black
     * @param piecePosition where the Knight is on the board
     * @param firstMove     whether it is the Knight's first move
     */
    public Knight(final Alliance pieceAlliance, final int piecePosition, final boolean firstMove) {
        super(KNIGHT, pieceAlliance, piecePosition, firstMove);
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
                // Determine whether the Knight will be on the 1st, 2nd, 7th, or 8th file
                if (AnyKnightFileExclusions(this.piecePosition, currentOffset)) {
                    // The current offset will break the Knight's movement, so move to the next offset
                    continue;
                }
                // Obtain the destination tile
                final Tile destinationTile = board.getTile(destinationPosition);
                // Determine whether the tile is empty
                if (!destinationTile.isTileOccupied()) {
                    // The move counts as moving to an empty tile
                    legalMoves.add(new MajorMove(board, this, destinationPosition));
                } else {
                    // Determine the piece on the occupied tile
                    final Piece pieceOnTile = board.getTile(destinationPosition).getPiece();
                    // Determine whether the piece is the opponent's
                    if (this.pieceAlliance != pieceOnTile.getPieceAlliance()) {
                        // The move counts as attacking the opponent's piece
                        legalMoves.add(new MajorAttackMove(board, this, destinationPosition, pieceOnTile));
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
    public Knight movePiece(final Move move) {
        return new Knight(move.getMovedPiece().getPieceAlliance(), move.getDestinationPosition());
    }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------- Helper Methods ---------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @param currentPosition where the Knight is on the board
     * @param currentOffset   the current offset used for calculating the Knight's destination position
     * @return whether the Knight is on the first, second, seventh, or eighth file with a faulty offset
     */
    private static boolean AnyKnightFileExclusions(final int currentPosition, final int currentOffset) {
        return (FIRST_FILE[currentPosition] &&
               (currentOffset == -17 ||
                currentOffset == -10 ||
                currentOffset == 6 ||
                currentOffset == 15))  ||
                (SECOND_FILE[currentPosition] &&
                (currentOffset == -10 ||
                 currentOffset == 6)) ||
                (SEVENTH_FILE[currentPosition] &&
                (currentOffset == -6 ||
                 currentOffset == 10)) ||
                (EIGHTH_FILE[currentPosition] &&
                (currentOffset == -15 ||
                 currentOffset == -6 ||
                 currentOffset == 10 ||
                 currentOffset == 17));
    }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------- Special Overridden Methods ---------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return the Knight's first initial
     */
    @Override
    public String toString() {
        return KNIGHT.toString();
    }
}
