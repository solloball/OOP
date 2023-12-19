package ru.nsu.romanov.calculator;

/**
 * Enum for store type of operation.
 * Store arity of this operation.
 */
public enum TypeOperation {
    ADD(2), SUB(2), MLT(2), DIV(2), SIN(1),
    COS(1), LOG(2), POW(2), SQRT(1);
    private final int arity;

    /**
     * Constructor for enum.
     *
     * @param arity arity of this operation.
     */
    TypeOperation(int arity) {
        this.arity = arity;
    }

    /**
     * getter for arity of operation.
     *
     * @return arity.
     */
    int getArity() {
        return arity;
    }
}
