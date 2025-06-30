package com.chess.tests;

import com.chess.engine.board.Board;
import com.chess.engine.players.BlackPlayer;
import com.chess.engine.players.WhitePlayer;
import org.junit.jupiter.api.Test;

import static com.chess.engine.pieces.Alliance.BLACK;
import static com.chess.engine.pieces.Alliance.WHITE;
import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {
//----------------------------------------------------------------------------------------------------------------------
//------------------------------------------------ Test Initial Players ------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    @Test
    public void initialPlayers() {
        final Board board = Board.CreateStandardBoard();
        final WhitePlayer whitePlayer = board.getWhitePlayer();
        final BlackPlayer blackPlayer = board.getBlackPlayer();

        // White's alliance is WHITE and Black's alliance is BLACK
        assertEquals(WHITE, whitePlayer.getAlliance());
        assertEquals(BLACK, blackPlayer.getAlliance());

        // The current move maker should be White
        assertEquals(whitePlayer, board.getCurrentPlayer());

        // White's opponent should be Black and vice versa
        assertEquals(blackPlayer, whitePlayer.getOpponent());
        assertEquals(whitePlayer, blackPlayer.getOpponent());

        // Both players should have an active King
        assertNotNull(whitePlayer.getPlayerKing());
        assertNotNull(blackPlayer.getPlayerKing());

        // Both players should have 16 active pieces
        assertEquals(16, whitePlayer.getActivePieces().size());
        assertEquals(16, blackPlayer.getActivePieces().size());

        // Both players should have 20 initial legal moves
        assertEquals(20, whitePlayer.getLegalMoves().size());
        assertEquals(20, blackPlayer.getLegalMoves().size());
    }
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
}
