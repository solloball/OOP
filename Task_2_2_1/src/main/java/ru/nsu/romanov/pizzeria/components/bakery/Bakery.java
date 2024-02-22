package ru.nsu.romanov.pizzeria.components.bakery;

import ru.nsu.romanov.pizzeria.components.order.Order;
import ru.nsu.romanov.pizzeria.components.stockpile.Stockpile;
import ru.nsu.romanov.pizzeria.components.thread_safe_queue.QueueThreadSafe;

import java.util.ArrayList;
import java.util.List;

public class Bakery {

    public Bakery(QueueThreadSafe<Order> cookingOrders, QueueThreadSafe<Order> deliveryOrders, Stockpile stockpile) {
        this.cookingOrders = cookingOrders;
        this.deliveryOrders = deliveryOrders;
        this.stockpile = stockpile;
    }

    public void run() {
        threads.forEach(Thread::start);
    }

    public void stop() {
    }

    public boolean removeBaker(int id) {
        if (id < 0 || id >= threads.size()) {
            return false;
        }
        threads.remove(id);
        return true;
    }

    public void addBaker(Baker baker) {
        threads.add(new Thread(new ThreadBaker(baker, cookingOrders, deliveryOrders, stockpile)));
    }

    private final List<Thread> threads = new ArrayList<>();
    private final QueueThreadSafe<Order> cookingOrders;
    private final QueueThreadSafe<Order> deliveryOrders;
    private final Stockpile stockpile;
}
