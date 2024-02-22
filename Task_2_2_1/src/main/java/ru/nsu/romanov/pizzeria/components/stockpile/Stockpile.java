package ru.nsu.romanov.pizzeria.components.stockpile;

import static java.lang.Math.min;

public class Stockpile {

    synchronized public void push(int countToPush) throws InterruptedException {
        while (countToPush > 0) {
            if (count == capacity) {
                wait();
            }
            int toAdd = min(countToPush, capacity - count);
            count += toAdd;
            notify();
            countToPush -= toAdd;
        }
    }

    synchronized public void pop(int countToPop) throws InterruptedException {
        while (countToPop > 0) {
            if (count == 0) {
                wait();
            }
            int toRemove = min(countToPop, count);
            count -= toRemove;
            countToPop -= toRemove;
        }
    }

    public int getCount() {
        return count;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        this.count = 0;
    }

    private int capacity = 0;
    volatile private int count = 0;
}
