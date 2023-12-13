package ru.nsu.romanov.recordbook;

/**
 * Enum for storing mark.
 * THREE_RETAKED, FOUR_RETAKED, FIVE_RETAKED should be set if student retakes exam or test.
 */
public enum Mark {
    TWO(2), THREE(3), FOUR(4), FIVE(5),
    UNDEFINED(0), THREE_RETAKED(3),
    FOUR_RETAKED(4), FIVE_RETAKED(5);

    private final int mark;

    Mark(int mark) {
        this.mark = mark;
    }

    public int toInt() {
        return mark;
    }
}
