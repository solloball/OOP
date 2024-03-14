package ru.nsu.romanov.pizzeria.bakery;

import java.util.concurrent.TimeUnit;
import ru.nsu.romanov.pizzeria.components.queue.MyQueue;
import ru.nsu.romanov.pizzeria.components.stockpile.Stockpile;
import ru.nsu.romanov.pizzeria.order.Order;

/**
 * Class which simulates one baker.
 */
public class ThreadBaker implements Runnable {

    /**
     * Default constructor.
     *
     * @param baker baker.
     * @param cookingOrders cooking orders.
     * @param deliveryOrders delivery orders.
     * @param stockpile stockpile.
     */
    public ThreadBaker(Baker baker, MyQueue<Order> cookingOrders,
                       MyQueue<Order> deliveryOrders, Stockpile stockpile) {
        this.baker = baker;
        this.cookingOrders = cookingOrders;
        this.deliveryOrders = deliveryOrders;
        this.stockpile = stockpile;
    }

    /**
     * Main simulation of baker.
     */
    @Override
    public void run() {
        while (true) {
            try {
                var order = cookingOrders.pop();
                TimeUnit.SECONDS.sleep(order.countPizzas() / baker.speed());
                deliveryOrders.push(order);
                stockpile.push(order.countPizzas());
                printState(order);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    /**
     * Start simulation externally.
     */
    public void start() {
        thread.start();
    }

    /**
     * Stop simulation.
     */
    public void stop() {
        thread.interrupt();
    }

    /**
     * Get baker record.
     *
     * @return baker.
     */
    public Baker getBaker() {
        return baker;
    }

    private void printState(Order order) {
        System.out.println("id " + order.id()
            + "\ncooking order " + cookingOrders.getQueue()
            + "\ndelivery order " + deliveryOrders.getQueue());
    }

    private final Thread thread = new Thread(this);
    private final Baker baker;
    private final MyQueue<Order> cookingOrders;
    private final MyQueue<Order> deliveryOrders;
    private final Stockpile stockpile;
}
