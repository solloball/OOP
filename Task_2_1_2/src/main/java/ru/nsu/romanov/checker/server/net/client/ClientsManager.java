package ru.nsu.romanov.checker.server.net.client;

import java.net.InetSocketAddress;
import java.util.*;

import static java.lang.Math.*;

public class ClientsManager {
    public ClientsManager(List<InetSocketAddress> addressList) {
        final int defaultPriority = 5;
        addressList.forEach(adr -> {
            statuses.put(adr, Status.INACTIVE);
            priority.put(adr, defaultPriority);
            timer.put(adr, 0);
        });
    }

    public void runClient(InetSocketAddress adr) {
        statuses.put(adr, Status.ACTIVE);
    }

    public List<InetSocketAddress> getClients() {
        List<InetSocketAddress> res  = new ArrayList<>();

        statuses.forEach((adr, status) -> {
            switch (status) {
                case FINISHED, INACTIVE ->  res.add(adr);
                case FAILED -> {
                    if (timer.get(adr) == 0) {
                        res.add(adr);
                    } else {
                        lowerTimer(adr);
                    }
                }
            }
        });

        res.sort(Comparator.comparing(priority::get));
        return res;
    }

    public void rejectClient(InetSocketAddress adr) {
        final int delay = 100;
        statuses.put(adr, Status.FAILED);
        timer.put(adr, new Random().nextInt(delay));
        lowerPriority(adr);
    }

    public void finishClient(InetSocketAddress adr) {
        statuses.put(adr, Status.INACTIVE);
        increasePriority(adr);
    }

    private void lowerTimer(InetSocketAddress adr) {
        timer.put(adr, max(timer.get(adr) - 1, 0));
    }

    private void lowerPriority(InetSocketAddress adr) {
        final int minPriority = 0;
        priority.put(adr, max(priority.get(adr) - 1, minPriority));
    }

    private void increasePriority(InetSocketAddress adr) {
        final int maxPriority = 12;
        priority.put(adr, min(priority.get(adr) + 1, maxPriority));
    }

    private final Map<InetSocketAddress, Integer> priority = new HashMap<>();
    private final Map<InetSocketAddress, Status> statuses = new HashMap<>();
    private final Map<InetSocketAddress, Integer> timer = new HashMap<>();
}
