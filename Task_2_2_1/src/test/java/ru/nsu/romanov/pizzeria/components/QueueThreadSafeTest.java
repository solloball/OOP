package ru.nsu.romanov.pizzeria.components;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.romanov.pizzeria.components.thread_safe_queue.MyQueue;
import ru.nsu.romanov.pizzeria.components.thread_safe_queue.QueueThreadSafe;

/**
 * Tests for queue thread safe.
 */
public class QueueThreadSafeTest {
    @Test
    public void simplePushPopTest() throws InterruptedException {
        MyQueue<Integer> queue = new QueueThreadSafe<Integer>();
        queue.push(1);
        Assertions.assertEquals(1, queue.pop());
    }

    @Test
    void MultiPushPopTest() throws InterruptedException {
        MyQueue<Integer> queue = new QueueThreadSafe<Integer>();
        queue.push(1);
        queue.push(2);
        queue.push(3);
        Assertions.assertEquals(1, queue.pop());
        Assertions.assertEquals(2, queue.pop());
        Assertions.assertEquals(3, queue.pop());
    }
}
