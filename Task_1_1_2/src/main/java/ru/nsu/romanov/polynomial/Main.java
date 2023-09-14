package ru.nsu.romanov.polynomial;

/**
 * Main class in task 1_2_1
 */
public class Main {

    /**
     * Class polynomial is a1*x^n1 + a2*x^n2 + ... + am*x^nt
     */
    private static class polynomial {
        private node[] arr; // array which store all node
    }

    /**
     *  Class node is (coefficient) * x ^ (degree)
     */
    private static class node {

        private double degree;
        private double coefficient;

        public double getDegree() {
            return degree;
        }

        public double getCoefficient() {
            return coefficient;
        }

        public void setCoefficient(double newCoefficient) {
            coefficient = newCoefficient;
        }

        public void setDegree(double newDegree) {
            degree = newDegree;
        }
    }
}