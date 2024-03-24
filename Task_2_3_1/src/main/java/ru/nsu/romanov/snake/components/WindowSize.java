package ru.nsu.romanov.snake.components;

import javafx.fxml.FXML;

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

    WindowSize(int i) {
    }

    public float getScale() {
        return -1;
    }
    public float getWidth() {
        return -1;
    }
    public float getHeight() {
        return -1;
    }
}
