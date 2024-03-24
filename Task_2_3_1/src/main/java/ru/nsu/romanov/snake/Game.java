package ru.nsu.romanov.snake;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.nsu.romanov.snake.components.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.max;

public class Game<T extends Event> implements EventHandler<T> {
    public Game(Stage primaryStage) {
        Position defaultPos = new Position(5, 5);
        Snake snake = new Snake(defaultPos);
        snakes.add(snake);
        display = new Display<>(primaryStage, this);
        init();
    }

    public void setLevel(Level level) {
        if (level == null) {
            return;
        }
        this.level = level;
        stateGame = StateGame.FINISHED;
    }

    public void setState(StateGame stateGame) {
        this.stateGame = stateGame;
    }

    public StateGame getState() {
        return stateGame;
    }
    public Snake getMainSnake() {
        return snakes.getFirst();
    }
    @Override
    public void handle(T event) {
        if (stateGame == StateGame.FINISHED) {
            display.clear();
            snakes.forEach(Snake::init);
            init();
            stateGame = StateGame.PAUSED;
            return;
        }

        if (stateGame == StateGame.PAUSED) {
            return;
        }

        snakes.forEach(snake -> {
            display.clear(snake.getTail());
            snake.move();
            display.draw(snake.getHead(), snakeColor);
        });

        snakes.forEach(snake -> {
            Position head = snake.getHead();
            if (isCollidePosVsFood(head)) {
                foodList.remove(new Food(head));
                snake.grow();
                display.draw(snake.getTail(), snakeColor);
                display.setScore(++score);
                maxScore = max(maxScore, score);
                display.setMaxScore(maxScore);
            }
        });

        if (snakes.stream().anyMatch(snake ->
                    isCollideSnakeVsSnakes(snake)
                    || isCollidePosVsObstacles(snake.getHead())
                    || snake.getHead().x() < 0
                    || snake.getHead().x() >= sizeGame
                    || snake.getHead().y() < 0
                    || snake.getHead().y() >= sizeGame)) {
                stateGame = StateGame.PAUSED;
                display.clear();
                snakes.forEach(Snake::init);
                init();
        }

        fillField();
    }

    private void init() {
        obstacles.clear();
        foodList.clear();

        snakes.forEach(snake -> display.draw(
                snake.getHead(), snakeColor));

        switch (level) {
            case EASY -> {
                countObstacles = 0;
                countFood = 1;
            }
            case HARD -> {
                countObstacles = 10;
                countFood = 3;
            }
            default -> throw new IllegalStateException("there is no this level");
        }
        fillField();
        display.setMaxScore(maxScore);
        score = 0;
        display.setScore(score);
    }

    private void fillField() {
        while (foodList.size() != countFood) {
            Position pos = getEmptyCell();
            if (pos == null) {
                throw new IllegalStateException("there is no empty cell");
            }
            foodList.add(new Food(pos));
            display.draw(pos, foodColor);
        }
        while (obstacles.size() != countObstacles) {
            Position pos = getEmptyCell();
            if (pos == null) {
                throw new IllegalStateException("there is no empty cell");
            }
            obstacles.add(new Obstacle(pos));
            display.draw(pos, obstacleColor);
        }
    }

    private Position getEmptyCell() {
        Random random = new Random();
        Position res = new Position(random.nextInt(sizeGame),
                random.nextInt(sizeGame));
        while (isCollidePosVsSnakes(res)
                || isCollidePosVsObstacles(res)
                || isCollidePosVsFood(res)) {
            res = new Position(random.nextInt(sizeGame),
                    random.nextInt(sizeGame));
        }
        return res;
    }

    private boolean isCollideSnakeVsSnakes(Snake snake) {
        if (snake.getBody()
                .subList(1, snake.getBody().size())
                .contains(snake.getHead())) {
            return true;
        }
        for (var sn : snakes) {
            if (sn == snake) {
                continue;
            }
            if (sn.getBody().contains(snake.getHead())) {
                return true;
            }
        }
        return false;
    }

    private boolean isCollidePosVsSnakes(Position position) {
        return snakes.stream().anyMatch(snake -> snake.getBody().stream().anyMatch(pos -> pos.equals(position)));
    }

    private boolean isCollidePosVsObstacles(Position position) {
        return obstacles.stream().anyMatch(obstacle -> obstacle.position().equals(position));
    }

    private boolean isCollidePosVsFood(Position position) {
        return foodList.stream().anyMatch(food -> food.position().equals(position));
    }


    private final Display<T> display;

    private Level level = Level.EASY;
    private StateGame stateGame = StateGame.PAUSED;
    private int countFood;
    private int countObstacles;

    private final Color foodColor = Color.RED;
    private final Color snakeColor = Color.DARKGREEN;
    private final Color obstacleColor = Color.GRAY;
    private final int sizeGame = 10;

    private final List<Food> foodList = new ArrayList<>();
    private final List<Snake> snakes = new ArrayList<>();
    private final List<Obstacle> obstacles = new ArrayList<>();
    private int maxScore = 0;
    private int score = 0;
}