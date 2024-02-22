package ru.nsu.romanov.pizzeria.components.thread_safe_queue;

import java.util.LinkedList;
import java.util.Queue;

public class QueueThreadSafe<T> {

    synchronized public void push(T obj) {
        queue.add(obj);
        notify();
    }

    synchronized public T pop() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        return queue.remove();
    }

    synchronized public Queue<T> getQueue() {
        return new LinkedList<>(queue);
    }

    private final Queue<T> queue = new LinkedList<T>();
}
