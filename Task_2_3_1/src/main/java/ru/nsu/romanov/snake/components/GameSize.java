package ru.nsu.romanov.snake.components;

/**
 * All types of game size.
 */
public enum GameSize {
    SIZE_10X10(10), SIZE_30X30(30), SIZE_50X50(50);

    /**
     * Constructor;
     *
     * @param size size to set.
     */
    GameSize(int size) {
        this.size = size;
    }

    /**
     * Return game size.
     *
     * @return size game.
     */
    public int getSize() {
        return size;
    }

    private final int size;
}
