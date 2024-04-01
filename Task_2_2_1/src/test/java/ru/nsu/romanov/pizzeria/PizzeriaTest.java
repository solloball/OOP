package ru.nsu.romanov.pizzeria;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.romanov.pizzeria.bakery.Baker;
import ru.nsu.romanov.pizzeria.delivery.DeliveryMan;
import ru.nsu.romanov.pizzeria.order.Order;

/**
 * Tests for pizzeria.
 */
public class PizzeriaTest {

    @Test
    public void addRemoveBakerTest() {
        Pizzeria pizzeria = new Pizzeria();
        pizzeria.addBaker(new Baker(10));
        Assertions.assertTrue(pizzeria.removeBaker(0));
    }

    @Test
    public void addRemoveEmptyBakerTest() {
        Pizzeria pizzeria = new Pizzeria();
        Assertions.assertFalse(pizzeria.removeBaker(0));
    }

    @Test
    public void addRemoveDeliveryManTest() {
        Pizzeria pizzeria = new Pizzeria();
        pizzeria.addDeliveryMan(new DeliveryMan(10));
        Assertions.assertTrue(pizzeria.removeDeliveryMan(0));
    }

    @Test
    public void addRemoveEmptyDeliveryManTest() {
        Pizzeria pizzeria = new Pizzeria(null, null, null);
        Assertions.assertFalse(pizzeria.removeDeliveryMan(0));
    }

    @Test
    public void simpleSimulationTest() throws InterruptedException {
        Pizzeria pizzeria = new Pizzeria();
        pizzeria.addBaker(new Baker(1));
        pizzeria.addBaker(new Baker(1));
        pizzeria.addBaker(new Baker(1));
        pizzeria.setStockpileCapacity(120);
        pizzeria.addDeliveryMan(new DeliveryMan(3));
        pizzeria.addDeliveryMan(new DeliveryMan(5));
        pizzeria.addDeliveryMan(new DeliveryMan(8));
        pizzeria.run();

        for (int i = 0; i < 10; i++) {
            pizzeria.addOrder(new Order(i, 1, 1));
        }
        TimeUnit.SECONDS.sleep(10);
        pizzeria.stop();
        var state = pizzeria.getState();
        Assertions.assertEquals(0, state.getFirst().size());
        Assertions.assertEquals(0, state.get(1).size());
        Assertions.assertEquals(10, state.get(2).size());
    }
}
