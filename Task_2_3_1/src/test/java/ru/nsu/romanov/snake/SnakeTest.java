package ru.nsu.romanov.snake;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.romanov.snake.components.Direction;
import ru.nsu.romanov.snake.components.Position;
import ru.nsu.romanov.snake.components.Snake;

/**
 * Tests for snake.
 */
public class SnakeTest {
    @Test
    public void constructorTest() {
        Position expectedPosition = new Position(1, 2);
        Snake snake = new Snake(expectedPosition);
        Assertions.assertEquals(expectedPosition, snake.getHead());
        Assertions.assertEquals(1, snake.getBody().size());
    }

    @Test
    public void simpleTestMoveUp() {
        Position expectedPosition = new Position(1, 2);
        Snake snake = new Snake(expectedPosition);
        snake.setDirection(Direction.UP);
        snake.move();
        Assertions.assertEquals(new Position(1, 3), snake.getHead());
    }

    @Test
    public void simpleTestMoveRight() {
        Position expectedPosition = new Position(1, 2);
        Snake snake = new Snake(expectedPosition);
        snake.setDirection(Direction.RIGHT);
        snake.move();
        Assertions.assertEquals(new Position(2, 2), snake.getHead());
    }

    @Test
    public void simpleTestMoveLeft() {
        Position expectedPosition = new Position(1, 2);
        Snake snake = new Snake(expectedPosition);
        snake.setDirection(Direction.LEFT);
        snake.move();
        Assertions.assertEquals(new Position(0, 2), snake.getHead());
    }

    @Test
    public void simpleTestMoveDown() {
        Position expectedPosition = new Position(1, 2);
        Snake snake = new Snake(expectedPosition);
        snake.setDirection(Direction.DOWN);
        snake.move();
        Assertions.assertEquals(new Position(1, 1), snake.getHead());
    }

    @Test
    public void moveSnakeLengthOneTest() {
        Position expectedPosition = new Position(1, 2);
        Snake snake = new Snake(expectedPosition);
        snake.setDirection(Direction.UP);
        snake.move();
        Assertions.assertEquals(new Position(1, 3), snake.getHead());
    }

    @Test
    public void moveRightSnakeWithBodyTest() {
        Position expectedPosition = new Position(1, 2);
        Snake snake = new Snake(expectedPosition);
        snake.setDirection(Direction.UP);
        snake.grow();
        snake.grow();
        snake.setDirection(Direction.RIGHT);
        snake.move();
        var body = snake.getBody();
        Assertions.assertEquals(new Position(2, 3), body.getFirst());
        Assertions.assertEquals(new Position(1, 3), body.get(1));
        Assertions.assertEquals(new Position(1, 2), body.get(2));
    }

    @Test
    public void moveLeftSnakeWithBodyTest() {
        Position expectedPosition = new Position(1, 2);
        Snake snake = new Snake(expectedPosition);
        snake.setDirection(Direction.UP);
        snake.grow();
        snake.grow();
        snake.setDirection(Direction.LEFT);
        snake.move();
        var body = snake.getBody();
        Assertions.assertEquals(new Position(0, 3), body.getFirst());
        Assertions.assertEquals(new Position(1, 3), body.get(1));
        Assertions.assertEquals(new Position(1, 2), body.get(2));
    }

    @Test
    public void moveUpSnakeWithBodyTest() {
        Position expectedPosition = new Position(1, 2);
        Snake snake = new Snake(expectedPosition);
        snake.setDirection(Direction.RIGHT);
        snake.grow();
        snake.grow();
        snake.setDirection(Direction.UP);
        snake.move();
        var body = snake.getBody();
        Assertions.assertEquals(new Position(2, 3), body.getFirst());
        Assertions.assertEquals(new Position(2, 2), body.get(1));
        Assertions.assertEquals(new Position(1, 2), body.get(2));
    }

    @Test
    public void moveDownSnakeWithBodyTest() {
        Position expectedPosition = new Position(1, 2);
        Snake snake = new Snake(expectedPosition);
        snake.setDirection(Direction.RIGHT);
        snake.grow();
        snake.grow();
        snake.setDirection(Direction.DOWN);
        snake.move();
        var body = snake.getBody();
        Assertions.assertEquals(new Position(2, 1), body.getFirst());
        Assertions.assertEquals(new Position(2, 2), body.get(1));
        Assertions.assertEquals(new Position(1, 2), body.get(2));
    }

    @Test
    public void growAfterMoveTest() {
        Position expectedPosition = new Position(1, 2);
        Snake snake = new Snake(expectedPosition);
        snake.setDirection(Direction.UP);
        snake.move();
        snake.grow();
        var body = snake.getBody();
        Assertions.assertEquals(body.getFirst(), new Position(1, 3));
        Assertions.assertEquals(body.getLast(), new Position(1, 2));
    }
}
