package ru.nsu.romanov.prime;

import ru.nsu.romanov.prime.solver.Solver;
import ru.nsu.romanov.prime.solver.SolverSeq;

import java.util.List;

/**
 * class implementing runnable and stores Thread.
 */
public class CompositeCheckerThread implements Runnable {

    public final Thread thr = new Thread(this);
    private final List<Integer> list;
    private boolean res;

    /**
     * Constructor for CompositeCheckerThread.
     *
     * @param list list to check.
     */
    public CompositeCheckerThread(List<Integer> list) {
        this.list = list;
    }

    /**
     * return result of calculating.
     *
     * @return true if thread found at least one composite number, otherwise false.
     */
    public boolean result() {
        return res;
    }

    /**
     * Standard method for running threads.
     */
    @Override
    public void run() {
        Solver solver = new SolverSeq();

        res = solver.solve(list);
        if (res) {
            thr.interrupt();
        }
    }
}
