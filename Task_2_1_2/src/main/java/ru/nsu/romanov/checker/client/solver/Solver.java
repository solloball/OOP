package ru.nsu.romanov.checker.client.solver;

import java.util.List;

/**
    Interface solver,
    it response for finding composite number in list.
 */
public interface Solver {

    /**
     * find is there composite number in list.
     *
     * @param arr list to check.
     * @return true if there is composite number.
     */
    boolean solve(List<Integer> arr);

    /**
     * check number for being composite.
     *
     * @param number number to check.
     * @return true if composite, false otherwise.
     */
    default boolean isComposite(int number) {
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
}
