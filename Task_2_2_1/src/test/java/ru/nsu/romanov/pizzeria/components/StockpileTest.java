package ru.nsu.romanov.pizzeria.components;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import ru.nsu.romanov.pizzeria.components.stockpile.Stockpile;

/**
 * Tests for stockpile.
 */
public class StockpileTest {
    @Test
    public void simpleTestCapacity() {
        Stockpile stockpile = new Stockpile();
        stockpile.setCapacity(10);
        Assertions.assertEquals(10, stockpile.getCapacity());
    }

    @Test
    public void countWithSetCapacity() throws InterruptedException {
        Stockpile stockpile = new Stockpile();
        stockpile.setCapacity(10);
        stockpile.push(1);
        stockpile.setCapacity(10);

        Assertions.assertEquals(0, stockpile.getCount());
    }

    @Test
    public void pushPopTest() throws InterruptedException {
        Stockpile stockpile = new Stockpile();
        stockpile.setCapacity(10);
        stockpile.push(10);
        stockpile.pop(10);
        Assertions.assertEquals(0, stockpile.getCount());
    }

    @Test
    public void severalPushPopTest() throws InterruptedException {
        Stockpile stockpile = new Stockpile();
        stockpile.setCapacity(10);
        for (int i = 0; i < 10; i++) {
            stockpile.push(1);
        }
        for (int i = 0; i < 10; i++) {
            stockpile.pop(1);
        }
        Assertions.assertEquals(0, stockpile.getCount());
    }
}
