package ru.nsu.romanov.prime.solver;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.romanov.prime.CompositeCheckerThread;

/**
 * Solver which uses threads to find composite number.
 */
public class SolverThread implements Solver {

    /**
     * Constructor for solver Thread.
     *
     * @param countThreads count of threads which will be used.
     */
    public SolverThread(int countThreads) {
        this.countThreads = countThreads;
    }

    @Override
    public boolean solve(List<Integer> arr) {

        if (arr.isEmpty()) {
            return false;
        }

        if (countThreads < 1) {
            throw new IllegalArgumentException(
                    "threads count must be more than zero");
        }

        List<CompositeCheckerThread> threads = new ArrayList<>();

        int butchSize = Math.max(arr.size() / countThreads + 1, 1);

        for (int i = 0; i < countThreads; i++) {
            threads.add(new CompositeCheckerThread(arr.subList(
                    Math.min(i * butchSize, arr.size() - 1),
                    Math.min((i + 1) * butchSize, arr.size())
            ), threads));
            threads.get(i).thr.start();
        }

        try {
            for (var t : threads) {
                t.thr.join();
            }
        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted");
        }

        return threads.stream()
                .anyMatch(CompositeCheckerThread::result);
    }

    /**
     * getter for count of threads.
     *
     * @return counts of threads.
     */
    public int getCountThreads() {
        return countThreads;
    }

    /**
     * setter for counts of threads.
     *
     * @param countThreads count of threads to set.
     */
    public void setCountThreads(int countThreads) {
        this.countThreads = countThreads;
    }

    private int countThreads = 0;
}
