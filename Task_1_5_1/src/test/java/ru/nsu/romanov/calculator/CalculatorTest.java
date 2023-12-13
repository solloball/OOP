package ru.nsu.romanov.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for calculators.
 */
class CalculatorTest {

    @Test
    void checkZeroString_expectedIllegalStateE() {
        Calculator calculator = new Calculator();
        Assertions.assertThrows(IllegalStateException.class,
                () -> calculator.solver(""));
    }

    @Test
    void checkNull_expectedIllegalStateE() {
        Calculator calculator = new Calculator();
        Assertions.assertThrows(IllegalStateException.class,
                () -> calculator.solver(null));
    }

    @Test
    void checkToManyNumbers_expectedIllegalStateE() {
        Calculator calculator = new Calculator();
        Assertions.assertThrows(IllegalStateException.class,
                () -> calculator.solver("+ 0 0 0"));
    }

    @Test
    void checkUndefinedOperation_expectedIllegalStateE() {
        Calculator calculator = new Calculator();
        Assertions.assertThrows(IllegalStateException.class,
                () -> calculator.solver("cosh 3 5"));
    }

    @Test
    void checkOneNumber() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(3,
                calculator.solver("3"));
    }

    @Test
    void calculatorTestSub() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(-1,
                calculator.solver("- 1 2"));
    }

    @Test
    void calculatorTestAdd() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(32,
                calculator.solver("+ 30 2"));
    }

    @Test
    void calculatorTestDiv() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(15,
                calculator.solver("/ 30 2"));
    }

    @Test
    void calculatorTestDivZero_expArithE() {
        Calculator calculator = new Calculator();
        Assertions.assertThrows(ArithmeticException.class,
                () -> calculator.solver("/ 1 0"));
    }

    @Test
    void calculatorTestSqrt() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(Math.sqrt(3),
                calculator.solver("sqrt 3"));
    }

    @Test
    void calculatorTestSqrtNegative_expArithE() {
        Calculator calculator = new Calculator();
        Assertions.assertThrows(ArithmeticException.class,
                () -> calculator.solver("sqrt -3"));
    }

    @Test
    void calculatorTestMlt() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(-6,
                calculator.solver("* 3 -2"));
    }

    @Test
    void calculatorTestCos() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(Math.cos((5)),
                calculator.solver("cos 5"));
    }

    @Test
    void calculatorTestSin() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(Math.sin((5)),
                calculator.solver("sin 5"));
    }

    @Test
    void calculatorTestLog() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(Math.log(5) / Math.log(3),
                calculator.solver("log 3 5"));
    }

    @Test
    void calculatorTestZeroFirstArgument_expArithE() {
        Calculator calculator = new Calculator();
        Assertions.assertThrows(ArithmeticException.class,
                () -> calculator.solver("log 0 3"));
    }

    @Test
    void calculatorTestNegativeFirstArgument_expArithE() {
        Calculator calculator = new Calculator();
        Assertions.assertThrows(ArithmeticException.class,
                () -> calculator.solver("log -3 3"));
    }

    @Test
    void calculatorTestOneFirstArgument_expArithE() {
        Calculator calculator = new Calculator();
        Assertions.assertThrows(ArithmeticException.class,
                () -> calculator.solver("log 1 3"));
    }

    @Test
    void calculatorTestZeroSecondArgument_expArithE() {
        Calculator calculator = new Calculator();
        Assertions.assertThrows(ArithmeticException.class,
                () -> calculator.solver("log 3 0"));
    }

    @Test
    void calculatorTestNegativeSecondArgument_expArithE() {
        Calculator calculator = new Calculator();
        Assertions.assertThrows(ArithmeticException.class,
                () -> calculator.solver("log 3 -3"));
    }

    @Test
    void calculatorTestPow() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(Math.pow(5, 7),
                calculator.solver("pow 5 7"));
    }

    @Test
    void calculatorTestBigExpression() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(Math.pow((double) 4 / 9, Math.sqrt(Math.cos(5))),
                calculator.solver("pow / 4 + 4 5 sqrt cos 5"));
    }

    @Test
    void calculatorTestBigExpressionWithNegative() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(Math.pow((double) -4 / (4 - 5), Math.sqrt(Math.cos(-5))),
                calculator.solver("pow / -4 + 4 -5 sqrt cos -5"));
    }

    @Test
    void calculatorTestWithFloatValue() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals(Math.pow((double) -4.3 / (4.1 - 5.145), Math.sqrt(Math.cos(-5.35))),
                calculator.solver("pow / -4,3 + 4,1 -5,145 sqrt cos -5,35"));
    }

    @Test
    void realBigTest() {
        Calculator calculator = new Calculator();
        Assertions.assertEquals( (Math.cos(Math.sin(4) + Math.pow(3, Math.sqrt(2))))
                        / (-1 * 3),
                calculator.solver("/ cos + sin 4 pow 3 sqrt 2 * -1 3"));
    }
}