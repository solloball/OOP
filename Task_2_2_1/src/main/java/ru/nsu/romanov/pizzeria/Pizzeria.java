package ru.nsu.romanov.pizzeria;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import ru.nsu.romanov.pizzeria.bakery.Baker;
import ru.nsu.romanov.pizzeria.bakery.Bakery;
import ru.nsu.romanov.pizzeria.components.queue.MyQueue;
import ru.nsu.romanov.pizzeria.components.queue.QueueThreadSafe;
import ru.nsu.romanov.pizzeria.components.stockpile.Stockpile;
import ru.nsu.romanov.pizzeria.delivery.Delivery;
import ru.nsu.romanov.pizzeria.delivery.DeliveryMan;
import ru.nsu.romanov.pizzeria.order.Order;
import ru.nsu.romanov.pizzeria.state.JsonStateManager;
import ru.nsu.romanov.pizzeria.state.StateManger;

/**
 * Class pizzeria, which simulated a real pizzeria.
 */
public class Pizzeria {

    /**
     * Default constructor.
     *
     * @param cookingOrders orders to cook.
     * @param deliveryOrders orders to delivery.
     * @param doneOrders done orders.
     */
    public Pizzeria(
            MyQueue<Order> cookingOrders,
            MyQueue<Order> deliveryOrders,
            MyQueue<Order> doneOrders) {
        this.cookingOrders = cookingOrders;
        this.deliveryOrders = deliveryOrders;
        this.doneOrders = doneOrders;
        bakery = new Bakery(cookingOrders, deliveryOrders, stockpile);
        delivery = new Delivery(deliveryOrders, doneOrders, stockpile);
    }

    /**
     * Default pizzeria.
     */
    public Pizzeria() {
        cookingOrders = new QueueThreadSafe<>();
        deliveryOrders = new QueueThreadSafe<>();
        doneOrders = new QueueThreadSafe<>();
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

    private final Delivery delivery;
    private final Stockpile stockpile = new Stockpile();
    private final Bakery bakery;
    private final MyQueue<Order> cookingOrders;
    private final MyQueue<Order> deliveryOrders;
    private final MyQueue<Order> doneOrders;


    /**
     * Testing simulation.
     *
     * @param args unused.
     * @throws InterruptedException can throw InterruptedException.
     */
    public static void main(String[] args) throws InterruptedException {
        File file = new File("test.json");
        Pizzeria pizzeria = getPizzeria(file);
        pizzeria.addBaker(new Baker(1));
        pizzeria.addBaker(new Baker(1));
        pizzeria.addBaker(new Baker(1));
        pizzeria.setStockpileCapacity(120);
        pizzeria.addDeliveryMan(new DeliveryMan(3));
        pizzeria.addDeliveryMan(new DeliveryMan(3));
        pizzeria.addDeliveryMan(new DeliveryMan(3));
        pizzeria.run();
        TimeUnit.SECONDS.sleep(15);
        pizzeria.stop();
    }

    /**
     * return pizzeria from file.
     *
     * @param file
     * @return
     */
    @NotNull
    private static Pizzeria getPizzeria(
            File file) {
        MyQueue<Order> deliveryOrders = new QueueThreadSafe<>();
        MyQueue<Order> doneOrders = new QueueThreadSafe<>();
        MyQueue<Order> cookingOrders = new QueueThreadSafe<>();
        StateManger stateManger = new JsonStateManager(
                cookingOrders,
                deliveryOrders,
                doneOrders);
        try (InputStream inputStream = new FileInputStream(file);
             InputStreamReader inputStreamReader =
                     new InputStreamReader(inputStream)) {
            stateManger.readState(inputStreamReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new Pizzeria(
                cookingOrders,
                deliveryOrders,
                doneOrders);
    }
}


