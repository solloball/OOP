package ru.nsu.romanov.snake.components;

import java.util.LinkedList;
import java.util.List;

/**
 * Snake realization.
 */
public class Snake {

    /**
     * Constructor.
     *
     * @param initialPosition default position of snake.
     */
    public Snake(Position initialPosition) {
        defaultPosition = initialPosition;
        body.add(initialPosition);
    }

    /**
     * Grow snake.
     */
    public void grow() {
        if (removedTail == null) {
            move();
        }
        body.addLast(removedTail);
    }

    /**
     * Move ahead direction.
     */
    public void move() {
        Position current = body.getFirst();
        switch (direction) {
            case UP ->
                    body.addFirst(new Position(current.x(), current.y() + 1));
            case DOWN ->
                    body.addFirst(new Position(current.x(), current.y() - 1));
            case RIGHT ->
                    body.addFirst(new Position(current.x() + 1, current.y()));
            case LEFT ->
                    body.addFirst(new Position(current.x() - 1, current.y()));
            default -> {
                throw new IllegalStateException("there is no direction");
            }
        }
        removedTail = body.removeLast();
        lastDirection = direction;
    }

    /**
     * Get body.
     *
     * @return body of snake.
     */
    public List<Position> getBody() {
        return body;
    }

    /**
     * Get head of snake.
     *
     * @return head of snake.
     */
    public Position getHead() {
        return body.getFirst();
    }

    /**
     * Set direction of snake.
     *
     * @param direction direction to set.
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Get direction of snake.
     *
     * @return direction.
     */
    public Direction getDirection() {
        return lastDirection;
    }

    /**
     * Init snake.
     */
    public void init() {
        direction = Direction.UP;
        lastDirection = direction;
        body.clear();
        body.add(defaultPosition);
    }

    private final Position defaultPosition;
    private Direction lastDirection;
    private Position removedTail;
    private Direction direction = Direction.RIGHT;
    private final List<Position> body = new LinkedList<>();
}
