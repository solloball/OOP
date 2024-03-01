package ru.nsu.romanov.pizzeria;

import java.util.ArrayList;
import java.util.Queue;
import java.util.List;
import ru.nsu.romanov.pizzeria.bakery.Baker;
import ru.nsu.romanov.pizzeria.bakery.Bakery;
import ru.nsu.romanov.pizzeria.delivery.Delivery;
import ru.nsu.romanov.pizzeria.delivery.DeliveryMan;
import ru.nsu.romanov.pizzeria.order.Order;
import ru.nsu.romanov.pizzeria.components.stockpile.Stockpile;
import ru.nsu.romanov.pizzeria.components.thread_safe_queue.QueueThreadSafe;

public class Pizzeria {

    public Pizzeria() {
        bakery = new Bakery(cookingOrders, deliveryOrders, stockpile);
        delivery = new Delivery(deliveryOrders, doneOrders, stockpile);
    }

    public void run() {
        bakery.run();
        delivery.run();
    }

    public void stop() {
        bakery.stop();
        delivery.stop();
    }

    public void addOrder(Order order) {
        cookingOrders.push(order);
    }

    public List<Queue<Order>> getStatus() {
        List<Queue<Order>> res = new ArrayList<>();
        res.add(cookingOrders.getQueue());
        res.add(deliveryOrders.getQueue());
        res.add(doneOrders.getQueue());
        return res;
    }

    public void setStatus(List<Queue<Order>> list) {
        if (list.size() != 3) {
            throw new IllegalArgumentException(
                    "list should have 3 elements");
        }
        cookingOrders.SetQueue(list.get(0));  
        deliveryOrders.SetQueue(list.get(1));  
        doneOrders.SetQueue(list.get(2));  
    }

    public void addDeliveryMan(DeliveryMan deliveryMan) {
        delivery.addDeliveryMan(deliveryMan);
    }

    public boolean removeDeliveryMan(int index) {
        return delivery.removeDeliveryMan(index);
    }

    public void setStockpileCapacity(int capacity) {
        stockpile.setCapacity(capacity);
    }

    public void addBaker(Baker baker) {
        bakery.addBaker(baker);
    }

    public boolean removeBaker(int index) {
        return bakery.removeBaker(index);
    }

    private final Delivery delivery;
    private final Stockpile stockpile = new Stockpile();
    private final Bakery bakery;
    private final QueueThreadSafe<Order> cookingOrders = 
        new QueueThreadSafe<>();
    private final QueueThreadSafe<Order> deliveryOrders = 
        new QueueThreadSafe<>();
    private final QueueThreadSafe<Order> doneOrders = 
        new QueueThreadSafe<>();
}
