package com.chess.tests;

import com.chess.engine.board.Board;
import com.chess.engine.players.Player;
import org.junit.jupiter.api.Test;

import static com.chess.engine.board.Board.CreateStandardBoard;
import static org.junit.jupiter.api.Assertions.*;

class TestBoard {
//----------------------------------------------------------------------------------------------------------------------
//------------------------------------------------- Test Initial Board -------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    @Test
    public void initialBoard() {
        final Board board = CreateStandardBoard();

        determineInitialLegalMovesSize(board);
        determineInitialPlayerStatus(board.getCurrentPlayer());
        determineInitialPlayerCastles(board.getCurrentPlayer());
        determineInitialPlayers(board);
        determineInitialPlayerStatus(board.getCurrentPlayer().getOpponent());
        determineInitialPlayerCastles(board.getCurrentPlayer().getOpponent());

        // TODO: create StandardBoardEvaluator
//        assertEquals(new StandardBoardEvaluator.evaluate(board, 0, 0));
    }

    /**
     * Determines whether the player and opponent start with 20 legal moves.
     *
     * @param board what the game is played on
     *
     */
    private void determineInitialLegalMovesSize(final Board board) {
        // There should be 20 legal moves for the player and opponent
        assertEquals(20, board.getCurrentPlayer().getLegalMoves().size());
        assertEquals(20, board.getCurrentPlayer().getOpponent().getLegalMoves().size());
    }

    /**
     * Determines whether the player is initially not in check, checkmate, and stalemate.
     *
     * @param player White/Black
     */
    private void determineInitialPlayerStatus(final Player player) {
        // The player should not be in check, checkmate, and stalemate
        assertFalse(player.isInCheck());
        assertFalse(player.isInCheckmate());
        assertFalse(player.isInStalemate());
    }

    /**
     * Determines whether the player cannot initially castle.
     *
     * @param player White/Black
     */
    private void determineInitialPlayerCastles(final Player player) {
        // The player should not be able to castle
        assertFalse(player.isCastled());
        // TODO: create isCastleCapable methods
//        assertTrue(player.isKingSideCastleCapable());
//        assertTrue(player.isQueenSideCastleCapable());
    }

    /**
     * Determines whether White is the current player and Black is the opponent.
     *
     * @param board what the game is played on
     */
    private void determineInitialPlayers(final Board board) {
        // The current player should be White and opponent should be Black
        assertEquals(board.getWhitePlayer(), board.getCurrentPlayer());
        assertEquals(board.getBlackPlayer(), board.getCurrentPlayer().getOpponent());
    }
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
}