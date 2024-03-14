package ru.nsu.romanov.pizzeria;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.romanov.pizzeria.components.queue.MyQueue;
import ru.nsu.romanov.pizzeria.components.queue.QueueThreadSafe;
import ru.nsu.romanov.pizzeria.order.Order;
import ru.nsu.romanov.pizzeria.state.JsonStateManager;
import ru.nsu.romanov.pizzeria.state.StateManger;

/**
 * Tests for json state manager.
 */
public class JsonStateManagerTest {
    @Test
    void simpleReadTest() throws InterruptedException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(("[[{\"id\":1,\"countPizzas\":1,\"deliveryTime\":1}]," +
                "[{\"id\":2,\"countPizzas\":2,\"deliveryTime\":2}]," +
                "[{\"id\":3,\"countPizzas\":3,\"deliveryTime\":3}]]").getBytes());
        MyQueue<Order> cookingOrders = new QueueThreadSafe<>();
        MyQueue<Order> deliveryOrders = new QueueThreadSafe<>();
        MyQueue<Order> doneOrders = new QueueThreadSafe<>();
        StateManger stateManger = new JsonStateManager(
                cookingOrders,
                deliveryOrders,
                doneOrders);

        try (InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
            stateManger.readState(inputStreamReader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(new Order(1, 1, 1), cookingOrders.pop());
        Assertions.assertEquals(new Order(2, 2, 2), deliveryOrders.pop());
        Assertions.assertEquals(new Order(3, 3, 3), doneOrders.pop());
    }

    @Test
    void simpleWriteTest() throws FileNotFoundException {
        MyQueue<Order> cookingOrders = new QueueThreadSafe<>();
        Queue<Order> queueC = new LinkedList<>();
        queueC.add(new Order(1, 1, 1));
        cookingOrders.setQueue(queueC);
        MyQueue<Order> deliveryOrders = new QueueThreadSafe<>();
        Queue<Order> queueD = new LinkedList<>();
        queueD.add(new Order(2, 2, 2));
        deliveryOrders.setQueue(queueD);
        MyQueue<Order> doneOrders = new QueueThreadSafe<>();
        Queue<Order> queueDo = new LinkedList<>();
        queueDo.add(new Order(3, 3, 3));
        doneOrders.setQueue(queueDo);
        StateManger stateManger = new JsonStateManager(
                cookingOrders,
                deliveryOrders,
                doneOrders);
        File file = new File("test.json");

        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                new FileOutputStream(file))) {
            stateManger.writeState(outputStreamWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (Scanner scanner = new Scanner(file)) {
            Assertions.assertEquals(
                    "[[{\"id\":1,\"countPizzas\":1,\"deliveryTime\":1}]," +
                            "[{\"id\":2,\"countPizzas\":2,\"deliveryTime\":2}]," +
                            "[{\"id\":3,\"countPizzas\":3,\"deliveryTime\":3}]]",
                    scanner.next());
        }
        if (!file.delete()) {
            throw new IllegalStateException("failed to delete file");
        }
    }
}
