package ru.nsu.romanov.checker.server.net;

import ru.nsu.romanov.checker.server.config.Address;
import ru.nsu.romanov.checker.server.config.Config;
import ru.nsu.romanov.checker.server.net.client.ClientsManager;
import ru.nsu.romanov.checker.server.net.task.Task;
import ru.nsu.romanov.checker.server.net.task.TaskId;
import ru.nsu.romanov.checker.server.net.task.TaskManager;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;


public class Checker {
    public Checker(Config config, List<Integer> array) {
        this.delay = config.getResponseTime();

        List<InetSocketAddress> addresses = parseAddresses(config.getAddresses());

        taskManager = new TaskManager(array, addresses.size());
        clientsManager = new ClientsManager(addresses);
    }

    public boolean Check() throws IOException {
        selector = Selector.open();
        updateConnections();

        while (true) {
            try {
                selector.select(delay);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (var key : selector.keys()) {
                SocketChannel socket = (SocketChannel) key.channel();
                var adr = (InetSocketAddress) socket.socket().getRemoteSocketAddress();
                var id = adrToId.get(adr);

                if (!key.isReadable() && !checkTimer(socket, id)) {
                    cancel(id);
                    continue;
                }

                try {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1);
                    if (socket.read(byteBuffer) >= 1) {
                        var output = byteBuffer.flip().get();
                        if (output == reminderMsg) {
                            runningTime.put(id, System.currentTimeMillis());
                            System.out.println("Successfully remind client: " + adr);
                            continue;
                        }

                        finish(id);
                        if (output == 1) {
                            return true;
                        }
                    }

                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    cancel(id);
                }
            }
            if (taskManager.isFinished()) {
                return false;
            }
            updateConnections();
        }
    }

    private void finish(TaskId id) {
        taskManager.finishTask(id);
        clientsManager.finishClient(idToAdr.get(id));
    }

    private void cancel(TaskId id) {
        taskManager.rejectTask(id);
        clientsManager.rejectClient(idToAdr.get(id));
    }

    private void updateConnections() {
        var clients = clientsManager.getClients();

        for (var client : clients) {
            if (!taskManager.hasActiveTask()) {
                break;
            }

            Task task = taskManager.runTask();
            clientsManager.runClient(client);
            idToAdr.put(task.taskId(), client);
            adrToId.put(client, task.taskId());


            if (!connectClient(client, task)) {
                cancel(task.taskId());
            }
        }
    }

    private boolean connectClient(InetSocketAddress adr, Task task) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(true);
            socketChannel.socket().connect(adr, delay);
            socketChannel.configureBlocking(false);

            System.out.println("Connect to client: " + adr);

            if (!sendData(socketChannel, task.array())) {
                System.out.println("Failed to send data to client: " + adr);
                socketChannel.finishConnect();
                return false;
            }

            socketChannel.register(selector, SelectionKey.OP_READ);

        } catch (IOException e) {
            System.out.println("Failed to connect to client: " + adr);
            return false;
        }

        runningTime.put(task.taskId(), System.currentTimeMillis());
        System.out.println("Data successfully sent to client: " + adr);
        return true;
    }

    private boolean sendData(SocketChannel socketChannel, List<Integer> array) {
        try {
            ByteBuffer byteBuffer = ByteBuffer.allocate(4).putInt(array.size()).flip();
            socketChannel.write(byteBuffer);
            for (var num : array) {
                ByteBuffer buf = ByteBuffer.allocate(4).putInt(num).flip();
                socketChannel.write(buf);
            }
        } catch (IOException e) {
            System.out.println("Failed to send data to client: " + e);
            return false;
        }
        return true;
    }

    private List<InetSocketAddress> parseAddresses(List<Address> list) {
        List<InetSocketAddress> res = new ArrayList<>();

        list.forEach(adr -> {
            try {
                res.add(parseAddress(adr));
            } catch (UnknownHostException e) {
                System.out.println(
                        "incorrect address"
                                + adr.getIp()
                                + " "
                                + adr.getPort());
            }
        });

        return res;
    }

    private InetSocketAddress parseAddress(Address address) throws UnknownHostException {
        return new InetSocketAddress(address.getIp(), address.getPort());
    }

    private boolean checkTimer(SocketChannel socket, TaskId id) {
        var duration = System.currentTimeMillis() - runningTime.get(id);
        if (duration > delay * 2L) {
            return false;
        } else if (duration > delay) {
            try {
                remind(socket);
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    private void remind(SocketChannel socket) throws IOException {
        socket.write(ByteBuffer.wrap(new byte[]{reminderMsg}));
    }

    private Selector selector;
    private final TaskManager taskManager;
    private final ClientsManager clientsManager;
    private final Map<TaskId, Long> runningTime = new HashMap<>();
    private final Map<InetSocketAddress, TaskId> adrToId = new HashMap<>();
    private final Map<TaskId, InetSocketAddress> idToAdr = new HashMap<>();
    private final int delay;
    private final byte reminderMsg = 12;
}
