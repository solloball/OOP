package ru.nsu.romanov.pizzeria.delivery;

import static java.lang.Math.min;

import java.util.concurrent.TimeUnit;
import ru.nsu.romanov.pizzeria.components.queue.MyQueue;
import ru.nsu.romanov.pizzeria.components.stockpile.Stockpile;
import ru.nsu.romanov.pizzeria.order.Order;

/**
 * Class for simulation delivery man.
 */
public class DeliveryManThread implements Runnable {
    /**
     * Default constructor.
     *
     * @param deliveryMan delivery man.
     * @param deliveryOrders queue of delivery orders.
     * @param doneOrders queue of done orders.
     * @param stockpile stockpile.
     */
    public DeliveryManThread(
            DeliveryMan deliveryMan,
            MyQueue<Order> deliveryOrders,
            MyQueue<Order> doneOrders,
            Stockpile stockpile) {
        this.deliveryMan = deliveryMan;
        this.deliveryOrders = deliveryOrders;
        this.doneOrders = doneOrders;
        this.stockpile = stockpile;
    }

    /**
     * Start of simulation.
     */
    @Override
    public void run() {
        while (!thread.isInterrupted()) {
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

    private void printState(Order order) {
        System.out.println("id " + order.id()
                + "\ndelivery order " + deliveryOrders.getQueue()
                + "\ndone order " + doneOrders.getQueue());
    }


    /**
     * get delivery man record.
     *
     * @return delivery man.
     */
    public DeliveryMan getDeliveryMan() {
        return deliveryMan;
    }

    private final Thread thread = new Thread(this);
    private final DeliveryMan deliveryMan;
    private final MyQueue<Order> deliveryOrders;
    private final MyQueue<Order> doneOrders;
    private final Stockpile stockpile;
}
