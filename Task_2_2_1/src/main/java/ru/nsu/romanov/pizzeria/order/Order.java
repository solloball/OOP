package ru.nsu.romanov.pizzeria.order;

/**
 * Record of order.
 *
 * @param id id of order.
 * @param countPizzas count pizza to make and delivery.
 * @param deliveryTime time from pizzeria to point of delivery.
 */
public record Order(int id, int countPizzas, int deliveryTime) {
}
