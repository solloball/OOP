package ru.nsu.romanov.checker.kernel;

import lombok.Data;

/**
 * Contains all info about checked task.
 */
@Data
public class TaskInfo {
    /**
     * Default constructor with name.
     *
     * @param name name to set.
     */
    public TaskInfo(String name) {
        this.name = name;
    }

    /**
     * Increments success task.
     */
    public void success() {
        total++;
        suc++;
    }

    /**
     * Increments fail task.
     */
    public void fail() {
        total++;
    }

    private String name;
    private String build = "-";
    private String javadoc = "-";
    private String javadocLink;
    private String tests = "-";
    private String testsLink;
    private String testsPercents;
    private String jacocoLink;
    private String jacoco = "-";
    private String jacocoPercents;
    private String linter = "-";
    private int total = 0;
    private int suc = 0;
}
