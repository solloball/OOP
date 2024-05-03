package ru.nsu.romanov.checker;

import groovy.lang.Binding;
import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyShell;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import ru.nsu.romanov.checker.config.Config;
import ru.nsu.romanov.checker.html.HtmlBuilder;
import ru.nsu.romanov.checker.kernel.Checker;
import ru.nsu.romanov.checker.kernel.StudentInfo;

/**
 * Main class, read config, run checking and run html builder.
 */
public class Main {
    /**
     * Main function for running main.
     *
     * @param args should have size one.
     * @throws IOException if args size will be not equal to one.
     */
    public static void run(String[] args) throws IOException {
        if (args.length != 1) {
            throw new IllegalArgumentException("count parameters should be 2");
        }
        String typeOperation = args[0];
        switch (typeOperation) {
            case "clone" -> {
                new Checker().gitClone(config);
            }
            case "build" -> {
                Map<String, StudentInfo> res = new Checker().build(config);
                new HtmlBuilder().build(res, config);
            }
            case "all" -> {
                Map<String, StudentInfo> res = new Checker().full(config);
                new HtmlBuilder().build(res, config);
            }
            default -> {
                throw new IllegalArgumentException("unknown type operation: " + typeOperation);
            }
        }
    }

    /**
     * Main method.
     *
     * @param args args should have size one/
     * @throws IOException throw if args size will not equal to one.
     */
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
    private static final String path =
            "./src/main/java/ru/nsu/"
            + "romanov/checker/config/process.groovy";
}