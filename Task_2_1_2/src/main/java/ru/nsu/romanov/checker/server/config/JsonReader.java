package ru.nsu.romanov.checker.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Reader a config from json file.
 */
public class JsonReader implements Reader {
    /**
     * Read from json file config.
     *
     * @param reader json stream.
     * @return config.
     * @throws IOException can throw IOException.
     */
    @Override
    public Config read(InputStreamReader reader) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(reader, Config.class);
    }
}
