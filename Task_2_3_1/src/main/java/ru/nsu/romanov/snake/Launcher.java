package ru.nsu.romanov.snake;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Thread thr = new Thread(
                new Game(primaryStage, widthWindow, heightWindow, 10, 10));
        thr.start();
    }

    private static final int widthWindow = 1920;
    private static final int heightWindow = 1080;
}