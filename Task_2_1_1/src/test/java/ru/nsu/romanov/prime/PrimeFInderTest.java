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
        PrimeChecker primeChecker = new PrimeChecker();
        Assertions.assertTrue(primeChecker.hasPrimeSeq(list));
    }

    @Test
    void SeqSimpleTestWithOnePrime() {
        List<Integer> list = List.of(20319251);
        PrimeChecker primeChecker = new PrimeChecker();
        Assertions.assertTrue(primeChecker.hasPrimeSeq(list));
    }


    @Test
    void StreamSimpleTest() {
        List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);
        PrimeChecker primeChecker = new PrimeChecker();
        Assertions.assertTrue(primeChecker.hasPrimeStream(list));
    }

    @Test
    void StreamSimpleTestWithOnePrime() {
        List<Integer> list = List.of(20319251);
        PrimeChecker primeChecker = new PrimeChecker();
        Assertions.assertTrue(primeChecker.hasPrimeStream(list));
    }

    @Test
    void ThreadSimpleTest() {
        List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);
        PrimeChecker primeChecker = new PrimeChecker();
        Assertions.assertTrue(primeChecker.hasPrimeThread(list, 1));
    }

    @Test
    void ThreadSimpleTestManyThreads() {
        List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);
        PrimeChecker primeChecker = new PrimeChecker();
        Assertions.assertTrue(primeChecker.hasPrimeThread(list, 100));
    }

    @Test
    void ThreadSimpleTestWithOnePrimeManyThreads() {
        List<Integer> list = List.of(20319251);
        PrimeChecker primeChecker = new PrimeChecker();
        Assertions.assertTrue(primeChecker.hasPrimeThread(list, 100));
    }

    @Test
    void ThreadSimpleTestEmptyList() {
        List<Integer> list = new ArrayList<>();
        PrimeChecker primeChecker = new PrimeChecker();
        Assertions.assertFalse(primeChecker.hasPrimeThread(list, 100));
    }
}
