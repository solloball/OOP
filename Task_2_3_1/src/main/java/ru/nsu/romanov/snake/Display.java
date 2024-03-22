package ru.nsu.romanov.snake;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ru.nsu.romanov.snake.components.Direction;
import ru.nsu.romanov.snake.components.Snake;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.IntStream;

public class Display {
    public Display(Stage stage, int widthWindow, int heightWindow,
                   int heightGame, int widthGame, Snake snake) throws IOException {

        this.stage = stage;
        this.heightGame = heightGame;
        this.widthGame = widthGame;

        for (int y = 0; y < heightGame; y++){
            for (int x = 0; x < widthGame; x++) {
                Rectangle shape = new Rectangle(20, 20, 70, 70);
                shape.setFill(background);
                shape.setStroke(Color.BLACK);

                // Iterate the Index using the loops
                GridPane.setRowIndex(shape, y);
                GridPane.setColumnIndex(shape,x);

                layoutGame.getChildren().add(shape);
            }
        }

        gameZone = new Scene(layoutGame, widthWindow, heightWindow);
        gameZone.setFill(background);
        gameZone.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP -> {
                    if (snake.getDirection() != Direction.UP
                            || snake.getBody().size() == 1) {
                        snake.setDirection(Direction.DOWN);
                    }
                }
                case DOWN -> {
                    if (snake.getDirection() != Direction.DOWN
                            || snake.getBody().size() == 1) {
                        snake.setDirection(Direction.UP);
                    }
                }
                case LEFT -> {
                    if (snake.getDirection() != Direction.RIGHT
                            || snake.getBody().size() == 1) {
                        snake.setDirection(Direction.LEFT);
                    }
                }
                case RIGHT -> {
                    if (snake.getDirection() != Direction.LEFT
                            || snake.getBody().size() == 1) {
                        snake.setDirection(Direction.RIGHT);
                    }
                }
            }
        });

        menu = FXMLLoader.load(
                Objects.requireNonNull(
                        getClass().getResource("menu.fxml")));
        menu.setFill(background);

        stage.setTitle("Snake");
        stage.setScene(menu);
        stage.setResizable(true);
        stage.show();
    }

    public void draw(int x, int y, Color color) {
        if (x < 0 || x >= widthGame || y < 0 || y >= heightGame) {
            return;
        }
        Rectangle node = (Rectangle) layoutGame.getChildren().get(y * widthGame + x);
        node.setFill(color);
    }

    public void clear(int x, int y) {
        if (x < 0 || x >= widthGame || y < 0 || y >= heightGame) {
            return;
        }
        Rectangle node = (Rectangle) layoutGame.getChildren().get(y * widthGame + x);
        node.setFill(background);
    }

    public void clear() {
        IntStream.range(0, widthGame).forEach(x ->
            IntStream.range(0, heightGame).forEach(y -> {
                Rectangle node = (Rectangle) layoutGame
                        .getChildren()
                        .get(y * widthGame + x);
                node.setFill(background);
            }));
    }

    public void switchScene() {
        //stage.setScene(menu);
    }

    private final Color background = Color.WHITESMOKE;
    private final GridPane layoutGame = new GridPane();
    private final Stage stage;
    private final int heightGame;
    private final int widthGame;
    private final Scene gameZone;
    private final Scene menu;

    @FXML
    private Button button;
}
