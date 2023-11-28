package ru.nsu.romanov.recordbook;

/**
 * Enum for storing mark.
 * THREE_RETAKED, FOUR_RETAKED, FIVE_RETAKED should be set if student retakes exam or test.
 */
public enum Mark {
    TWO {
        @Override
        public int toInt() {
            return 2;
        }
    },
    THREE {
        @Override
        public int toInt() {
            return 3;
        }
    },
    FOUR {
        @Override
        public int toInt() {
            return 4;
        }
    },
    FIVE {
        @Override
        public int toInt() {
            return 5;
        }
    },
    UNDEFINED,
    THREE_RETAKED {
        @Override
        public int toInt() {
            return 3;
        }
    },
    FOUR_RETAKED {
        @Override
        public int toInt() {
            return 4;
        }
    },
    FIVE_RETAKED {
        @Override
        public int toInt() {
            return 5;
        }
    };
    public int toInt() {
        return 0;
    }
}
