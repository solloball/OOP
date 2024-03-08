package ru.nsu.romanov.pizzeria.components.stockpile;

import static java.lang.Math.min;

/**
 * Stockpile of something.
 */
public class Stockpile {

    /**
     * Try to push into stockpile.
     * Block thread if stockpile is full.
     *
     * @param countToPush count to push.
     * @throws InterruptedException can throw InterruptedException.
     */
    synchronized public void push(int countToPush) throws InterruptedException {
        while (countToPush > 0) {
            while (count == capacity) {
                wait();
            }
            int toAdd = min(countToPush, capacity - count);
            count += toAdd;
            notify();
            countToPush -= toAdd;
        }
    }

    /**
     * Try to pop n count of something.
     * Block thread if stockpile is empty.
     *
     * @param countToPop count to pop.
     * @throws InterruptedException can throw InterruptedException.
     */
    synchronized public void pop(int countToPop) throws InterruptedException {
        while (countToPop > 0) {
            while (count == 0) {
                wait();
            }
            int toRemove = min(countToPop, count);
            count -= toRemove;
            countToPop -= toRemove;
        }
    }

    /**
     * Get count at moment of call.
     *
     * @return count from stockpile.
     */
    public int getCount() {
        return count;
    }

    /**
     * Get capacity at moment of call.
     *
     * @return capacity of stockpile.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Set capacity in stockpile.
     * If other threads calls other methods, can be illegal state.
     *
     * @param capacity capacity to set.
     */
    synchronized public void setCapacity(int capacity) {
        this.capacity = capacity;
        this.count = 0;
    }

    private int capacity = 0;
    private int count = 0;
}
