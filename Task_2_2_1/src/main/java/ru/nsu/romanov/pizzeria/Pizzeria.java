package ru.nsu.romanov.pizzeria;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import ru.nsu.romanov.pizzeria.bakery.Baker;
import ru.nsu.romanov.pizzeria.bakery.Bakery;
import ru.nsu.romanov.pizzeria.components.queue.MyQueue;
import ru.nsu.romanov.pizzeria.components.queue.QueueThreadSafe;
import ru.nsu.romanov.pizzeria.components.stockpile.Stockpile;
import ru.nsu.romanov.pizzeria.delivery.Delivery;
import ru.nsu.romanov.pizzeria.delivery.DeliveryMan;
import ru.nsu.romanov.pizzeria.order.Order;
import ru.nsu.romanov.pizzeria.state.StateManger;

/**
 * Class pizzeria, which simulated a real pizzeria.
 */
public class Pizzeria {

    /**
     * Default constructor for pizzeria.
     */
    public Pizzeria() {
        bakery = new Bakery(cookingOrders, deliveryOrders, stockpile);
        delivery = new Delivery(deliveryOrders, doneOrders, stockpile);
    }

    /**
     * Start simulation of pizzeria.
     */
    public void run() {
        bakery.run();
        delivery.run();
    }

    /**
     * Stop simulation of pizzeria.
     */
    public void stop() {
        bakery.stop();
        delivery.stop();
    }

    /**
     * Add new order to pizzeria.
     *
     * @param order order to add.
     */
    public void addOrder(Order order) {
        cookingOrders.push(order);
    }

    /**
     * return state of pizzeria.
     * If pizzeria work, can return strange state.
     *
     * @return state of pizzeria.
     */
    public List<Queue<Order>> getState() {
        List<Queue<Order>> res = new ArrayList<>();
        res.add(cookingOrders.getQueue());
        res.add(deliveryOrders.getQueue());
        res.add(doneOrders.getQueue());
        return res;
    }

    /**
     * Set state to pizzeria.
     * list must have 3 queues, which are cooking, delivery and done orders.
     *
     *
     * @param list list to set.
     */
    public void setState(List<java.util.Queue<Order>> list) {
        if (list.size() != 3) {
            throw new IllegalArgumentException(
                    "list should have 3 elements");
        }
        cookingOrders.setQueue(list.get(0));
        deliveryOrders.setQueue(list.get(1));
        doneOrders.setQueue(list.get(2));
    }

    /**
     * Add new delivery man to pizzeria.
     *
     * @param deliveryMan delivery man to add.
     */
    public void addDeliveryMan(DeliveryMan deliveryMan) {
        delivery.addDeliveryMan(deliveryMan);
    }

    /**
     * Remove delivery man from pizzeria.
     *
     * @param index index of delivery man.
     * @return true if delivery man was removed, false otherwise.
     */
    public boolean removeDeliveryMan(int index) {
        return delivery.removeDeliveryMan(index);
    }

    /**
     * Set capacity to stockpile.
     * If pizzeria work, can make illegal state.
     *
     * @param capacity capacity to set.
     */
    public void setStockpileCapacity(int capacity) {
        stockpile.setCapacity(capacity);
    }

    /**
     * Add new baker.
     *
     * @param baker baker to set.
     */
    public void addBaker(Baker baker) {
        bakery.addBaker(baker);
    }

    /**
     * Remove baker from pizzeria.
     *
     * @param index index of baker.
     * @return true if baker was removed, false otherwise.
     */
    public boolean removeBaker(int index) {
        return bakery.removeBaker(index);
    }

    /**
     * Set state manager in pizzeria, by default null.
     *
     * @param stateManger state manager to set.
     */
    public void setStateManager(StateManger stateManger) {
        this.stateManger = stateManger;
    }

    /**
     * Set state using state manager.
     *
     * @param inputStreamReader input stream from which read.
     * @throws IOException can throw IOException.
     */
    public void setStateManager(InputStreamReader inputStreamReader) throws IOException {
       stateManger.readState(inputStreamReader);
    }

    /**
     * Get state using state manager.
     *
     * @param outputStreamWriter stream in which write state.
     */
    public void getStateManager(OutputStreamWriter outputStreamWriter) {
        stateManger.writeState(outputStreamWriter);
    }

    private StateManger stateManger = null;
    private final Delivery delivery;
    private final Stockpile stockpile = new Stockpile();
    private final Bakery bakery;
    private final MyQueue<Order> cookingOrders =
        new QueueThreadSafe<>();
    private final MyQueue<Order> deliveryOrders =
        new QueueThreadSafe<>();
    private final MyQueue<Order> doneOrders =
        new QueueThreadSafe<>();
}
