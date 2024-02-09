package ru.nsu.romanov.prime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Class for testing Composite checker.
 */
public class PrimeFinderTest {
    @Test
    void seqSimpleTest() {
        List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertTrue(primeChecker.hasCompositeSeq(list));
    }

    @Test
    void seqSimpleTestWithOnePrime() {
        List<Integer> list = List.of(20319251);
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertFalse(primeChecker.hasCompositeSeq(list));
    }

    @Test
    void seqSimpleTestEmptyList() {
        List<Integer> list = new ArrayList<>();
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertFalse(primeChecker.hasCompositeStream(list));
    }

    @Test
    void seqTestOnlyPrime() {
        List<Integer> list = List.of(
                20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053);
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertFalse(primeChecker.hasCompositeSeq(list));
    }


    @Test
    void streamSimpleTest() {
        List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertTrue(primeChecker.hasCompositeStream(list));
    }

    @Test
    void StreamSimpleTestWithOnePrime() {
        List<Integer> list = List.of(20319251);
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertFalse(primeChecker.hasCompositeStream(list));
    }

    @Test
    void streamSimpleTestEmptyList() {
        List<Integer> list = new ArrayList<>();
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertFalse(primeChecker.hasCompositeStream(list));
    }

    @Test
    void streamTestOnlyPrime() {
        List<Integer> list = List.of(
                20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053);
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertFalse(primeChecker.hasCompositeStream(list));
    }

    @Test
    void threadSimpleTest() {
        List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertTrue(primeChecker.hasCompositeThread(list, 1));
    }

    @Test
    void threadSimpleTestManyThreads() {
        List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertTrue(primeChecker.hasCompositeThread(list, 100));
    }

    @Test
    void threadSimpleTestWithOnePrimeManyThreads() {
        List<Integer> list = List.of(20319251);
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertFalse(primeChecker.hasCompositeThread(list, 100));
    }

    @Test
    void threadTestEmptyList() {
        List<Integer> list = new ArrayList<>();
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertFalse(primeChecker.hasCompositeThread(list, 100));
    }

    @Test
    void threadTestOnlyPrime() {
        List<Integer> list = List.of(
                20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053);
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertFalse(primeChecker.hasCompositeThread(list, 5));
    }
}
