package ru.nsu.romanov.checker.server;

import ru.nsu.romanov.checker.server.config.Config;
import ru.nsu.romanov.checker.server.config.JsonReader;

import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        final String confName = "config.json";

        Config config = new JsonReader().read(
                new FileReader(confName));

        System.out.println("hello");
    }

}
