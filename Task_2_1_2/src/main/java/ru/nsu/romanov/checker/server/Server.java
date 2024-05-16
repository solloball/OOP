package ru.nsu.romanov.checker.server;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ru.nsu.romanov.checker.server.config.Config;
import ru.nsu.romanov.checker.server.config.JsonReader;
import ru.nsu.romanov.checker.server.net.Checker;

/**
 * Main class of realization of server.
 */
public class Server {
    /**
     * Method for cli using, read config from file config.json, arr from array.txt.
     *
     * @param args unused.
     * @throws IOException can throw IOException.
     */
    public static void main(String[] args) throws IOException {
        final String confName = "config.json";
        final String arrayFile = "array.txt";

        Config config = new JsonReader().read(new FileReader(confName));

        List<Integer> arr = readInput(new FileReader(arrayFile));

        System.out.println("Res : " + javaMain(config, arr));
    }

    /**
     * Used for using inside java.
     *
     * @param config config.
     * @param arr array of number.
     * @return true if there is a composite number.
     * @throws IOException can throw IOException.
     */
    public static boolean javaMain(Config config, List<Integer> arr) throws IOException {
        return new Checker(config, arr).check();
    }

    /**
     * Read array from stream.
     *
     * @param reader stream with array.
     * @return return array in list.
     */
    public static List<Integer> readInput(InputStreamReader reader) {
        List<Integer> res = new ArrayList<>();

        try (Scanner scanner = new Scanner(reader)) {
            while (scanner.hasNext()) {
                res.add(scanner.nextInt());
            }
        }

        return res;
    }
}
