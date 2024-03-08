package ru.nsu.romanov.prime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.romanov.prime.solver.Solver;
import ru.nsu.romanov.prime.solver.SolverSeq;
import ru.nsu.romanov.prime.solver.SolverStream;
import ru.nsu.romanov.prime.solver.SolverThread;

/**
 * Class for testing Composite checker.
 */
public class PrimeFinderTest {
    @Test
    void seqSimpleTest() {
        List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);
        Solver primeChecker = new SolverSeq();
        Assertions.assertTrue(primeChecker.solve(list));
    }

    @Test
    void seqSimpleTestWithOnePrime() {
        List<Integer> list = List.of(20319251);
        Solver primeChecker = new SolverSeq();
        Assertions.assertFalse(primeChecker.solve(list));
    }

    @Test
    void seqSimpleTestEmptyList() {
        List<Integer> list = new ArrayList<>();
        Solver primeChecker = new SolverSeq();
        Assertions.assertFalse(primeChecker.solve(list));
    }

    @Test
    void seqTestOnlyPrime() {
        List<Integer> list = List.of(
                20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053);
        Solver primeChecker = new SolverSeq();
        Assertions.assertFalse(primeChecker.solve(list));
    }


    @Test
    void streamSimpleTest() {
        List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);
        Solver primeChecker = new SolverStream();
        Assertions.assertTrue(primeChecker.solve(list));
    }

    @Test
    void streamSimpleTestWithOnePrime() {
        List<Integer> list = List.of(20319251);
        Solver primeChecker = new SolverStream();
        Assertions.assertFalse(primeChecker.solve(list));
    }

    @Test
    void streamSimpleTestEmptyList() {
        List<Integer> list = new ArrayList<>();
        Solver primeChecker = new SolverStream();
        Assertions.assertFalse(primeChecker.solve(list));
    }

    @Test
    void streamTestOnlyPrime() {
        List<Integer> list = List.of(
                20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053);
        Solver primeChecker = new SolverStream();
        Assertions.assertFalse(primeChecker.solve(list));
    }

    @Test
    void threadSimpleTest() {
        List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);
        Solver primeChecker = new SolverThread(1);
        Assertions.assertTrue(primeChecker.solve(list));
    }

    @Test
    void threadSimpleTestManyThreads() {
        List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);
        Solver primeChecker = new SolverThread(100);
        Assertions.assertTrue(primeChecker.solve(list));
    }

    @Test
    void threadSimpleTestWithOnePrimeManyThreads() {
        List<Integer> list = List.of(20319251);
        Solver primeChecker = new SolverThread(100);
        Assertions.assertFalse(primeChecker.solve(list));
    }

    @Test
    void threadTestEmptyList() {
        List<Integer> list = new ArrayList<>();
        Solver primeChecker = new SolverThread(100);
        Assertions.assertFalse(primeChecker.solve(list));
    }

    @Test
    void threadTestOnlyPrime() {
        List<Integer> list = List.of(
                20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053);
        Solver primeChecker = new SolverThread(5);
        Assertions.assertFalse(primeChecker.solve(list));
    }
}
