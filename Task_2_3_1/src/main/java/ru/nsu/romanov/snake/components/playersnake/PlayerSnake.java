package ru.nsu.romanov.snake.components.playersnake;

import ru.nsu.romanov.snake.components.Direction;
import ru.nsu.romanov.snake.components.Position;
import ru.nsu.romanov.snake.components.Snake;

import java.util.LinkedList;
import java.util.List;

public class PlayerSnake implements Snake {

    public PlayerSnake(Position initalPosition) {
        body.add(initalPosition);
    }

    @Override
    public void grow() {
        body.addLast(removedTail);
    }

    @Override
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

    @Override
    public List<Position> getBody() {
        return body;
    }

    @Override
    public Position getHead() {
        return body.getFirst();
    }

    @Override
    public Position getTail() {
        return body.getLast();
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction getDirection() {
        return lastDirection;
    }

    private Direction lastDirection;
    private Position removedTail;
    private Direction direction = Direction.RIGHT;
    private final List<Position> body = new LinkedList<>();
}
