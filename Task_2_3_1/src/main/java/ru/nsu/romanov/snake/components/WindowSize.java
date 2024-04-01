package ru.nsu.romanov.snake.components;

/**
 * Window state.
 */
public enum WindowSize {
    SIZE_1280X720(0) {
        @Override
        public float getScale() {
            return 1;
        }

        @Override
        public float getHeight() {
            return 723;
        }

        @Override
        public float getWidth() {
            return 1280;
        }
    },
    SIZE_1600X900(1) {
        @Override
        public float getScale() {
            return 1.25f;
        }

        @Override
        public float getHeight() {
            return 900;
        }
        
        @Override
        public float getWidth() {
            return 1600;
        }
    };

    /**
     * Default constructor.
     *
     * @param i i to set.
     */
    WindowSize(int i) {
    }

    /**
     * Get scale.
     *
     * @return scale.
     */
    public float getScale() {
        return -1;
    }

    /**
     * get width of window size.
     *
     * @return width of window size.
     */
    public float getWidth() {
        return -1;
    }

    /**
     * Get height of window size.
     *
     * @return height of window.
     */
    public float getHeight() {
        return -1;
    }
}
