package ru.nsu.romanov.checker.server.config;

import lombok.Data;

import java.util.List;

@Data
public class Config {
    private List<Address> addresses;
    private int responseTime = 100;
    private int port = 8888;
    //private TypeChecker type = TypeChecker.PARALLEL_STREAM;
}
