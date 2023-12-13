package ru.nsu.romanov.calculator;

import java.lang.Math;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import org.jetbrains.annotations.NotNull;

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

        String operation = scanner.next();
        TypeOperation typeOperation = switch (operation) {
            case "+" -> TypeOperation.ADD;
            case "-" -> TypeOperation.SUB;
            case "/" -> TypeOperation.DIV;
            case "*" -> TypeOperation.MLT;
            case "sin" -> TypeOperation.SIN;
            case "cos" -> TypeOperation.COS;
            case "log" -> TypeOperation.LOG;
            case "sqrt" -> TypeOperation.SQRT;
            case "pow" -> TypeOperation.POW;
            default -> throw new IllegalStateException("Unexpected value: " + operation);
        };

        List<@NotNull Double> arguments = new LinkedList<>();
        for (int i = 0; i < typeOperation.getArity(); i++) {
            arguments.add(parser());
        }

        return switch (typeOperation) {
            case ADD -> arguments.get(0) + arguments.get(1);
            case SUB -> arguments.get(0) - arguments.get(1);
            case MLT -> arguments.get(0) * arguments.get(1);
            case DIV -> {
                double dividend = arguments.get(0);
                double divisor = arguments.get(1);
                if (divisor == 0) {
                    throw new ArithmeticException("Division by null exception: "
                            + dividend + " / 0");
                }
                yield dividend / arguments.get(1);
            }
            case SIN -> Math.sin(arguments.get(0));
            case COS -> Math.cos(arguments.get(0));
            case LOG -> {
                double base  = arguments.get(0);
                if (base <= 0) {
                    throw new ArithmeticException("First parameter in log must be above then zero");
                }
                if (base == 1) {
                    throw new ArithmeticException("First parameter must be not equal to 1");
                }
                double argument = arguments.get(1);
                if (argument <= 0) {
                    throw  new ArithmeticException("Second parameter must be above then zero");
                }
                yield Math.log(argument) / Math.log(base);
            }
            case POW -> {
                double argument = arguments.get(0);
                double degree = arguments.get(1);
                yield Math.pow(argument, degree);
            }
            case SQRT -> {
                double argument = arguments.get(0);
                if (argument < 0) {
                    throw new ArithmeticException("Argument is less than zero in sqrt");
                }
                yield Math.sqrt(argument);
            }
        };
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
            while (true) {
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