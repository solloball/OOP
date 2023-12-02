package ru.nsu.romanov.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest {
    @Test
    void checkMain() {
        Calculator calculator = new Calculator();
        calculator.setString("");
        System.out.println(calculator.solver());
    }
}