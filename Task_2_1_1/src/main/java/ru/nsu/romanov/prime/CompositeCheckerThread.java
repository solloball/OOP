package ru.nsu.romanov.prime;

import java.util.List;
import java.util.Scanner;

public class CompositeCheckerThread implements Runnable {

    public final Thread t = new Thread(this);
    private final List<Integer> list;
    private volatile boolean res;

    public CompositeCheckerThread(List<Integer> list) {
        this.list = list;
    }

    public boolean result() {
        return res;
    }

    @Override
    public void run() {
        CompositeChecker primeChecker = new CompositeChecker();

        res = primeChecker.hasCompositeSeq(list);
    }
}
