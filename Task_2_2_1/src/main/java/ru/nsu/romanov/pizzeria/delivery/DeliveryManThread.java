package ru.nsu.romanov.pizzeria.delivery;

import ru.nsu.romanov.pizzeria.order.Order;
import ru.nsu.romanov.pizzeria.components.stockpile.Stockpile;
import ru.nsu.romanov.pizzeria.components.thread_safe_queue.QueueThreadSafe;

import java.util.concurrent.TimeUnit;

import static java.lang.Math.min;

public class DeliveryManThread implements Runnable {
    public DeliveryManThread(DeliveryMan deliveryMan,
                             QueueThreadSafe<Order> deliveryOrders, QueueThreadSafe<Order> doneOrders, Stockpile stockpile) {
        this.deliveryMan = deliveryMan;
        this.deliveryOrders = deliveryOrders;
        this.doneOrders = doneOrders;
        this.stockpile = stockpile;
    }
    @Override
    public void run() {
        while (true) {
            try {
                var order = deliveryOrders.pop();
                int count = order.countPizzas();
                while (count > 0) {
                    int toDelivery = min(count, deliveryMan.luggageVolume());
                    stockpile.pop(toDelivery);
                    TimeUnit.SECONDS.sleep(order.deliveryTime() * 2L);
                    count -= toDelivery;
                }
                doneOrders.push(order);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private final DeliveryMan deliveryMan;
    private final QueueThreadSafe<Order> deliveryOrders;
    private final QueueThreadSafe<Order> doneOrders;
    private final Stockpile stockpile;
}
