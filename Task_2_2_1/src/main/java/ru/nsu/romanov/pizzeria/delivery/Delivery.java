package ru.nsu.romanov.pizzeria.delivery;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.romanov.pizzeria.components.stockpile.Stockpile;
import ru.nsu.romanov.pizzeria.components.queue.MyQueue;
import ru.nsu.romanov.pizzeria.order.Order;

/**
 * Class which simulated delivery.
 */
public class Delivery {

    /**
     * Default constructor.
     *
     * @param deliveryOrders queue of delivery orders.
     * @param doneOrders queue of done orders.
     * @param stockpile stockpile of pizzas.
     */
    public Delivery(MyQueue<Order> deliveryOrders,
                    MyQueue<Order> doneOrders, Stockpile stockpile) {
        this.deliveryOrders = deliveryOrders;
        this.doneOrders = doneOrders;
        this.stockpile = stockpile;
    }

    /**
     * Start simulation of delivery.
     */
    public void run() {
        deliveryManThreads.forEach(DeliveryManThread::start);
    }

    /**
     * Stop simulation of delivery.
     */
    public void stop() {
        deliveryManThreads.forEach(DeliveryManThread::stop);
    }

    /**
     * Add new delivery man.
     *
     * @param deliveryMan delivery man to add.
     */
    public void addDeliveryMan(DeliveryMan deliveryMan) {
        deliveryManThreads.add(new DeliveryManThread(deliveryMan,
                        deliveryOrders, doneOrders, stockpile));
    }

    /**
     * remove delivery man.
     *
     * @param id id of delivery man.
     * @return true if delivery man was removed, false otherwise.
     */
    public boolean removeDeliveryMan(int id) {
        if (id < 0 || id >= deliveryManThreads.size()) {
            return false;
        }
        deliveryManThreads.get(id).stop();
        deliveryManThreads.remove(id);
        return true;
    }

    /**
     * Get delivery man.
     *
     * @param id id of delivery man.
     * @return delivery man if exists, null otherwise.
     */
    public DeliveryMan getDeliveryMan(int id) {
        if (id < 0 || id >= deliveryManThreads.size()) {
            return null;
        }
        return deliveryManThreads.get(id).getDeliveryMan();
    }

    private final List<DeliveryManThread> deliveryManThreads = new ArrayList<>();
    private final MyQueue<Order> deliveryOrders;
    private final MyQueue<Order> doneOrders;
    private final Stockpile stockpile;
}
