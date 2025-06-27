package com.chess.engine.pieces;

import com.chess.engine.players.BlackPlayer;
import com.chess.engine.players.Player;
import com.chess.engine.players.WhitePlayer;

public enum Alliance {
    WHITE {
        /**
         * @return the direction the Pawns go in a certain alliance
         */
        @Override
        public int getDirection() {
            return -1;
        }

        /**
         * @return the opposite direction of the Pawn
         */
        @Override
        public int getOppositeDirection() {
            return 1;
        }

        /**
         * @return whether the piece is white
         */
        @Override
        public boolean isWhite() {
            return true;
        }

        /**
         * @return whether the piece is black
         */
        @Override
        public boolean isBlack() {
            return false;
        }

        /**
         * @param whitePlayer White
         * @param blackPlayer Black
         * @return whose turn it is
         */
        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
            return whitePlayer;
        }
    },
    BLACK {
        /**
         * @return the direction the Pawns go in a certain alliance
         */
        @Override
        public int getDirection() {
            return 1;
        }

        /**
         * @return the opposite direction of the Pawn
         */
        @Override
        public int getOppositeDirection() {
            return -1;
        }

        /**
         * @return whether the piece is white
         */
        @Override
        public boolean isWhite() {
            return false;
        }

        /**
         * @return whether the piece is black
         */
        @Override
        public boolean isBlack() {
            return true;
        }

        /**
         * @param whitePlayer White
         * @param blackPlayer Black
         * @return whose turn it is
         */
        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) {
            return blackPlayer;
        }
    };
//----------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------- Abstract Methods --------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return the direction the Pawns go in a certain alliance
     */
    public abstract int getDirection();

    /**
     * @return whether the piece is white
     */
    public abstract boolean isWhite();

    /**
     * @return whether the piece is black
     */
    public abstract boolean isBlack();

    /**
     * @param whitePlayer White
     * @param blackPlayer Black
     * @return whose turn it is
     */
    public abstract Player choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer);

    /**
     * @return the opposite direction of the Pawn
     */
    public abstract int getOppositeDirection();
}
