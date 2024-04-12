package ru.nsu.romanov.snake;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Launcher of game.
 */
public class Launcher extends Application {

    /**
     * Init display, game, attach it, start rendering.
     *
     * @param primaryStage stage.
     */
    public void start(Stage primaryStage) {
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        Game<ActionEvent> game = new Game<>();
        Display<ActionEvent> display = new Display<>(primaryStage, game);
        game.attachDisplay(display);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100),
                game));
        timeline.play();
    }

    /**
     * main method.
     *
     * @param args unused.
     */
    public static void main(String[] args) {
        launch(args);
    }
}