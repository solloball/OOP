package ru.nsu.romanov.checker;

import groovy.lang.Binding;
import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyShell;
import ru.nsu.romanov.checker.config.Config;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class Checker {
    public static void run(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("count parameters should be 2");
        }
        String typeOperation = args[0];
        switch (typeOperation) {
            case "clone" -> {
                new GitManager().gitClone(config);
            }
            case "build" -> {

            }
            default -> {
                throw new IllegalArgumentException("unknown type operation: " + typeOperation);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        readConfig();
        run(args);
    }

    private static void readConfig() throws IOException {
        config = new Config();
        Map<String, Config> map = new HashMap<>();
        map.put("config", config);
        Binding binding = new Binding(map);
        GroovyShell shell = new GroovyShell(binding);
        try {
            GroovyCodeSource source = new GroovyCodeSource(new File(path));
            shell.run(source, Collections.emptyList());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    private static Config config;
    private static final String path = "./src/main/java/ru/nsu/romanov/checker/process.groovy";
}