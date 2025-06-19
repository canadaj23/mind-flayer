package chess.engine.pieces;

import chess.engine.board.Board;
import chess.engine.moves.MajorMove;
import chess.engine.moves.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static chess.engine.pieces.Piece.PieceType.PAWN;
import static chess.engine.pieces.PieceUtils.*;
import static chess.engine.utils.Constants.PieceConstants.PAWN_OFFSETS;

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
        super(pieceAlliance, piecePosition);
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
        // Iterate through all the offsets to determine the Queen's legal moves
        for (final int currentOffset : PAWN_OFFSETS) {
            // Calculate the destination position
            final int destinationPosition = this.piecePosition +
                                            (this.getPieceAlliance().getPawnDirection() * currentOffset);
            // Move on to the next offset if the tile ahead of the Pawn is occupied
            if (!IsDestinationPositionValid(destinationPosition)) {
                continue;
            }
            // Figure out what to do with an empty/occupied tile
            if (!board.getTile(destinationPosition).isTileOccupied()) { // The destination tile is empty
                if (currentOffset == 8) {
                    // TODO: implement a standard one-tile advance and one that leads to Pawn promotion
                    // Perform a one-tile advance that could land on the first/eighth rank
                    legalMoves.add(new MajorMove(board, this, destinationPosition));
                } else if (currentOffset == 16) {
                    // Determine if it is the Pawn's first move
                    if (this.isFirstMove() && pawnInInitialPosition()) {
                        // Determine the tile position between the start and destination positions
                        final int positionBetweenStartDestination =  this.piecePosition +
                                (this.pieceAlliance.getPawnDirection() * 8);
                        // Determine if that tile is empty
                        if (!board.getTile(positionBetweenStartDestination).isTileOccupied()) {
                            // TODO: implement a two-tile advance
                            // Perform a two-tile advance
                            legalMoves.add(new MajorMove(board, this, destinationPosition));
                        }
                    }
                }
            } else { // The destination tile is occupied
                // Determine whether the current offset messes up the Pawn attack
                if (!AnyPawnFileExclusions(this.piecePosition, currentOffset)) {
                    // Determine the piece on the occupied tile
                    final Piece pieceAtDestination = board.getTile(destinationPosition).getPiece();
                    // Determine the other piece's alliance
                    if (this.pieceAlliance != pieceAtDestination.getPieceAlliance()) {
                        if (currentOffset == 7) {
                            // TODO: implement Pawn attack that could result in a Pawn promotion
                            // Perform a standard Pawn attack on the opponent's piece
                            legalMoves.add(new MajorMove(board, this, destinationPosition));
                        } else if (currentOffset == 9) {
                            // TODO: implement En Passant Pawn attack
                            // Perform a En Passant Pawn attack on the opponent's piece
                            legalMoves.add(new MajorMove(board, this, destinationPosition));
                        }
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
     * @return whether the Pawn is in its initial position
     */
    private boolean pawnInInitialPosition() {
        final int currentRank = (this.piecePosition / 8) + 1;
        return (this.getPieceAlliance().isBlack() && currentRank == 2) ||
               (this.getPieceAlliance().isWhite() && currentRank == 7);
    }

    /**
     * @param currentPosition where the Pawn is
     * @param currentOffset   the offset used for calculating the Pawn's destination position
     * @return whether the Pawn is on the first or eighth file with a faulty offset
     */
    private boolean AnyPawnFileExclusions(final int currentPosition, final int currentOffset) {
        // Calculate the current file
        final int currentFile = (currentPosition % 8) + 1;

        switch (currentOffset) {
            case 7 -> {
                return (currentFile == 1 && this.pieceAlliance.isBlack()) ||
                       (currentFile == 8 && this.pieceAlliance.isWhite());
            }
            case 9 -> {
                return (currentFile == 1 && this.pieceAlliance.isWhite()) ||
                       (currentFile == 8 && this.pieceAlliance.isBlack());
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
