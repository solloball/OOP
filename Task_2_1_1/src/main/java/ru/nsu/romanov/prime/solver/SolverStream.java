package ru.nsu.romanov.prime.solver;

import java.util.List;

/**
 * Solver which uses parallel stream to find composite number.
 */
public class SolverStream implements Solver {
    @Override
    public boolean solve(List<Integer> arr) {
        return arr.parallelStream()
                .anyMatch(this::isComposite);
    }
}
