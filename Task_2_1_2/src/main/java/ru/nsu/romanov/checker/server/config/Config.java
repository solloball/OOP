package ru.nsu.romanov.checker.server.config;

import java.util.List;
import lombok.Data;

/**
 * Class for storing config.
 */
@Data
public class Config {
    private List<Address> addresses;
    private int responseTime = 100;
    private int port = 8888;
}
