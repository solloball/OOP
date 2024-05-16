package ru.nsu.romanov.checker.server;

import ru.nsu.romanov.checker.server.config.Config;
import ru.nsu.romanov.checker.server.config.JsonReader;
import ru.nsu.romanov.checker.server.net.Checker;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        final String confName = "config.json";
        final String arrayFile = "array.txt";

        Config config = new JsonReader().read(new FileReader(confName));

        List<Integer> arr = readInput(new FileReader(arrayFile));

        System.out.println("Res : " + Main(config, arr));
    }

    public static boolean Main(Config config, List<Integer> arr) throws IOException {
        return new Checker(config, arr).Check();
    }

    public static List<Integer> readInput(InputStreamReader reader) {
        List<Integer> res = new ArrayList<>();

        try (Scanner scanner = new Scanner(reader)) {
            while(scanner.hasNext()) {
                res.add(scanner.nextInt());
            }
        }

        return res;
    }
}
