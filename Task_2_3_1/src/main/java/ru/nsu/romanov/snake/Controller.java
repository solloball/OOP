package ru.nsu.romanov.snake;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ru.nsu.romanov.snake.components.Position;
import ru.nsu.romanov.snake.components.StateGame;

public class Controller <T extends ActionEvent> {
    public void init(Color background, Display<T> display, Game<T> game) {
        this.display = display;
        this.game = game;
        matrix.getChildren().clear();
        int heightWindow = 720;
        final int size = (heightWindow - 70) / 10;
        for (int y = 0; y < 10; y++){
            for (int x = 0; x < 10; x++) {
                Rectangle shape = new Rectangle(size, size);
                shape.setFill(background);
                shape.setStroke(Color.BLACK);

                // Iterate the Index using the loops
                GridPane.setRowIndex(shape, y);
                GridPane.setColumnIndex(shape, x);

                matrix.getChildren().add(shape);
            }
        }
    }

    public void switchMenu() {
        game.setState(StateGame.PAUSED);
        display.switchMenu();
    }

    public void draw(Position position, Color color) {
        int sizeGame = 10;
        if (position.x() < 0|| position.x() >= sizeGame || position.y() < 0 || position.y() >= sizeGame) {
            return;
        }
        Rectangle node = (Rectangle) matrix.getChildren().get(position.y() * sizeGame + position.x());
        node.setFill(color);
    }

    public void setScore(int scoreCount) {
        score.setText("Score " + scoreCount);
    }

    public void setMaxScore(int score) {
        maxScore.setText("Max score " + score);
    }

    @FXML
    private GridPane matrix;
    @FXML
    private Label score;
    @FXML
    private Label maxScore;
    private Display<T> display;
    private Game<T> game;
}
