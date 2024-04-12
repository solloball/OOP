package ru.nsu.romanov.snake;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import ru.nsu.romanov.snake.components.Food;
import ru.nsu.romanov.snake.components.Level;
import ru.nsu.romanov.snake.components.Obstacle;
import ru.nsu.romanov.snake.components.Position;
import ru.nsu.romanov.snake.components.Snake;
import ru.nsu.romanov.snake.components.StateGame;

/**
 * Game class.
 *
 * @param <T> type of event.
 */
public class Game<T extends ActionEvent> implements EventHandler<T> {

    /**
     * Constructor, init game logic.
     */
    public Game() {
        Position defaultPos = new Position(sizeGame / 2, sizeGame / 2);
        Snake snake = new Snake(defaultPos);
        snakes.add(snake);
        initGame();
    }

    /**
     * Set game size.
     *
     * @param sizeGame size game to set.
     */
    public void setGameSize(int sizeGame) {
        this.sizeGame = sizeGame;
        initGame();
        initDisplay();
    }

    /**
     * Attach display and init it.
     *
     * @param display display to attach.
     */
    public void attachDisplay(Display<T> display) {
        this.display = display;
        initDisplay();
    }

    /**
     * Set level of game.
     *
     * @param level level to set.
     */
    public void setLevel(Level level) {
        if (level == null) {
            return;
        }
        this.level = level;
        stateGame = StateGame.FINISHED;
    }

    /**
     * Set state.
     *
     * @param stateGame state to set.
     */
    public void setState(StateGame stateGame) {
        this.stateGame = stateGame;
    }

    /**
     * Get state.
     *
     * @return state.
     */
    public StateGame getState() {
        return stateGame;
    }

    /**
     * get main (is controlled by user) snake.
     *
     * @return snake.
     */
    public Snake getMainSnake() {
        return snakes.getFirst();
    }

    /**
     * Iteration of game.
     *
     * @param event event.
     */
    @Override
    public void handle(T event) {
        if (stateGame == StateGame.FINISHED) {
            snakes.forEach(Snake::init);
            initGame();
            if (display != null) {
                initDisplay();
            }
            stateGame = StateGame.PAUSED;
            return;
        }

        if (stateGame == StateGame.PAUSED) {
            return;
        }

        snakes.forEach(Snake::move);

        snakes.forEach(snake -> {
            Position head = snake.getHead();
            if (isCollidePosVsFood(head)) {
                foodList.remove(new Food(head));
                snake.grow();
                score++;
                maxScore = max(maxScore, score);
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
            snakes.forEach(Snake::init);
            initGame();
            if (display != null) {
                initDisplay();
            }
        }

        fillField();
        if (display != null) {
            initDisplay();
        }
    }

    /**
     * Get food list.
     *
     * @return list of food.
     */
    public List<Food> getFoodList() {
        return foodList;
    }

    /**
     * Init game logic.
     */
    private void initGame() {
        obstacles.clear();
        foodList.clear();

        switch (level) {
            case EASY -> {
                countObstacles = 0;
                countFood = 1;
            }
            case HARD -> {
                countObstacles = sizeGame * 3 / 10;
                countFood = max(sizeGame / 10, 1);
            }
            default -> throw new IllegalStateException("there is no this level");
        }
        fillField();
        score = 0;
    }

    /**
     * Init ui and display.
     */
    private void initDisplay() {
        assert display != null;
        display.clear();
        snakes.forEach(snake -> snake.getBody().forEach(node ->
                display.draw(node, snakeColor)));
        display.setMaxScore(maxScore);
        display.setScore(score);
        obstacles.forEach(obstacle -> display.draw(
                obstacle.position(), obstacleColor));
        foodList.forEach(food -> display.draw(
                food.position(), foodColor));

    }

    /**
     * Fill obstacles and food.
     */
    private void fillField() {
        while (foodList.size() != countFood) {
            Position pos = getEmptyCell();
            if (pos == null) {
                throw new IllegalStateException("there is no empty cell");
            }
            foodList.add(new Food(pos));
        }
        while (obstacles.size() != countObstacles) {
            Position pos = getEmptyCell();
            if (pos == null) {
                throw new IllegalStateException("there is no empty cell");
            }
            obstacles.add(new Obstacle(pos));
        }
    }

    /**
     * Used for finding empty cell for food and obstacles.
     *
     * @return empty obstacle.
     */
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

    /**
     * Collide test snake vs snakes.
     *
     * @param snake snake to check.
     * @return true of collide, false otherwise.
     */
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

    /**
     * Collide test position vs snakes.
     *
     * @param position position to check.
     * @return true if collides, false otherwise.
     */
    private boolean isCollidePosVsSnakes(Position position) {
        return snakes.stream().anyMatch(snake ->
                snake.getBody().stream().anyMatch(pos ->
                        pos.equals(position)));
    }

    /**
     * Collide test position vs obstacles.
     *
     * @param position position to check.
     * @return true if collides, false otherwise.
     */
    private boolean isCollidePosVsObstacles(Position position) {
        return obstacles.stream().anyMatch(obstacle ->
                obstacle.position().equals(position));
    }

    /**
     * Collide test position vs food.
     *
     * @param position position to check.
     * @return true if collides, false otherwise.
     */
    private boolean isCollidePosVsFood(Position position) {
        return foodList.stream().anyMatch(food ->
                food.position().equals(position));
    }

    private Display<T> display;
    private Level level = Level.EASY;
    private StateGame stateGame = StateGame.PAUSED;
    private int countFood;
    private int countObstacles;
    private final Color foodColor = Color.RED;
    private final Color snakeColor = Color.DARKGREEN;
    private final Color obstacleColor = Color.GRAY;
    private int sizeGame = 10;
    private final List<Food> foodList = new ArrayList<>();
    private final List<Snake> snakes = new ArrayList<>();
    private final List<Obstacle> obstacles = new ArrayList<>();
    private int maxScore = 0;
    private int score = 0;
}