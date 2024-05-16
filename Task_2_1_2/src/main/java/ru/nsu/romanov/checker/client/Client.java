package ru.nsu.romanov.checker.client;

import java.io.IOException;
import ru.nsu.romanov.checker.client.net.Nod;

/**
 * Main class of client.
 */
public class Client {
    /**
     * Using for cli using, set std port 8888, if there is no any other.
     *
     * @param args make port equal to args[0], otherwise set port 8888.
     * @throws IOException can throw IOException.
     */
    public static void main(String[] args) throws IOException {
        int port = args.length == 0 ? 8888 : Integer.parseInt(args[0]);
        System.out.println("Start working on port " + port);
        javaMain(port);
    }

    /**
     * Using internal in java.
     *
     * @param port port to set.
     * @throws IOException can throw IOException.
     */
    public static void javaMain(int port) throws IOException {
        new Nod().start(port);
    }
}
