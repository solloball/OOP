package ru.nsu.romanov.checker.client.net;

import ru.nsu.romanov.checker.client.bytes.ReaderWithTimer;
import ru.nsu.romanov.checker.client.solver.SolverSeq;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Nod {
    public void start(int port) throws IOException {
        try (ServerSocket socket = new ServerSocket(port)) {
            socket.setSoTimeout(delay);

            System.out.println("Client wait accepting from a server");
            while (true) {

                Socket server;
                try {
                    server = socket.accept();
                } catch (Exception e) {
                    continue;
                }
                System.out.println("Client successfully connected");

                var in = server.getInputStream();
                var out = server.getOutputStream();
                List<Integer> list;

                try {
                    list = readArray(in);
                } catch (IOException e) {
                    throw new IOException(e);
                }

                var thread = echo(in, out);
                thread.start();

                byte res = (byte) (new SolverSeq().solve(list) ? 1 : 0);

                out.write(res);
                out.flush();
                thread.interrupt();
                System.out.println("Client finished calculation");
            }
        }
    }

    private List<Integer> readArray(InputStream in) throws IOException {
        ReaderWithTimer reader = new ReaderWithTimer();
        int length = reader.readInt(in, delay);
        List<Integer> list = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            list.add(reader.readInt(in, delay));
        }
        return list;
    }

    private Thread echo(InputStream in, OutputStream out) {
        return new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }

                try {
                    if (in.available() == 0) {
                        continue;
                    }
                    byte[] msg = new byte[1];
                    assert in.read(msg, 0, 1) == 1;
                    out.write(msg);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    private final int delay = 1000;
}
