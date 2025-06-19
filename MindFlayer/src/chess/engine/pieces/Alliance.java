package chess.engine.pieces;

public enum Alliance {
    WHITE {
        /**
         * @return the direction the Pawns go in a certain alliance
         */
        @Override
        public int getPawnDirection() {
            return -1;
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
    },
    BLACK {
        /**
         * @return the direction the Pawns go in a certain alliance
         */
        @Override
        public int getPawnDirection() {
            return 1;
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
    };
//----------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------- Abstract Methods --------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return the direction the Pawns go in a certain alliance
     */
    public abstract int getPawnDirection();

    /**
     * @return whether the piece is white
     */
    public abstract boolean isWhite();

    /**
     * @return whether the piece is black
     */
    public abstract boolean isBlack();
}
