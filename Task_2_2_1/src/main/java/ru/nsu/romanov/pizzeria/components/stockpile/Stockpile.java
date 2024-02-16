package ru.nsu.romanov.pizzeria.components.stockpile;

public class Stockpile {

    public void push() {
    }

    public void pop() {

    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        this.unusedSpace = capacity;
    }

    private int capacity = 0;
    private int unusedSpace = 0;
}
