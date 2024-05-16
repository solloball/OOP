package ru.nsu.romanov.checker.server.net.client;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Class which observe for clients and their states.
 */
public class ClientsManager {
    /**
     * Default constructor.
     *
     * @param addressList list of addresses.
     */
    public ClientsManager(List<InetSocketAddress> addressList) {
        final int defaultPriority = 5;
        addressList.forEach(adr -> {
            statuses.put(adr, Status.INACTIVE);
            priority.put(adr, defaultPriority);
            timer.put(adr, 0);
        });
    }

    /**
     * Make client's status runnable.
     *
     * @param adr address of client.
     */
    public void runClient(InetSocketAddress adr) {
        statuses.put(adr, Status.ACTIVE);
    }

    /**
     * Return available clients in order their priority.
     *
     * @return list of clients.
     */
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
                default -> throw new RuntimeException("Unknown type");
            }
        });

        res.sort(Comparator.comparing(priority::get));
        return res;
    }

    /**
     * Reject client and lower priority.
     *
     * @param adr address of client.
     */
    public void rejectClient(InetSocketAddress adr) {
        final int delay = 100;
        statuses.put(adr, Status.FAILED);
        timer.put(adr, new Random().nextInt(delay));
        lowerPriority(adr);
    }

    /**
     * Successfully finish client and enhance priority.
     *
     * @param adr address of client.
     */
    public void finishClient(InetSocketAddress adr) {
        statuses.put(adr, Status.INACTIVE);
        increasePriority(adr);
    }

    /**
     * Low time in timer.
     *
     * @param adr address of client.
     */
    private void lowerTimer(InetSocketAddress adr) {
        timer.put(adr, max(timer.get(adr) - 1, 0));
    }

    /**
     * Lower priority.
     *
     * @param adr address of client.
     */
    private void lowerPriority(InetSocketAddress adr) {
        final int minPriority = 0;
        priority.put(adr, max(priority.get(adr) - 1, minPriority));
    }

    /**
     * Enhance priority.
     *
     * @param adr address of client.
     */
    private void increasePriority(InetSocketAddress adr) {
        final int maxPriority = 12;
        priority.put(adr, min(priority.get(adr) + 1, maxPriority));
    }

    private final Map<InetSocketAddress, Integer> priority = new HashMap<>();
    private final Map<InetSocketAddress, Status> statuses = new HashMap<>();
    private final Map<InetSocketAddress, Integer> timer = new HashMap<>();
}
