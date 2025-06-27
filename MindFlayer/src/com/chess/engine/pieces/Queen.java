package com.chess.engine.pieces;

import com.chess.engine.board.Board;
import com.chess.engine.moves.Move;
import com.google.common.collect.ImmutableList;

import java.util.Collection;

import static com.chess.engine.pieces.Piece.PieceType.QUEEN;
import static com.chess.engine.pieces.PieceUtils.*;
import static com.chess.engine.utils.Constants.PieceConstants.QUEEN_KING_OFFSETS;

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
        super(QUEEN, pieceAlliance, piecePosition, true);
    }

    /**
     * Constructor for a Queen object.
     *
     * @param pieceAlliance White/Black
     * @param piecePosition where the Queen is on the board
     * @param firstMove     whether it is the Queen's first move
     */
    public Queen(final Alliance pieceAlliance, final int piecePosition, final boolean firstMove) {
        super(QUEEN, pieceAlliance, piecePosition, firstMove);
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
        return ImmutableList.copyOf(DetermineSlidingPieceLegalMoves(QUEEN_KING_OFFSETS,
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
    public Queen movePiece(final Move move) {
        return new Queen(move.getMovedPiece().getPieceAlliance(), move.getDestinationPosition());
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
