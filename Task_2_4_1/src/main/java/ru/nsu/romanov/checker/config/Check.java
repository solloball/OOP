package ru.nsu.romanov.checker.config;

import java.time.Duration;

/**
 * Contain info about checks.
 */
public class Check {
    public String studentName;
    public String taskName;
    public Duration limitTask = Duration.ofMinutes(1);

    /**
     * Setter for student name.
     *
     * @param studentName student name to use.
     */
    public void studentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * Setter for task name.
     *
     * @param taskName task name for set.
     */
    public void taskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Setter for limit in minute.
     *
     * @param limit limit time for one task in minute.
     */
    public void limit(int limit) {
        limitTask = Duration.ofMinutes(limit);
    }
}
