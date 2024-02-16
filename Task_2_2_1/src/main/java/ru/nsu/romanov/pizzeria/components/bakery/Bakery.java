package ru.nsu.romanov.pizzeria.components.bakery;

import java.util.ArrayList;
import java.util.List;

public class Bakery {

    public void run() {

    }

    public boolean removeBaker(int idx) {
        if (idx < 0 || idx >= bakers.size()) {
            return false;
        }
        bakers.remove(idx);
        return true;
    }

    public void addBaker(Baker baker) {
        bakers.add(baker);
    }
    private final List<Baker> bakers = new ArrayList<>();
}
