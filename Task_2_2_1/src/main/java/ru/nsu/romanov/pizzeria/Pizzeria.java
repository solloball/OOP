package ru.nsu.romanov.pizzeria;

import ru.nsu.romanov.pizzeria.components.bakery.Baker;
import ru.nsu.romanov.pizzeria.components.bakery.Bakery;
import ru.nsu.romanov.pizzeria.components.delivery.Delivery;
import ru.nsu.romanov.pizzeria.components.delivery.DeliveryMan;
import ru.nsu.romanov.pizzeria.components.order.Order;
import ru.nsu.romanov.pizzeria.components.stockpile.Stockpile;
import ru.nsu.romanov.pizzeria.components.thread_safe_queue.QueueThreadSafe;

import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class Pizzeria {

    public Pizzeria() {
        bakery = new Bakery(cookingOrders, deliveryOrders, stockpile);
        delivery = new Delivery(deliveryOrders, doneOrders, stockpile);
    }

    public void run() throws InterruptedException {
        bakery.run();
        delivery.run();

        while (true) {
            Queue<Order> cQueue = cookingOrders.getQueue();
            Queue<Order> dQueue = deliveryOrders.getQueue();
            Queue<Order> doneQueue = doneOrders.getQueue();
            int count = stockpile.getCount();
            System.out.println(count + cQueue.toString() + dQueue.toString() + doneQueue.toString());
            TimeUnit.SECONDS.sleep(1);
        }
    }

    public void addOrder(Order order) {
        cookingOrders.push(order);
    }

    public void stop() {
        bakery.stop();
        delivery.stop();
    }

    public void addDeliveryMan(DeliveryMan deliveryMan) {
        delivery.addDeliveryMan(deliveryMan);
    }

    public boolean removeDeliveryMan(int index) {
        return delivery.removeDeliveryMan(index);
    }

    public void setStockpileCapacity(int capacity) {
        stockpile.setCapacity(capacity);
    }

    public void addBaker(Baker baker) {
        bakery.addBaker(baker);
    }

    public boolean removeBaker(int index) {
        return bakery.removeBaker(index);
    }


    private final Delivery delivery;
    private final Stockpile stockpile = new Stockpile();
    private final Bakery bakery;
    private final QueueThreadSafe<Order> cookingOrders = new QueueThreadSafe<>();
    private final QueueThreadSafe<Order> deliveryOrders = new QueueThreadSafe<>();
    private final QueueThreadSafe<Order> doneOrders = new QueueThreadSafe<>();
}