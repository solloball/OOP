package ru.nsu.romanov.checker.config;

/**
 * Contains info about extra points.
 */
public class ExtraPoints {
    public String studentName;
    public int points = 0;
    public String taskName;
    public String comment;
    public String type;

    /**
     * Setter for student name.
     *
     * @param name name to set.
     */
    public void studentName(String name) {
        this.studentName = name;
    }

    /**
     * Setter for task name.
     *
     * @param name name to set.
     */
    public void taskName(String name) {
        this.taskName = name;
    }

    /**
     * Setter for count points for the task.
     *
     * @param points points to set.
     */
    public void points(int points) {
        this.points = points;
    }

    /**
     * Setter for comment.
     *
     * @param comment comment to set.
     */
    public void comment(String comment) {
        this.comment = comment;
    }

    /**
     * Setter for type of work, like soft, hard or classwork.
     *
     * @param type type to set.
     */
    public void type(String type) {
        this.type = type;
    }
}
