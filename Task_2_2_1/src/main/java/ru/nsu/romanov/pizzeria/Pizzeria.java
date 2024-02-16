package ru.nsu.romanov.pizzeria;

import ru.nsu.romanov.pizzeria.components.bakery.Baker;
import ru.nsu.romanov.pizzeria.components.bakery.Bakery;
import ru.nsu.romanov.pizzeria.components.delivery.Delivery;
import ru.nsu.romanov.pizzeria.components.delivery.DeliveryMan;
import ru.nsu.romanov.pizzeria.components.order.Order;
import ru.nsu.romanov.pizzeria.components.stockpile.Stockpile;

import java.util.LinkedList;
import java.util.Queue;

public class Pizzeria {

    public void run() {

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

    private Delivery delivery = new Delivery();
    private Stockpile stockpile = new Stockpile();
    private Bakery bakery = new Bakery();
    private Queue<Order> orders = new LinkedList<>();
}