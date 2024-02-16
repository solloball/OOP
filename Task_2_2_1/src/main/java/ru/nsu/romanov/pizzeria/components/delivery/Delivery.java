package ru.nsu.romanov.pizzeria.components.delivery;

import java.util.ArrayList;
import java.util.List;

public class Delivery {

    public void Run() {

    }

    public void addDeliveryMan(DeliveryMan deliveryMan) {
        deliveryMen.add(deliveryMan);
        isFreeDeliveryMen.add(true);
    }

    public boolean removeDeliveryMan(int idx) {
        if (idx < 0 || idx >= deliveryMen.size()) {
            return false;
        }
        deliveryMen.remove(idx);
        isFreeDeliveryMen.remove(idx);
        return true;
    }

    public List<DeliveryMan> getListDeliveryMen() {
        return deliveryMen;
    }

    private final List<DeliveryMan> deliveryMen = new ArrayList<>();
    private final List<Boolean> isFreeDeliveryMen = new ArrayList<>();
}
