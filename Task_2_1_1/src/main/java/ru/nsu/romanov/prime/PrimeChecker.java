package ru.nsu.romanov.prime;

import java.util.Arrays;
import java.util.List;

public class PrimeChecker {
    private boolean isPrime(int number) {
        if (number == 2) {
            return true;
        }
        if (number < 2 || number % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public boolean hasPrimeStream(List<Integer> arr) {
        return arr.parallelStream().anyMatch(this::isPrime);
    }

    public boolean hasPrimeThread(List<Integer> arr, int threadsCount) {

        if (arr.isEmpty()) {
            return false;
        }

        if (threadsCount < 1) {
            throw new IllegalArgumentException("threads count must be more than zero");
        }

        PrimeCheckerThread[] threads = new PrimeCheckerThread[threadsCount];

        int butchSize = Math.max(arr.size() / threadsCount + 1, 1);

        for (int i = 0; i < threadsCount; i++) {
            threads[i] = new PrimeCheckerThread(arr.subList(
                    Math.min(i * butchSize, arr.size() - 1),
                    Math.min((i + 1) * butchSize, arr.size())
            ));
            threads[i].t.start();
        }

        Arrays.stream(threads).forEach(d -> {
            try {
                d.t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        return Arrays.stream(threads).anyMatch(PrimeCheckerThread::result);
    }

    public boolean hasPrimeSeq(List<Integer> arr) {
        return arr.stream().anyMatch(this::isPrime);
    }
}