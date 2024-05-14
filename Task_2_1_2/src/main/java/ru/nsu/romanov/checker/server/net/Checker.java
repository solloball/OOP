package ru.nsu.romanov.checker.server.net;

import ru.nsu.romanov.checker.server.config.Address;
import ru.nsu.romanov.checker.server.config.Config;
import ru.nsu.romanov.checker.server.task.Task;
import ru.nsu.romanov.checker.server.task.TaskId;
import ru.nsu.romanov.checker.server.task.TaskManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Observer {
    public void Check() {

    }

    private InetAddress address(Address address) throws UnknownHostException {
        return Inet4Address.getByName(address.getIp());
    }

    private final TaskManager taskManager;
    private final Map<TaskId, InetAddress> idToAdr = new HashMap<>();
    private final Map<InetAddress, TaskId> adrToId = new HashMap<>();
    private final Map<TaskId, Status> statuses = new HashMap<>();
}
