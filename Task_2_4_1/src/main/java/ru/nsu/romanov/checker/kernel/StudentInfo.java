package ru.nsu.romanov.checker.kernel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Contains info about student checked tasks.
 */
public class StudentInfo {

    /**
     * Adds new task.
     *
     * @param task task to add.
     */
    public void addTask(TaskInfo task) {
        tasksInfo.removeIf(taskInfo ->
                taskInfo.getName().equals(task.getName()));
        tasksInfo.add(task);
    }

    /**
     * Gets task by name.
     *
     * @param name name for getting.
     * @return taskInfo.
     */
    public TaskInfo getTaskInfo(String name) {
        return tasksInfo
                .stream()
                .filter(taskInfo -> taskInfo.getName().equals(name))
                .findFirst()
                .get();
    }

    /**
     * Clear task info.
     */
    public void clear() {
        tasksInfo.clear();
    }

    /**
     * Override equals.
     *
     * @param o o to check for equals.
     * @return true if two objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StudentInfo that = (StudentInfo) o;
        return Objects.equals(tasksInfo, that.tasksInfo);
    }

    /**
     * Hashcode function.
     *
     * @return hash.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(tasksInfo);
    }

    private final List<TaskInfo> tasksInfo = new ArrayList<>();
}
