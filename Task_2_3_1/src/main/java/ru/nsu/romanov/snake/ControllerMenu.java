package ru.nsu.romanov.snake;

import java.util.EnumSet;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import ru.nsu.romanov.snake.components.GameSize;
import ru.nsu.romanov.snake.components.Level;
import ru.nsu.romanov.snake.components.WindowSize;

/**
 * Controller of menu.
 *
 * @param <T> event.
 */
public class ControllerMenu<T extends ActionEvent> {
    /**
     * Init controller.
     *
     * @param display display to attach.
     * @param game game to attach.
     */
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
                    var level = Level.values()[newValue.intValue()];
                    game.setLevel(level);
                    setStatus("Set level: " + level);
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
        //windowSize.setSelectionModel(windowSize.getSelectionModel());

        gameSize.setItems(
                FXCollections.observableList(
                        EnumSet
                                .allOf(GameSize.class)
                                .stream()
                                .toList()));
        gameSize.setConverter(new StringConverter<>() {
            @Override
            public String toString(GameSize gameSize) {
                if (gameSize == null) {
                    return null;
                }
                return gameSize.toString().substring(5);
            }

            @Override
            public GameSize fromString(String s) {
                return null;
            }
        });
        gameSize.getSelectionModel().selectedIndexProperty().addListener(
                (a, b, newValue) -> {
                    var size = GameSize.values()[newValue.intValue()];
                    display.setGameSize(size.getSize());
                    setStatus("Set game size: "
                            + size.toString().substring(5));
                }
        );

    }

    /**
     * Switch scene to game.
     */
    public void switchGame() {
        display.switchGame();
    }

    /**
     * Set status in status bar.
     *
     * @param text text to set.
     */
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
    @FXML
    private ChoiceBox<GameSize> gameSize;
}
