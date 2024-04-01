package ru.nsu.romanov.snake.components;

import java.util.LinkedList;
import java.util.List;

public class Snake {

    public Snake (Position initalPosition) {
        defaultPosition = initalPosition;
        body.add(initalPosition);
    }

    public void grow() {
        if (removedTail == null) {
            move();
        }
        body.addLast(removedTail);
    }

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

    public List<Position> getBody() {
        return body;
    }

    public Position getHead() {
        return body.getFirst();
    }

    public Position getTail() {
        return body.getLast();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return lastDirection;
    }

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
