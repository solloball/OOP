package ru.nsu.romanov.pizzeria.components.threadSafeQueue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Realization of blocking queue.
 *
 * @param <T> type of elements.
 */
public class QueueThreadSafe<T> implements MyQueue<T> {

    /**
     * Push obj into queue.
     *
     * @param obj obj to push.
     */
    public synchronized void push(T obj) {
        queue.add(obj);
        notify();
    }

    /**
     * Pop from queue.
     *
     * @return elements.
     * @throws InterruptedException can throw InterruptedException.
     */
    public synchronized T pop() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        return queue.remove();
    }

    /**
     * Get queue.
     * If other threads call method, can return illegal state.
     *
     * @return queue.
     */
    public synchronized Queue<T> getQueue() {
        return queue;
    }

    /**
     * Set queue.
     *
     * @param queue queue to set.
     */
    public synchronized void setQueue(Queue<T> queue) {
        this.queue = queue; 
    }

    private java.util.Queue<T> queue = new LinkedList<T>();
}
