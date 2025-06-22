package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.moves.other.MajorMove;
import com.chess.engine.moves.Move;
import com.chess.engine.moves.pawn.PawnAttackMove;
import com.chess.engine.moves.pawn.PawnJump;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine.pieces.Piece.PieceType.PAWN;
import static com.chess.engine.pieces.PieceUtils.*;
import static com.chess.engine.utils.Constants.PieceConstants.PAWN_OFFSETS;

/**
 * This class represents the Pawn chess piece.
 */
public class Pawn extends Piece {
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Constructor -----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Constructor for a Pawn object.
     *
     * @param pieceAlliance White/Black
     * @param piecePosition where the Pawn is on the board
     */
    public Pawn(final Alliance pieceAlliance, final int piecePosition) {
        super(PAWN, pieceAlliance, piecePosition, true);
    }

    /**
     * Constructor for a Pawn object.
     *
     * @param pieceAlliance White/Black
     * @param piecePosition where the Pawn is on the board
     * @param firstMove     whether it is the Pawn's first move
     */
    public Pawn(final Alliance pieceAlliance, final int piecePosition, final boolean firstMove) {
        super(PAWN, pieceAlliance, piecePosition, firstMove);
    }
//----------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------- Main Methods ----------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * Calculates all legal moves for the Pawn.
     *
     * @param board where the Pawn will make a move
     * @return a list of all the Pawn's legal moves
     */
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        // Iterate through all the offsets to determine the Pawn's legal moves
        for (final int currentOffset : PAWN_OFFSETS) {
            // Calculate the destination position
            final int destinationPosition = this.piecePosition +
                                            (this.getPieceAlliance().getPawnDirection() * currentOffset);
            // Move on to the next offset if the tile ahead of the Pawn is occupied
            if (!IsDestinationPositionValid(destinationPosition)) {
                continue;
            }
            // Figure out what to do with an empty/occupied tile
            if (currentOffset == 8 && !board.getTile(destinationPosition).isTileOccupied()) {
                // TODO: implement one-tile advance with/without Pawn promotion
                // One-tile advance with possible Pawn promotion
                legalMoves.add(new MajorMove(board, this, destinationPosition));
            } else if (currentOffset == 16 && this.isFirstMove() && pawnInInitialPosition()) {
                // Determine the position between the start and destination positions
                final int positionBetweenStartDestination = this.piecePosition +
                                                            (this.pieceAlliance.getPawnDirection() * 8);
                // Determine if the two tiles in front of the Pawn are empty
                if (!board.getTile(positionBetweenStartDestination).isTileOccupied() &&
                    !board.getTile(destinationPosition).isTileOccupied()) {
                    // TODO: implement two-tile advance
                    // Two-tile advance
                    legalMoves.add(new PawnJump(board, this, destinationPosition));
                }
            } else if (currentOffset == 7 && !anyPawnFileExclusions(this.piecePosition, currentOffset)) {
                if (board.getTile(destinationPosition).isTileOccupied()) {
                    // Determine the piece on the occupied tile
                    final Piece pieceAtDestination = board.getTile(destinationPosition).getPiece();
                    // Determine whether the piece is friendly
                    if (this.pieceAlliance != pieceAtDestination.getPieceAlliance()) {
                        // TODO: implement standard Pawn attack
                        // Standard Pawn attack
                        legalMoves.add(new PawnAttackMove(board,
                                                          this,
                                                          destinationPosition,
                                                          pieceAtDestination));
                    }
                }
            } else if (currentOffset == 9 && !anyPawnFileExclusions(this.piecePosition, currentOffset)) {
                if (board.getTile(destinationPosition).isTileOccupied()) {
                    // Determine the piece on the occupied tile
                    final Piece pieceAtDestination = board.getTile(destinationPosition).getPiece();
                    // Determine whether the piece is friendly
                    if (this.pieceAlliance != pieceAtDestination.getPieceAlliance()) {
                        // TODO: implement En Passant attack
                        // En Passant attack
                        legalMoves.add(new PawnAttackMove(board,
                                                          this,
                                                          destinationPosition,
                                                          pieceAtDestination));
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
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getMovedPiece().getPieceAlliance(), move.getDestinationPosition());
    }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------------- Helper Methods ---------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return whether the Pawn is in its initial position
     */
    private boolean pawnInInitialPosition() {
        final int currentRank = (this.piecePosition / 8) + 1;
        return (this.getPieceAlliance().isBlack() && currentRank == 2) ||
               (this.getPieceAlliance().isWhite() && currentRank == 7);
    }

    /**
     * @param currentPosition where the Pawn is on the board
     * @param currentOffset   the offset used for calculating the Pawn's destination position
     * @return whether the Pawn is on the first or eighth file with a faulty offset
     */
    private boolean anyPawnFileExclusions(final int currentPosition, final int currentOffset) {
        switch (currentOffset) {
            case 7 -> {
                return (FIRST_FILE[currentPosition] && this.pieceAlliance.isBlack()) ||
                       (EIGHTH_FILE[currentPosition] && this.pieceAlliance.isWhite());
            }
            case 9 -> {
                return (FIRST_FILE[currentPosition] && this.pieceAlliance.isWhite()) ||
                       (EIGHTH_FILE[currentPosition] && this.pieceAlliance.isBlack());
            }
            default -> {return false;}
        }
    }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------- Special Overridden Methods ---------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return the Pawn's first initial
     */
    @Override
    public String toString() {
        return PAWN.toString();
    }
}
