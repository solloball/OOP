package ru.nsu.romanov.snake;

import java.io.IOException;
import java.util.stream.IntStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import ru.nsu.romanov.snake.components.Direction;
import ru.nsu.romanov.snake.components.Position;
import ru.nsu.romanov.snake.components.Snake;
import ru.nsu.romanov.snake.components.StateGame;
import ru.nsu.romanov.snake.components.WindowSize;

/**
 * Class which respond for ui.
 *
 * @param <T> event.
 */
public class Display<T extends ActionEvent> {

    /**
     * Constructor.
     *
     * @param stage stage to set.
     * @param game game to attach.
     */
    public Display(Stage stage, Game<T> game) {
        this.stage = stage;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
            gameZone = new Scene(loader.load());
            controllerGame = loader.getController();
            controllerGame.init(background, this, game);

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

    /**
     * Draw in x, y position.
     *
     * @param position position to draw.
     * @param color color to draw.
     */
    public void draw(Position position, Color color) {
        controllerGame.draw(position, color);
    }

    /**
     * Clear all display.
     */
    public void clear() {
        IntStream.range(0, sizeGame)
                .forEach(x -> IntStream.range(0, sizeGame)
                        .forEach(y ->
                                controllerGame
                                        .draw(new Position(x, y), background)));
    }

    /**
     * Set score.
     *
     * @param score score to set.
     */
    public void setScore(int score) {
        controllerGame.setScore(score);
    }

    /**
     * set max score.
     *
     * @param score set to set.
     */
    public void setMaxScore(int score) {
        controllerGame.setMaxScore(score);
    }

    /**
     * Switch scene to game.
     */
    public void switchGame() {
        stage.setScene(gameZone);
    }

    /**
     * Switch scene to menu.
     */
    public void switchMenu() {
        stage.setScene(menu);
    }

    /**
     * Set scale of windowSize.
     *
     * @param windowSize used for finding scale.
     */
    public void setScale(WindowSize windowSize) {
        var scale = new Scale(
                windowSize.getScale(), windowSize.getScale(), 0, 0);
        menu.getRoot().getTransforms().setAll(scale);
        gameZone.getRoot().getTransforms().setAll(scale);
        stage.setHeight(windowSize.getHeight());
        stage.setWidth(windowSize.getWidth());
    }

    private final ControllerGame<T> controllerGame;
    private final Color background = Color.WHITESMOKE;
    private final Stage stage;
    private final Scene gameZone;
    private final Scene menu;
    private final int sizeGame = 10;
}
