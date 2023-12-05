package ru.nsu.romanov.calculator;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.lang.Math;

/**
 * Calculator.
 */
public class Calculator {
    private Scanner scanner = null;

    /**
     * Parse string and solve it.
     * If string is not valid, wil throw IllegalStateException,
     *     or ArithmeticException.
     *
     * @param string string to parse.
     * @return value of expression.
     */
    public @NotNull Double solver(String string) {
        if (string == null || string.isEmpty()) {
            throw new IllegalStateException("Input string is null or empty");
        }
        scanner = new Scanner(string);
        @NotNull Double ans = parser();
        if (scanner.hasNext()) {
            throw new IllegalStateException("Input string has too many numbers");
        }
        scanner.close();
        return ans;
    }

    /**
     * parse and solve string using scanner.
     * If string is not valid, wil throw IllegalStateException,
     *     or ArithmeticException.
     *
     * @return value of expression.
     */
    private @NotNull Double parser() {
        if (!scanner.hasNext()) {
            throw new IllegalStateException("Input string is not valid");
        }
        if (scanner.hasNextDouble()) {
            return scanner.nextDouble();
        }
        TypeOperation typeOperation;
        String operation = scanner.next();
        switch (operation) {
            case "+" ->
                typeOperation = TypeOperation.ADD;

            case "-" -> {
                typeOperation = TypeOperation.SUB;
            }
            case "/" -> {
                typeOperation = TypeOperation.DIV;
            }
            case "*" -> {
                typeOperation = TypeOperation.MLT;
            }
            case "sin" -> {
                typeOperation = TypeOperation.SIN;
            }
            case "cos" -> {
                typeOperation = TypeOperation.COS;
            }
            case "log" -> {
                typeOperation = TypeOperation.LOG;
            }
            case "sqrt" -> {
                typeOperation = TypeOperation.SQRT;
            }
            case "pow" -> {
                typeOperation = TypeOperation.POW;
            }
            default -> throw new IllegalStateException("Unexpected value: " + operation);
        }

        List<@NotNull Double> arguments = new LinkedList<>();
        for (int i = 0; i < typeOperation.getArity(); i++) {
            arguments.add(parser());
        }

        switch (typeOperation) {
            case ADD -> {
                return arguments.get(0) + arguments.get(1);
            }
            case SUB -> {
                return arguments.get(0) - arguments.get(1);
            }
            case MLT -> {
                return arguments.get(0) * arguments.get(1);
            }
            case DIV -> {
                if (arguments.get(1) == 0) {
                    throw new ArithmeticException("Division by null exception: " + arguments.get(0) + " / 0" );
                }
                return arguments.get(0) / arguments.get(1);
            }
            case SIN -> {
                return Math.sin(arguments.get(0));
            }
            case COS -> {
                return Math.cos(arguments.get(0));
            }
            case LOG -> {
                if (arguments.get(0) <= 0) {
                    throw new ArithmeticException("First parameter in log must be above then zero");
                }
                if (arguments.get(0) == 1) {
                    throw new ArithmeticException("First parameter must be not equal to 1");
                }
                if (arguments.get(1) <= 0) {
                    throw  new ArithmeticException("Second parameter must be above then zero");
                }
                return Math.log(arguments.get(1)) / Math.log(arguments.get(0));
            }
            case POW -> {
                return Math.pow(arguments.get(0), arguments.get(1));
            }
            case SQRT -> {
                if (arguments.get(0) < 0) {
                    throw new ArithmeticException("Argument is less than zero in sqrt");
                }
                return Math.sqrt(arguments.get(0));
            }
            default -> throw new IllegalStateException("Unexpected value: " + operation);
        }
    }

    /**
     * main function that run calculator.
     * It catches IllegalStateException and ArithmeticException,
     *     and writes their msg.
     * To exit, just write "exit" in one line.
     *
     * @param args args.
     */
    public static void main(String[] args) {
        try (Scanner inputScanner = new Scanner(System.in)) {
            Calculator calculator = new Calculator();
            while(true) {
                String input = inputScanner.nextLine();
                if (Objects.equals(input, "exit")) {
                    break;
                }
                try {
                    System.out.print(calculator.solver(input));
                } catch (IllegalStateException | ArithmeticException e) {
                    System.out.println(e);
                }
            }
        }
    }
}