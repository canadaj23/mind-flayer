package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.moves.Move;
import com.chess.engine.moves.pawn.*;
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
                                            (this.getPieceAlliance().getDirection() * currentOffset);
            // Move on to the next offset if the tile ahead of the Pawn is occupied
            if (!IsDestinationPositionValid(destinationPosition)) {
                continue;
            }
            // Figure out what to do with an empty/occupied tile
            if (!board.getTile(destinationPosition).isTileOccupied()) {
                performPawnMove(board, destinationPosition, legalMoves, currentOffset);
            } else if ((currentOffset == 7 || currentOffset == 9) &&
                       !anyPawnFileExclusions(this.piecePosition, currentOffset)) {
                performPawnAttack(board, destinationPosition, legalMoves, currentOffset);
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    /**
     * Determines which Pawn advancement to perform.
     * It will either be a one-tile advance or two-tile advance.
     *
     * @param board               where the Pawn advancement takes place
     * @param destinationPosition where the Pawn will move to
     * @param legalMoves          all the Pawn's legal moves
     * @param currentOffset       the current offset for the Pawn's advancement
     */
    private void performPawnMove(final Board board,
                                 final int destinationPosition,
                                 final List<Move> legalMoves,
                                 final int currentOffset) {
        if (currentOffset == 8) {
            // One-tile advance with possible Pawn promotion
            if (this.pieceAlliance.isPawnPromotionSquare(destinationPosition)) {
                // Pawn promotion
                legalMoves.add(new PawnPromotion(new PawnMove(board, this, destinationPosition)));
            } else {
                // No Pawn promotion
                legalMoves.add(new PawnMove(board, this, destinationPosition));
            }
        }
        else if (currentOffset == 16) {
            if (this.isFirstMove() && pawnInInitialPosition()) {
                // Determine the position between the start and destination positions
                final int positionBetweenStartDestination = this.piecePosition +
                        (this.pieceAlliance.getDirection() * 8);
                // Determine if the first tile in front of the Pawn is empty (the second one was checked above)
                if (!board.getTile(positionBetweenStartDestination).isTileOccupied()) {
                    // Two-tile advance
                    legalMoves.add(new PawnJump(board, this, destinationPosition));
                }
            }
        }
    }

    /**
     * Determines which Pawn attack to perform.
     * It will either be a standard Pawn attack or an En Passant attack.
     *
     * @param board               where the Pawn attack takes place
     * @param destinationPosition where the Pawn will capture
     * @param legalMoves          all the Pawn's legal moves
     * @param currentOffset       the current offset for the Pawn's attack
     */
    private void performPawnAttack(final Board board,
                                   final int destinationPosition,
                                   final List<Move> legalMoves,
                                   final int currentOffset) {
        // Determine whether the tile is occupied
        if (board.getTile(destinationPosition).isTileOccupied()) {
            // Determine the piece on the occupied tile
            final Piece pieceAtDestination = board.getTile(destinationPosition).getPiece();
            // Determine whether the piece is friendly
            if (this.pieceAlliance != pieceAtDestination.getPieceAlliance()) {
                // Pawn attack with possible Pawn promotion
                if (this.pieceAlliance.isPawnPromotionSquare(destinationPosition)) {
                    // Pawn promotion
                    legalMoves.add(new PawnPromotion(new PawnAttackMove(board,
                            this,
                            destinationPosition,
                            pieceAtDestination)));
                } else {
                    // No Pawn promotion
                    legalMoves.add(new PawnAttackMove(board,
                            this,
                            destinationPosition,
                            pieceAtDestination));
                }
            }
        } else if (board.getEnPassantPawn() != null) {
            // See if an En Passant attack can be made
            performEnPassantAttack(board,
                    this.piecePosition,
                    currentOffset,
                    this.pieceAlliance,
                    destinationPosition,
                    legalMoves);
        }
    }

    /**
     * Performs a valid En Passant attack move.
     *
     * @param board               where the attack will take place
     * @param piecePosition       where the attacking Pawn currently is
     * @param currentOffset       the current offset of the attacking Pawn
     * @param pieceAlliance       the attacking Pawn's alliance
     * @param destinationPosition where the attacking Pawn wants to move to
     * @param legalMoves          all the Pawn's legal moves
     */
    private void performEnPassantAttack(final Board board,
                                        int piecePosition,
                                        final int currentOffset,
                                        final Alliance pieceAlliance,
                                        final int destinationPosition,
                                        final List<Move> legalMoves) {
        // Determine which side the En Passant Pawn will be on
        int adjacentPosition = 0;
        switch (currentOffset) {
            case 7 -> adjacentPosition = piecePosition + pieceAlliance.getOppositeDirection();
            case 9 -> adjacentPosition = piecePosition - pieceAlliance.getOppositeDirection();
        }
        // Determine whether the En Passant Pawn is adjacent to another Pawn
        if (board.getEnPassantPawn().getPiecePosition() == adjacentPosition) {
            // Get the piece at the destination (the En Passant Pawn)
            final Piece pieceAtDestination = board.getEnPassantPawn();
            // Determine whether the En Passant Pawn is friendly
            if (pieceAlliance != pieceAtDestination.getPieceAlliance()) {
                // Capture the En Passant Pawn
                legalMoves.add(new PawnEnPassantAttackMove(board,
                        this,
                        destinationPosition,
                        pieceAtDestination));
            }
        }
    }

    /**
     * @param move what is forcing an updated piece to be made
     * @return the moved piece at its destination position after a move is made
     */
    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getMovedPiece().getPieceAlliance(), move.getDestinationPosition());
    }

    /**
     * There will not be underpromotion (at least for now) for simplicity.
     *
     * @return a new Queen
     */
    public Piece getPromotionPiece() {
        return new Queen(this.pieceAlliance, this.piecePosition, false);
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
