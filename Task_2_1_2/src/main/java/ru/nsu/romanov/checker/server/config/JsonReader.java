package ru.nsu.romanov.checker.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStreamReader;

public class JsonReader implements Reader {
    @Override
    public Config read(InputStreamReader reader) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(reader, Config.class);
    }
}
