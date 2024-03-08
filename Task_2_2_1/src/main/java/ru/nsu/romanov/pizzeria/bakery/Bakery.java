package ru.nsu.romanov.pizzeria.bakery;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.romanov.pizzeria.components.thread_safe_queue.MyQueue;
import ru.nsu.romanov.pizzeria.components.stockpile.Stockpile;
import ru.nsu.romanov.pizzeria.order.Order;

/**
 * Class which simulates bakery.
 */
public class Bakery {

    /**
     * Default constructor.
     *
     * @param cookingOrders cooking orders.
     * @param deliveryOrders delivery orders.
     * @param stockpile stockpile.
     */
    public Bakery(MyQueue<Order> cookingOrders,
                  MyQueue<Order> deliveryOrders, Stockpile stockpile) {
        this.cookingOrders = cookingOrders;
        this.deliveryOrders = deliveryOrders;
        this.stockpile = stockpile;
    }

    /**
     * Run simulation of bakery.
     */
    public void run() {
        bakerThreads.forEach(ThreadBaker::start);
    }

    /**
     * Stop simulation of bakery.
     */
    public void stop() {
        bakerThreads.forEach(ThreadBaker::stop);
    }

    /**
     * Remove baker from bakery.
     *
     * @param id baker's id.
     * @return true if baker was removed, false otherwise.
     */
    public boolean removeBaker(int id) {
        if (id < 0 || id >= bakerThreads.size()) {
            return false;
        }
        bakerThreads.get(id).stop();
        bakerThreads.remove(id);
        return true;
    }

    /**
     * Add new baker to bakery.
     *
     * @param baker baker to add.
     */
    public void addBaker(Baker baker) {
        bakerThreads.add(new ThreadBaker(baker, cookingOrders, deliveryOrders, stockpile));
    }

    /**
     * get baker from bakery.
     *
     * @param id id of baker.
     * @return baker if exists, null otherwise.
     */
    public Baker getBaker(int id) {
        if (id < 0 || id >= bakerThreads.size()) {
            return null;
        }
        return bakerThreads.get(id).getBaker();
    }

    private final List<ThreadBaker> bakerThreads = new ArrayList<>();
    private final MyQueue<Order> cookingOrders;
    private final MyQueue<Order> deliveryOrders;
    private final Stockpile stockpile;
}
