package ru.nsu.romanov.pizzeria;

import org.junit.jupiter.api.Test;
import ru.nsu.romanov.pizzeria.components.bakery.Baker;
import ru.nsu.romanov.pizzeria.components.delivery.DeliveryMan;
import ru.nsu.romanov.pizzeria.components.order.Order;

public class PizzeriaTest {

    @Test
    void simpleTest() throws InterruptedException {
        Pizzeria pizzeria = new Pizzeria();
        pizzeria.addBaker(new Baker(1));
        pizzeria.addBaker(new Baker(1));
        pizzeria.addBaker(new Baker(1));
        pizzeria.setStockpileCapacity(120);
        pizzeria.addDeliveryMan(new DeliveryMan(3));
        pizzeria.addDeliveryMan(new DeliveryMan(5));
        pizzeria.addDeliveryMan(new DeliveryMan(8));
        pizzeria.addOrder(new Order(0, 1, 1));
        pizzeria.addOrder(new Order(1, 1, 1));
        pizzeria.addOrder(new Order(2, 1, 1));
        pizzeria.addOrder(new Order(3, 1, 1));
        pizzeria.addOrder(new Order(4, 1, 1));
        pizzeria.addOrder(new Order(5, 1, 1));
        pizzeria.addOrder(new Order(6, 1, 1));
        pizzeria.addOrder(new Order(7, 1, 1));
        try {
            pizzeria.run();
        } catch (InterruptedException e) {
            throw new InterruptedException(e.toString());
        }
    }
}