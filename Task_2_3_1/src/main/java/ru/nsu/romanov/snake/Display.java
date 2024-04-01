package ru.nsu.romanov.snake;

import java.io.IOException;
import java.util.stream.IntStream;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import ru.nsu.romanov.snake.components.*;

public class Display<T extends ActionEvent> {
    public Display(Stage stage, Game<T> game) {
        this.stage = stage;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
            gameZone = new Scene(loader.load());
            controller = loader.getController();
            controller.init(background, this, game);

            loader = new FXMLLoader(getClass().getResource("menu.fxml"));
            menu = new Scene(loader.load());
            ControllerMenu<T> controllerMenu = loader.getController();
            controllerMenu.init(this, game);
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e.getCause());
        }
        gameZone.setFill(background);
        Snake snake = game.getMainSnake();
        gameZone.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W -> {
                    if (snake.getDirection() != Direction.UP
                            || snake.getBody().size() == 1) {
                        snake.setDirection(Direction.DOWN);
                    }
                }
                case S -> {
                    if (snake.getDirection() != Direction.DOWN
                            || snake.getBody().size() == 1) {
                        snake.setDirection(Direction.UP);
                    }
                }
                case A -> {
                    if (snake.getDirection() != Direction.RIGHT
                            || snake.getBody().size() == 1) {
                        snake.setDirection(Direction.LEFT);
                    }
                }
                case D -> {
                    if (snake.getDirection() != Direction.LEFT
                            || snake.getBody().size() == 1) {
                        snake.setDirection(Direction.RIGHT);
                    }
                }
                case P -> {
                    if (game.getState() == StateGame.PAUSED) {
                        game.setState(StateGame.ACTIVE);
                    } else {
                        game.setState(StateGame.PAUSED);
                    }
                }
            }
        });

        stage.setTitle("Snake");
        stage.setScene(menu);
        stage.setResizable(false);
        stage.show();
    }

    public void draw(Position position, Color color) {
        controller.draw(position, color);
    }

    public void clear(Position position) {
        controller.draw(position, background);
    }

    public void clear() {
        IntStream.range(0, sizeGame)
                .forEach(x -> IntStream.range(0, sizeGame)
                        .forEach(y ->
                                controller
                                        .draw(new Position(x, y), background)));
    }

    public void setScore(int score) {
        controller.setScore(score);
    }

    public void setMaxScore(int score) {
        controller.setMaxScore(score);
    }

    public void switchGame() {
        stage.setScene(gameZone);
    }

    public void switchMenu() {
        stage.setScene(menu);
    }

    public void setScale(WindowSize windowSize) {
        var scale = new Scale(
                windowSize.getScale(), windowSize.getScale(), 0, 0);
        menu.getRoot().getTransforms().setAll(scale);
        gameZone.getRoot().getTransforms().setAll(scale);
        stage.setHeight(windowSize.getHeight());
        stage.setWidth(windowSize.getWidth());
    }

    private final Controller<T> controller;
    private final Color background = Color.WHITESMOKE;
    private final Stage stage;
    private final Scene gameZone;
    private final Scene menu;
    private final int sizeGame = 10;
}
