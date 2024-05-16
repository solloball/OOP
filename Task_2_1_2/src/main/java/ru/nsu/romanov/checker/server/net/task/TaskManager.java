package ru.nsu.romanov.checker.server.net.task;

import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class TaskManager {
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

    public Task runTask() {
        var id = activeTasks.removeFirst();
        runningTasks.add(id);
        return tasks.get(id);
    }

    public void rejectTask(TaskId id) {
        if (runningTasks.remove(id)) {
            activeTasks.add(id);
        }
    }

    public void finishTask(TaskId id) {
        runningTasks.remove(id);
    }

    public boolean hasActiveTask() {
        return !activeTasks.isEmpty();
    }

    public boolean isFinished() {
        return activeTasks.isEmpty() && runningTasks.isEmpty();
    }

    private final Map<TaskId, Task> tasks = new HashMap<>();
    private final List<TaskId> activeTasks = new LinkedList<>();
    private final List<TaskId> runningTasks = new LinkedList<>();
}
