package ru.nsu.romanov.pizzeria;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.romanov.pizzeria.components.stockpile.Stockpile;
import ru.nsu.romanov.pizzeria.components.thread_safe_queue.QueueThreadSafe;
import ru.nsu.romanov.pizzeria.delivery.Delivery;
import ru.nsu.romanov.pizzeria.delivery.DeliveryMan;
import ru.nsu.romanov.pizzeria.order.Order;

/**
 * Tests for delivery.
 */
public class DeliveryTest {
    @Test
    public void simpleAddDeliveryManTest() {
        Delivery delivery = new Delivery(
                new QueueThreadSafe<>(),
                new QueueThreadSafe<>(),
                new Stockpile());
        var expectedDeliveryMan = new DeliveryMan(1);
        delivery.addDeliveryMan(expectedDeliveryMan);
        Assertions.assertEquals(expectedDeliveryMan, delivery.getDeliveryMan(0));
    }

    @Test
    public void emptyDeliveryTest() {
        Delivery delivery = new Delivery(
                new QueueThreadSafe<>(),
                new QueueThreadSafe<>(),
                new Stockpile());
        Assertions.assertNull(delivery.getDeliveryMan(0));
    }

    @Test
    public void removeEmptyDeliveryTest() {
        Delivery delivery = new Delivery(
                new QueueThreadSafe<>(),
                new QueueThreadSafe<>(),
                new Stockpile());
        Assertions.assertFalse(delivery.removeDeliveryMan(0));
    }

    @Test
    public void simpleRemoveEmptyDeliverManTest() {
        Delivery delivery = new Delivery(
                new QueueThreadSafe<>(),
                new QueueThreadSafe<>(),
                new Stockpile());
        delivery.addDeliveryMan(new DeliveryMan(9));
        delivery.removeDeliveryMan(0);
        Assertions.assertNull(delivery.getDeliveryMan(0));
    }

    @Test
    public void deliveryManWorkEmptyStockpileTest() throws InterruptedException {
        Stockpile stockpile = new Stockpile();
        stockpile.setCapacity(10);
        QueueThreadSafe<Order> deliveryOrders =
                new QueueThreadSafe<>();
        deliveryOrders.push(new Order(0, 2, 1));
        deliveryOrders.push(new Order(1, 3, 1));
        QueueThreadSafe<Order> doneOrders =
                new QueueThreadSafe<>();
        Delivery delivery = new Delivery(
                deliveryOrders,
                doneOrders,
                stockpile);
        delivery.addDeliveryMan(new DeliveryMan(10));
        delivery.run();
        TimeUnit.SECONDS.sleep(10);
        delivery.stop();
        Assertions.assertEquals(1, deliveryOrders.getQueue().size());
        Assertions.assertEquals(0, doneOrders.getQueue().size());
        Assertions.assertEquals(0, stockpile.getCount());
    }

    @Test
    public void deliveryManWorkTest() throws InterruptedException {
        Stockpile stockpile = new Stockpile();
        stockpile.setCapacity(10);
        stockpile.push(10);
        QueueThreadSafe<Order> deliveryOrders =
                new QueueThreadSafe<>();
        deliveryOrders.push(new Order(0, 2, 1));
        deliveryOrders.push(new Order(1, 3, 1));
        QueueThreadSafe<Order> doneOrders =
                new QueueThreadSafe<>();
        Delivery delivery = new Delivery(
                deliveryOrders,
                doneOrders,
                stockpile);
        delivery.addDeliveryMan(new DeliveryMan(10));
        delivery.run();
        TimeUnit.SECONDS.sleep(10);
        delivery.stop();
        Assertions.assertEquals(0, deliveryOrders.getQueue().size());
        Assertions.assertEquals(2, doneOrders.getQueue().size());
        Assertions.assertEquals(5, stockpile.getCount());
    }

    @Test
    public void deliveryManWorkEmptyOrdersTest() throws InterruptedException {
        Stockpile stockpile = new Stockpile();
        stockpile.setCapacity(10);
        QueueThreadSafe<Order> deliveryOrders =
                new QueueThreadSafe<>();
        QueueThreadSafe<Order> doneOrders =
                new QueueThreadSafe<>();
        Delivery delivery = new Delivery(
                deliveryOrders,
                doneOrders,
                stockpile);
        delivery.addDeliveryMan(new DeliveryMan(10));
        delivery.run();
        TimeUnit.SECONDS.sleep(10);
        delivery.stop();
        Assertions.assertEquals(0, deliveryOrders.getQueue().size());
        Assertions.assertEquals(0, doneOrders.getQueue().size());
        Assertions.assertEquals(0, stockpile.getCount());
    }
}
