package ru.nsu.romanov.checker.server.net.task;

import java.util.List;

/**
 * Task.
 *
 * @param taskId id of task.
 * @param array array to do.
 */
public record Task(TaskId taskId, List<Integer> array) {
}
