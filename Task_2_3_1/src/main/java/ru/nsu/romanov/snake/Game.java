package ru.nsu.romanov.snake;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.nsu.romanov.snake.components.*;
import ru.nsu.romanov.snake.components.playersnake.PlayerSnake;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game implements Runnable {
    public Game(Stage primaryStage, int widthWindow, int heightWindow, int heightGame, int widthGame) throws IOException {
        this.heightGame = heightGame;
        this.widthGame = widthGame;
        Snake snake = new PlayerSnake(new Position(widthGame / 2, heightGame / 2));
        snakes.add(snake);
        display = new Display(primaryStage, widthWindow, heightWindow, heightGame, widthGame, snake);
        init();
    }

    @Override
    public void run() {
        final long diff = 300;
        long prevFrame = System.currentTimeMillis();
        while(true) {

            if (System.currentTimeMillis() - prevFrame < diff) {
                continue;
            }

            snakes.forEach(snake -> {
                display.clear(snake.getTail().x(), snake.getTail().y());
                snake.move();
                display.draw(snake.getHead().x(), snake.getHead().y(), snakeColor);
            });

            snakes.forEach(snake -> {
                Position head = snake.getHead();
                if (isCollidePosVsFood(head)) {
                    foodList.remove(new Food(head));
                    snake.grow();
                    display.draw(snake.getTail().x(), snake.getTail().y(), snakeColor);
                }
            });

            if (snakes.stream().anyMatch(snake ->
                        isCollideSnakeVsSnakes(snake)
                        || isCollidePosVsObstacles(snake.getHead())
                        || snake.getHead().x() < 0
                        || snake.getHead().x() >= widthGame
                        || snake.getHead().y() < 0
                        || snake.getHead().y() >= heightGame)) {
                    break;
            }

            fillField();

            prevFrame = System.currentTimeMillis();
        }

        display.clear();
        display.switchScene();
    }

    private void init() {
        if (heightGame < 2 || widthGame < 2) {
            throw new IllegalStateException("height or weight must be more than one");
        }

        snakes.forEach(snake -> display.draw(
                snake.getHead().x(), snake.getHead().y(), snakeColor));

        switch (level) {
            case EASY -> {
                countObstacles = 0;
                countFood = 1;
            }
            default -> throw new IllegalStateException("there is no this level");
        }
        fillField();
    }

    private void fillField() {
        while (foodList.size() != countFood) {
            Position pos = getEmptyCell();
            if (pos == null) {
                throw new IllegalStateException("there is no empty cell");
            }
            foodList.add(new Food(pos));
            display.draw(pos.x(), pos.y(), foodColor);
        }
        while (obstacles.size() != countObstacles) {
            Position pos = getEmptyCell();
            if (pos == null) {
                throw new IllegalStateException("there is no empty cell");
            }
            obstacles.add(new Obstacle(pos));
            display.draw(pos.x(), pos.y(), obstacleColor);
        }
    }

    private Position getEmptyCell() {
        Random random = new Random();
        Position res = new Position(random.nextInt(widthGame),
                random.nextInt(heightGame));
        while (isCollidePosVsSnakes(res)
                || isCollidePosVsObstacles(res)
                || isCollidePosVsFood(res)) {
            res = new Position(random.nextInt(widthGame),
                    random.nextInt(heightGame));
        }
        return res;
    }

    private boolean isCollideSnakeVsSnakes(Snake snake) {
        if (snake.getBody().subList(1, snake.getBody().size()).contains(snake.getHead())) {
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

    private final Color foodColor = Color.RED;
    private final Color snakeColor = Color.DARKGREEN;
    private final Color obstacleColor = Color.GRAY;
    private final Display display;
    private Level level = Level.EASY;
    private int countFood;
    private int countObstacles;
    private final int heightGame;
    private final int widthGame;
    private final List<Food> foodList = new ArrayList<>();
    private final List<Snake> snakes = new ArrayList<>();
    private final List<Obstacle> obstacles = new ArrayList<>();
}