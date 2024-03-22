package ru.nsu.romanov.snake.components;

import java.util.List;

public interface Snake {
    void grow();

    void move();

    List<Position> getBody();

    Position getHead();

    Position getTail();

    void setDirection(Direction direction);

    Direction getDirection();
}
