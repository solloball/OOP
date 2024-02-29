package ru.nsu.romanov.pizzeria.bakery;

import ru.nsu.romanov.pizzeria.order.Order;
import ru.nsu.romanov.pizzeria.components.stockpile.Stockpile;
import ru.nsu.romanov.pizzeria.components.thread_safe_queue.QueueThreadSafe;

import java.util.concurrent.TimeUnit;

public class ThreadBaker implements Runnable {

    public ThreadBaker(Baker baker, QueueThreadSafe<Order> cookingOrders, 
            QueueThreadSafe<Order> deliveryOrders, Stockpile stockpile) {
        this.baker = baker;
        this.cookingOrders = cookingOrders;
        this.deliveryOrders = deliveryOrders;
        this.stockpile = stockpile;
    }

    @Override
    public void run() {
        while (true) {
            try {
                var order = cookingOrders.pop();
                TimeUnit.SECONDS.sleep(order.countPizzas() / baker.speed());
                deliveryOrders.push(order);
                stockpile.push(order.countPizzas());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stop() {
    }

    private final Baker baker;
    private final QueueThreadSafe<Order> cookingOrders;
    private final QueueThreadSafe<Order> deliveryOrders;
    private final Stockpile stockpile;
}
