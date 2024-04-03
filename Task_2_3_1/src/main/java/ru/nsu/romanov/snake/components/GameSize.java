package ru.nsu.romanov.snake.components;

/**
 * All types of game size.
 */
public enum GameSize {
    SIZE_10X10 {
        @Override
        public int getSize() {
            return 10;
        }
    },
    SIZE_30X30 {
        @Override
        public int getSize() {
            return 30;
        }
    }, SIZE_50X50 {
        @Override
        public int getSize() {
            return 50;
        }
    };

    /**
     * Return game size.
     *
     * @return size game.
     */
    public int getSize() {
        return -1;
    }
}
