package ru.nsu.romanov.prime;

import java.util.List;
import ru.nsu.romanov.prime.solver.Solver;
import ru.nsu.romanov.prime.solver.SolverSeq;

/**
 * class implementing runnable and stores Thread.
 */
public class CompositeCheckerThread implements Runnable {

    public final Thread thr = new Thread(this);
    private final List<Integer> list;
    private boolean res;
    private final List<CompositeCheckerThread> otherThreads;

    /**
     * Constructor for CompositeCheckerThread.
     *
     * @param list list to check.
     */
    public CompositeCheckerThread(List<Integer> list,
            List<CompositeCheckerThread> otherThreads) {
        this.otherThreads = otherThreads;
        this.list = list;
    }

    /**
     * return result of calculating.
     *
     * @return true if thread found at least one composite number,
     *      otherwise false.
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
        for (var elem : list) {
            if (thr.isInterrupted()) {
                return;
            }
            if (solver.isComposite(elem)) {
                res = true;
                //otherThreads.forEach(d -> d.thr.interrupt());
                for (var d : otherThreads) {
                    d.thr.interrupt();
                }
                return;
            }
        }
    }
}
