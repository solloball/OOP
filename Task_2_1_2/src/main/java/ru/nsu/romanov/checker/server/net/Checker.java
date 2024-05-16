package ru.nsu.romanov.checker.server.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.nsu.romanov.checker.server.config.Address;
import ru.nsu.romanov.checker.server.config.Config;
import ru.nsu.romanov.checker.server.net.client.ClientsManager;
import ru.nsu.romanov.checker.server.net.task.Task;
import ru.nsu.romanov.checker.server.net.task.TaskId;
import ru.nsu.romanov.checker.server.net.task.TaskManager;

/**
 * Main logic of server, connect to clients, send data and check result.
 */
public class Checker {
    /**
     * Default constructor.
     *
     * @param config config to set.
     * @param array array of number.
     */
    public Checker(Config config, List<Integer> array) {
        this.delay = config.getResponseTime();

        List<InetSocketAddress> addresses = parseAddresses(config.getAddresses());

        taskManager = new TaskManager(array, addresses.size());
        clientsManager = new ClientsManager(addresses);
    }

    /**
     * Run server.
     *
     * @return true if in array there is composite number, otherwise false.
     * @throws IOException can throw IOException.
     */
    public boolean check() throws IOException {
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
                var adr = (InetSocketAddress) socket.socket()
                        .getRemoteSocketAddress();
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

    /**
     * Successfully finish task.
     *
     * @param id id of task.
     */
    private void finish(TaskId id) {
        taskManager.finishTask(id);
        clientsManager.finishClient(idToAdr.get(id));
    }

    /**
     * Cancel running task.
     *
     * @param id id of task.
     */
    private void cancel(TaskId id) {
        taskManager.rejectTask(id);
        clientsManager.rejectClient(idToAdr.get(id));
    }

    /**
     * Update all connections.
     */
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

    /**
     * Connect client.
     *
     * @param adr address of client.
     * @param task task for client.
     * @return true if finished successfully, otherwise false.
     */
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

    /**
     * Send data to client.
     *
     * @param socketChannel socket of client.
     * @param array array to send.
     * @return true if finished successfully, false otherwise.
     */
    private boolean sendData(SocketChannel socketChannel, List<Integer> array) {
        try {
            ByteBuffer byteBuffer = ByteBuffer
                    .allocate(4)
                    .putInt(array.size())
                    .flip();
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

    /**
     * Parse internal class  address into InetSocketAddress.
     *
     * @param list list to parse.
     * @return list of InetSocketAddress elements.
     */
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

    /**
     * Parse internal class address into InetSocketAddress.
     *
     * @param address address to parse.
     * @return InetSocketAddress element.
     */
    private InetSocketAddress parseAddress(Address address) throws UnknownHostException {
        return new InetSocketAddress(address.getIp(), address.getPort());
    }

    /**
     * Check running time, if running time more than delay, send remindMsg.
     *
     * @param socket socket to read and write.
     * @param id if of task.
     * @return if running time more than 2 * delay return false, otherwise true.
     */
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

    /**
     * Send remindMsg to socket.
     *
     * @param socket socket to write.
     * @throws IOException can throw IOException.
     */
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
