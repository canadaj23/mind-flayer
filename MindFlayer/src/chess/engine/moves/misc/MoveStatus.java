package chess.engine.moves.misc;

/**
 * A move status indicates the state of the move.
 */
public enum MoveStatus {
    DONE {
        /**
         * @return whether the move is done
         */
        @Override
        public boolean isDone() {
            return true;
        }
    },
    ILLEGAL_MOVE {
        /**
         * @return whether the move is done
         */
        @Override
        public boolean isDone() {
            return false;
        }
    },
    LEAVES_PLAYER_IN_CHECK {
        /**
         * @return whether the move is done
         */
        @Override
        public boolean isDone() {
            return false;
        }
    };
//----------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------- Abstract Methods --------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------
    /**
     * @return whether the move is done
     */
    public abstract boolean isDone();
}
