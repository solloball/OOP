package ru.nsu.romanov.checker.server.net.task;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class which observe for tasks.
 */
public class TaskManager {
    /**
     * Default constructor.
     *
     * @param array all array of number.
     * @param tasksCount count clients.
     */
    public TaskManager(List<Integer> array, int tasksCount) {
        int count = min(array.size(), tasksCount);
        int butchSize = max(array.size() / (count + 1), 1);

        for (int i = 0; i < count; i++) {
            TaskId id = new TaskId(tasks.size());
            List<Integer> subArr = array.subList(
                    min(i * butchSize, array.size() - 1),
                    min((i + 1) * butchSize, array.size())
            );
            Task task = new Task(id, subArr);
            tasks.put(id, task);
            activeTasks.add(id);
        }
    }

    /**
     * Make task runnable and give it.
     *
     * @return task.
     */
    public Task runTask() {
        var id = activeTasks.removeFirst();
        runningTasks.add(id);
        return tasks.get(id);
    }

    /**
     * Reject task.
     *
     * @param id id of task.
     */
    public void rejectTask(TaskId id) {
        if (runningTasks.remove(id)) {
            activeTasks.add(id);
        }
    }

    /**
     * FinisH task.
     *
     * @param id if of task.
     */
    public void finishTask(TaskId id) {
        runningTasks.remove(id);
    }

    /**
     * Find if there is active tasks.
     *
     * @return true if there is a least one active task, otherwise false.
     */
    public boolean hasActiveTask() {
        return !activeTasks.isEmpty();
    }

    /**
     * Find whether there are some tasks to run.
     *
     * @return true if all tasks was made, false otherwise.
     */
    public boolean isFinished() {
        return activeTasks.isEmpty() && runningTasks.isEmpty();
    }

    private final Map<TaskId, Task> tasks = new HashMap<>();
    private final List<TaskId> activeTasks = new LinkedList<>();
    private final List<TaskId> runningTasks = new LinkedList<>();
}
