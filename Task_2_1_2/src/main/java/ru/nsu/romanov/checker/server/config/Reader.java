package ru.nsu.romanov.checker.server.config;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Interface for reading config from stream.
 */
public interface Reader {
    /**
     * Read config from a stream.
     *
     * @param reader stream.
     * @return config.
     * @throws IOException can throw IOException.
     */
    Config read(InputStreamReader reader) throws IOException;
}
