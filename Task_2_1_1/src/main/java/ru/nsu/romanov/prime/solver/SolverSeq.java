package ru.nsu.romanov.prime.solver;

import java.util.List;

/**
 * Solver which uses default stream to find composite number.
 */
public class SolverSeq implements Solver {
    @Override
    public boolean solve(List<Integer> arr) {
        return arr.stream().anyMatch(this::isComposite);
    }
}
