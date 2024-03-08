package ru.nsu.romanov.pizzeria;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.romanov.pizzeria.bakery.Baker;
import ru.nsu.romanov.pizzeria.bakery.Bakery;
import ru.nsu.romanov.pizzeria.components.stockpile.Stockpile;
import ru.nsu.romanov.pizzeria.components.threadSafeQueue.QueueThreadSafe;
import ru.nsu.romanov.pizzeria.order.Order;

/**
 * Tests for bakery.
 */
public class BakeryTest {
    @Test
    public void simpleAddBakerTest() {
        Bakery bakery = new Bakery(
                new QueueThreadSafe<>(),
                new QueueThreadSafe<>(),
                new Stockpile());
        var expectedBaker = new Baker(1);
        bakery.addBaker(expectedBaker);
        Assertions.assertEquals(expectedBaker, bakery.getBaker(0));
    }

    @Test
    public void emptyBakeryTest() {
        Bakery bakery = new Bakery(
                new QueueThreadSafe<>(),
                new QueueThreadSafe<>(),
                new Stockpile());
        Assertions.assertNull(bakery.getBaker(0));
    }

    @Test
    public void removeEmptyBakerTest() {
        Bakery bakery = new Bakery(
                new QueueThreadSafe<>(),
                new QueueThreadSafe<>(),
                new Stockpile());
        Assertions.assertFalse(bakery.removeBaker(0));
    }

    @Test
    public void simpleRemoveEmptyBakerTest() {
        Bakery bakery = new Bakery(
                new QueueThreadSafe<>(),
                new QueueThreadSafe<>(),
                new Stockpile());
        bakery.addBaker(new Baker(9));
        bakery.removeBaker(0);
        Assertions.assertNull(bakery.getBaker(0));
    }

    @Test
    public void bakerWorkTest() throws InterruptedException {
        Stockpile stockpile = new Stockpile();
        stockpile.setCapacity(10);
        QueueThreadSafe<Order> cookingOrders =
                new QueueThreadSafe<>();
        cookingOrders.push(new Order(0, 1, 1));
        cookingOrders.push(new Order(0, 2, 1));
        QueueThreadSafe<Order> deliveryOrders =
                new QueueThreadSafe<>();
        Bakery bakery = new Bakery(
                cookingOrders,
                deliveryOrders,
                stockpile);
        bakery.addBaker(new Baker(1));
        bakery.run();
        TimeUnit.SECONDS.sleep(10);
        bakery.stop();
        Assertions.assertEquals(0, cookingOrders.getQueue().size());
        Assertions.assertEquals(2, deliveryOrders.getQueue().size());
        Assertions.assertEquals(3, stockpile.getCount());
    }

    @Test
    public void bakerWorkFullStockpileTest() throws InterruptedException {
        Stockpile stockpile = new Stockpile();
        stockpile.setCapacity(10);
        stockpile.push(10);
        QueueThreadSafe<Order> cookingOrders =
                new QueueThreadSafe<>();
        cookingOrders.push(new Order(0, 1, 1));
        cookingOrders.push(new Order(0, 2, 1));
        QueueThreadSafe<Order> deliveryOrders =
                new QueueThreadSafe<>();
        Bakery bakery = new Bakery(
                cookingOrders,
                deliveryOrders,
                stockpile);
        bakery.addBaker(new Baker(1));
        bakery.run();
        TimeUnit.SECONDS.sleep(10);
        bakery.stop();
        Assertions.assertEquals(1, cookingOrders.getQueue().size());
        Assertions.assertEquals(1, deliveryOrders.getQueue().size());
        Assertions.assertEquals(10, stockpile.getCount());
    }

    @Test
    public void bakerWorkEmptyOrdersTest() throws InterruptedException {
        Stockpile stockpile = new Stockpile();
        stockpile.setCapacity(10);
        QueueThreadSafe<Order> cookingOrders =
                new QueueThreadSafe<>();
        QueueThreadSafe<Order> deliveryOrders =
                new QueueThreadSafe<>();
        Bakery bakery = new Bakery(
                cookingOrders,
                deliveryOrders,
                stockpile);
        bakery.addBaker(new Baker(1));
        bakery.run();
        TimeUnit.SECONDS.sleep(10);
        bakery.stop();
        Assertions.assertEquals(0, cookingOrders.getQueue().size());
        Assertions.assertEquals(0, deliveryOrders.getQueue().size());
        Assertions.assertEquals(0, stockpile.getCount());
    }
}
