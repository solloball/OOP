package ru.nsu.romanov.pizzeria.state;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import ru.nsu.romanov.pizzeria.components.queue.MyQueue;
import ru.nsu.romanov.pizzeria.order.Order;

/**
 * Read and write to json.
 */
public class JsonStateManager implements StateManger {
    /**
     * Constructor for json state manager.
     *
     * @param cookingOrders orders to cook.
     * @param deliveryOrders orders to delivery.
     * @param doneOrders done orders.
     */
    public JsonStateManager(
            MyQueue<Order> cookingOrders,
            MyQueue<Order> deliveryOrders,
            MyQueue<Order> doneOrders){
        this.cookingOrders = cookingOrders;
        this.deliveryOrders = deliveryOrders;
        this.doneOrders = doneOrders;
    }

    /**
     * read state from json.
     *
     * @param inputStreamReader stream from which read.
     */
    @Override
    public void readState(InputStreamReader inputStreamReader) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Queue<Order>> json = mapper.readValue(inputStreamReader, new TypeReference<>(){});
            if (json.size() != 3) {
                throw new IllegalArgumentException("size should be 3");
            }
            cookingOrders.setQueue(json.getFirst());
            deliveryOrders.setQueue(json.get(1));
            doneOrders.setQueue(json.get(2));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Write state into state.
     *
     * @param outputStreamWriter stream in which write.
     */
    @Override
    public void writeState(OutputStreamWriter outputStreamWriter) {
        try {
            List<Queue<Order>> json = new LinkedList<>();
            json.add(new LinkedList<>(cookingOrders.getQueue()));
            json.add(new LinkedList<>(deliveryOrders.getQueue()));
            json.add(new LinkedList<>(doneOrders.getQueue()));
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(outputStreamWriter, json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final private MyQueue<Order> cookingOrders;
    final private MyQueue<Order> deliveryOrders;
    final private MyQueue<Order> doneOrders;
}
