package ru.nsu.romanov.pizzeria.components.thread_safe_queue;

/**
 * interface for queue.
 *
 * @param <T> type of containing elements.
 */
public interface MyQueue<T> {
    /**
     * Get queue.
     *
     * @return queue.
     */
    java.util.Queue<T> getQueue();

    /**
     * Set queue.
     *
     * @param queue queue to set.
     */
    void setQueue(java.util.Queue<T> queue);

    /**
     * push into queue.
     *
     * @param obj obj to set.
     */
    void push(T obj);

    /**
     * pop from queue.
     *
     * @return elements.
     * @throws InterruptedException can throw InterruptedException.
     */
    T pop() throws InterruptedException;
}
