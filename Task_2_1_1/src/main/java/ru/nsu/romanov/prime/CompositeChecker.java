package ru.nsu.romanov.prime;

import java.util.Arrays;
import java.util.List;

public class CompositeChecker {
    private boolean isComposite(int number) {
        if (number == 2) {
            return false;
        }
        if (number < 2 || number % 2 == 0) {
            return true;
        }
        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCompositeStream(List<Integer> arr) {
        return arr.parallelStream().anyMatch(this::isComposite);
    }

    public boolean hasCompositeThread(List<Integer> arr, int threadsCount) {

        if (arr.isEmpty()) {
            return false;
        }

        if (threadsCount < 1) {
            throw new IllegalArgumentException("threads count must be more than zero");
        }

        CompositeCheckerThread[] threads = new CompositeCheckerThread[threadsCount];

        int butchSize = Math.max(arr.size() / threadsCount + 1, 1);

        for (int i = 0; i < threadsCount; i++) {
            threads[i] = new CompositeCheckerThread(arr.subList(
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

        return Arrays.stream(threads).anyMatch(CompositeCheckerThread::result);
    }

    public boolean hasCompositeSeq(List<Integer> arr) {
        return arr.stream().anyMatch(this::isComposite);
    }
}