package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.moves.Move;
import com.google.common.collect.ImmutableList;

import java.util.Collection;

import static com.chess.engine.pieces.Piece.PieceType.ROOK;
import static com.chess.engine.pieces.PieceUtils.*;
import static com.chess.engine.utils.Constants.PieceConstants.ROOK_OFFSETS;

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
        super(ROOK, pieceAlliance, piecePosition, true);
    }

    /**
     * Constructor for a Rook object.
     *
     * @param pieceAlliance White/Black
     * @param piecePosition where the Rook is on the board
     * @param firstMove     whether it is the Rook's first move
     */
    public Rook(final Alliance pieceAlliance, final int piecePosition, final boolean firstMove) {
        super(ROOK, pieceAlliance, piecePosition, firstMove);
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
        return ImmutableList.copyOf(DetermineSlidingPieceLegalMoves(ROOK_OFFSETS,
                                                                    this.piecePosition,
                                                                    board,
                                                                    this,
                                                                    this.pieceAlliance,
                                                                    this.getPieceType()));
    }

    /**
     * @param move what is forcing an updated piece to be made
     * @return the moved piece at its destination position after a move is made
     */
    @Override
    public Rook movePiece(final Move move) {
        return new Rook(move.getMovedPiece().getPieceAlliance(), move.getDestinationPosition());
    }
//----------------------------------------------------------------------------------------------------------------------
//--------------------------------------------- Special Overridden Methods ---------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return the Rook's first initial
     */
    @Override
    public String toString() {
        return ROOK.toString();
    }
}
