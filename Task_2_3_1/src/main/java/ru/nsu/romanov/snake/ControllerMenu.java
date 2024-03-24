package ru.nsu.romanov.snake;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import ru.nsu.romanov.snake.components.Level;
import ru.nsu.romanov.snake.components.WindowSize;

import java.util.EnumSet;

public class ControllerMenu<T extends Event> {
    public void init(Display<T> display, Game<T> game) {
        this.display = display;
        level.setItems(
                FXCollections.observableList(
                        EnumSet
                        .allOf(Level.class)
                        .stream()
                        .toList()));
        level.getSelectionModel().selectedIndexProperty().addListener(
                (a, b, newValue) -> {
                    game.setLevel(
                            Level.values()[newValue.intValue()]);
                    setStatus("Set level: " +
                            Level.values()[newValue.intValue()]);
                });

        windowSize.setItems(
                FXCollections.observableList(
                        EnumSet
                        .allOf(WindowSize.class)
                        .stream()
                        .toList()));
        windowSize.setConverter(new StringConverter<>() {
            @Override
            public String toString(WindowSize windowSize) {
                if (windowSize == null) {
                    return null;
                }
                return windowSize.toString().substring(5);
            }

            @Override
            public WindowSize fromString(String s) {
                return null;
            }
        });
        windowSize.getSelectionModel().selectedIndexProperty().addListener(
                (a, b, newValue) -> {
                    display.setScale(WindowSize.values()[newValue.intValue()]);
                    setStatus("Set window size: "
                        + WindowSize.values()[newValue.intValue()]
                            .toString().substring(5));
                }
        );
    }

    public void switchGame(MouseEvent mouseEvent) {
        display.switchGame();
    }

    private void setStatus(String text) {
        statusBar.setText("Status bar:\n" + text);
    }

    private Display<T> display;
    @FXML
    private ChoiceBox<Level> level;
    @FXML
    private ChoiceBox<WindowSize> windowSize;
    @FXML
    private Label statusBar;
}
