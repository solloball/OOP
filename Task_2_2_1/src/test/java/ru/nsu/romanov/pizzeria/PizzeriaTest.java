package ru.nsu.romanov.pizzeria;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Queue;
import org.junit.jupiter.api.Test;
import ru.nsu.romanov.pizzeria.bakery.Baker;
import ru.nsu.romanov.pizzeria.delivery.DeliveryMan;
import ru.nsu.romanov.pizzeria.order.Order;

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
        pizzeria.run();

        int i = 0;
        while (true) {
            pizzeria.addOrder(new Order(i++, 1, 1));
            List<Queue<Order>> list =
                pizzeria.getStatus();
            printState(list);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    private void printState(List<Queue<Order>> list) {
        System.out.println("State: ");
        list.forEach(elem -> {
            System.out.println(elem.toString());
        });
    }
}
