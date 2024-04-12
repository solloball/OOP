package ru.nsu.romanov.snake.components;

/**
 * Window state.
 */
public enum WindowSize {
    SIZE_1280X720(720, 1280, 1),
    SIZE_1600X900(900, 1600, 1.25f);


    /**
     * Constructor.
     *
     * @param height height to set.
     * @param width width to set.
     * @param scale scale to set.
     */
    WindowSize(int height, int width, float scale) {
        this.height = height;
        this.width = width;
        this.scale = scale;
    }

    /**
     * Get scale.
     *
     * @return scale.
     */
    public float getScale() {
        return scale;
    }

    /**
     * get width of window size.
     *
     * @return width of window size.
     */
    public float getWidth() {
        return width;
    }

    /**
     * Get height of window size.
     *
     * @return height of window.
     */
    public float getHeight() {
        return height;
    }

    private final int height;
    private final int width;
    private final float scale;

}
