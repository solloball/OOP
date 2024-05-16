package ru.nsu.romanov.checker.client;

import ru.nsu.romanov.checker.client.net.Nod;
import java.io.IOException;

public class Client {
    public static void main(String[] args) throws IOException {
        int port = args.length == 0 ? 8888 : Integer.parseInt(args[0]);
        System.out.println("Start working on port " + port);
        Main(port);
    }

    public static void Main(int port) throws IOException {
        new Nod().start(port);
    }
}
