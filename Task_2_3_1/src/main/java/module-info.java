module ru.nsu.romanov.snake {
    requires javafx.controls;
    requires javafx.fxml;

    opens ru.nsu.romanov.snake to javafx.fxml;
    exports ru.nsu.romanov.snake;
}