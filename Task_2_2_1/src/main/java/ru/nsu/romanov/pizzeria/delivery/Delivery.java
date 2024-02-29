package ru.nsu.romanov.pizzeria.delivery;

import ru.nsu.romanov.pizzeria.order.Order;
import ru.nsu.romanov.pizzeria.components.stockpile.Stockpile;
import ru.nsu.romanov.pizzeria.components.thread_safe_queue.QueueThreadSafe;

import java.util.ArrayList;
import java.util.List;

public class Delivery {

    public Delivery(QueueThreadSafe<Order> deliveryOrders, 
            QueueThreadSafe<Order> doneOrders, Stockpile stockpile) {
        this.deliveryOrders = deliveryOrders;
        this.doneOrders = doneOrders;
        this.stockpile = stockpile;
    }

    public void run() {
        threads.forEach(Thread::start);
    }

    public void stop() {
        threads.forEach(Thread::interrupt);    
    }

    public void addDeliveryMan(DeliveryMan deliveryMan) {
        threads.add(new Thread(new DeliveryManThread(deliveryMan,
                        deliveryOrders, doneOrders, stockpile)));
    }

    public boolean removeDeliveryMan(int id) {
        if (id < 0 || id >= threads.size()) {
            return false;
        }
        threads.remove(id);
        return true;
    }

    private final List<Thread> threads = new ArrayList<>();
    private final QueueThreadSafe<Order> deliveryOrders;
    private final QueueThreadSafe<Order> doneOrders;
    private final Stockpile stockpile;
}
