package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.moves.Move;
import com.google.common.collect.ImmutableList;

import java.util.Collection;

import static com.chess.engine.pieces.Piece.PieceType.BISHOP;
import static com.chess.engine.pieces.PieceUtils.*;
import static com.chess.engine.utils.Constants.PieceConstants.BISHOP_OFFSETS;

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
        super(BISHOP, pieceAlliance, piecePosition, true);
    }

    /**
     * Constructor for a Bishop object.
     *
     * @param pieceAlliance White/Black
     * @param piecePosition where the Bishop is on the board
     * @param firstMove     whether it is the Bishop's first move
     */
    public Bishop(final Alliance pieceAlliance, final int piecePosition, final boolean firstMove) {
        super(BISHOP, pieceAlliance, piecePosition, firstMove);
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
        return ImmutableList.copyOf(DetermineSlidingPieceLegalMoves(BISHOP_OFFSETS,
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
    public Bishop movePiece(final Move move) {
        return new Bishop(move.getMovedPiece().getPieceAlliance(), move.getDestinationPosition());
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
