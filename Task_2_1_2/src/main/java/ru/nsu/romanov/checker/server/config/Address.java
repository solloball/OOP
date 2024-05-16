package ru.nsu.romanov.checker.server.config;

import lombok.Data;

/**
 * Class for storing address.
 */
@Data
public class Address {
    private String ip;
    private int port;
}
