package ru.nsu.romanov.checker.server.net.task;

import java.util.List;

public record Task(TaskId taskId, List<Integer> array) {
}
