package ru.nsu.romanov.snake;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Launcher extends Application{
    public void start(Stage primaryStage) throws IOException {

        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                new Game<>(primaryStage)));
        timeline.play();
    }
}