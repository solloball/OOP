package ru.nsu.romanov.prime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrimeFInderTest {
    @Test
    void SeqSimpleTest() {
        List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertTrue(primeChecker.hasCompositeSeq(list));
    }

    @Test
    void SeqSimpleTestWithOnePrime() {
        List<Integer> list = List.of(20319251);
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertFalse(primeChecker.hasCompositeSeq(list));
    }

    @Test
    void SeqSimpleTestEmptyList() {
        List<Integer> list = new ArrayList<>();
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertFalse(primeChecker.hasCompositeStream(list));
    }

    @Test
    void SeqTestOnlyPrime() {
        List<Integer> list = List.of(
                20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053);
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertFalse(primeChecker.hasCompositeSeq(list));
    }


    @Test
    void StreamSimpleTest() {
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
    void StreamSimpleTestEmptyList() {
        List<Integer> list = new ArrayList<>();
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertFalse(primeChecker.hasCompositeStream(list));
    }

    @Test
    void StreamTestOnlyPrime() {
        List<Integer> list = List.of(
                20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053);
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertFalse(primeChecker.hasCompositeStream(list));
    }

    @Test
    void ThreadSimpleTest() {
        List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertTrue(primeChecker.hasCompositeThread(list, 1));
    }

    @Test
    void ThreadSimpleTestManyThreads() {
        List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertTrue(primeChecker.hasCompositeThread(list, 100));
    }

    @Test
    void ThreadSimpleTestWithOnePrimeManyThreads() {
        List<Integer> list = List.of(20319251);
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertFalse(primeChecker.hasCompositeThread(list, 100));
    }

    @Test
    void ThreadTestEmptyList() {
        List<Integer> list = new ArrayList<>();
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertFalse(primeChecker.hasCompositeThread(list, 100));
    }

    @Test
    void ThreadTestOnlyPrime() {
        List<Integer> list = List.of(
                20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053);
        CompositeChecker primeChecker = new CompositeChecker();
        Assertions.assertFalse(primeChecker.hasCompositeThread(list, 5));
    }
}
