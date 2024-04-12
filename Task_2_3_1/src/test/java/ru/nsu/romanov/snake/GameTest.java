package ru.nsu.romanov.snake;

import javafx.event.ActionEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.romanov.snake.components.Direction;
import ru.nsu.romanov.snake.components.Food;
import ru.nsu.romanov.snake.components.Level;
import ru.nsu.romanov.snake.components.Position;
import ru.nsu.romanov.snake.components.StateGame;

/**
 * Tests for game.
 */
public class GameTest {
    @Test
    public void constructorTest() {
        Game<ActionEvent> game = new Game<>();
        Assertions.assertEquals(StateGame.PAUSED, game.getState());
    }

    @Test
    public void testHandle() {
        Game<ActionEvent> game = new Game<>();
        var snake = game.getMainSnake();
        snake.setDirection(Direction.UP);
        game.setState(StateGame.ACTIVE);
        game.handle(null);
        game.handle(null);
        game.handle(null);
        Assertions.assertEquals(4, snake.getHead().x(), 4);
        Assertions.assertEquals(8, snake.getHead().y());
    }

    @Test
    public void testFinishingGame() {
        Game<ActionEvent> game = new Game<>();
        var snake = game.getMainSnake();
        snake.setDirection(Direction.UP);
        game.setState(StateGame.ACTIVE);
        game.handle(null);
        game.handle(null);
        game.handle(null);
        game.handle(null);
        game.handle(null);
        Assertions.assertEquals(StateGame.PAUSED, game.getState());
    }

    @Test
    public void testPause() {
        Game<ActionEvent> game = new Game<>();
        var snake = game.getMainSnake();
        snake.setDirection(Direction.UP);
        for (int i = 0; i < 100; i++) {
            game.handle(null);
        }
        Assertions.assertEquals(new Position(5, 5), snake.getHead());
    }

    @Test
    public void testCollideFood() {
        Game<ActionEvent> game = new Game<>();
        var snake = game.getMainSnake();
        snake.setDirection(Direction.UP);
        game.setLevel(Level.EASY);
        game.setState(StateGame.ACTIVE);
        game.getFoodList().removeLast();
        game.getFoodList().add(new Food(new Position(5, 6)));
        game.handle(null);
        Assertions.assertEquals(2, snake.getBody().size());
    }
}
