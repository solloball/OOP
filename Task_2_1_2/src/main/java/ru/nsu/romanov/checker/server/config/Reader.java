package ru.nsu.romanov.checker.server.config;

import java.io.IOException;
import java.io.InputStreamReader;

public interface Reader {
    public Config read(InputStreamReader reader) throws IOException;
}
