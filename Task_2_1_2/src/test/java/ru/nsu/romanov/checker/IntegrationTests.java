package ru.nsu.romanov.checker;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.romanov.checker.client.Client;
import ru.nsu.romanov.checker.server.Server;
import ru.nsu.romanov.checker.server.config.Address;
import ru.nsu.romanov.checker.server.config.Config;

/**
 * Integration tests.
 */
public class IntegrationTests {
    @Test
    void twoClientsOneServerTest() throws InterruptedException {
        Config config = new Config();
        config.setResponseTime(1000);

        Address address1 = new Address();
        address1.setIp("localhost");
        address1.setPort(8888);

        Address address2 = new Address();
        address2.setIp("localhost");
        address2.setPort(8889);

        List<Address> addresses = List.of(address1, address2);

        config.setAddresses(addresses);

        new Thread(() -> {
            try {
                Client.Main(8888);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                Client.Main(8889);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                Assertions.assertFalse(Server.javaMain(config, primeNumbers));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        Thread.sleep(1000);
    }

    @Test
    void oneClientOneServerTest() throws InterruptedException {
        Config config = new Config();
        config.setResponseTime(1000);

        Address address1 = new Address();
        address1.setIp("localhost");
        address1.setPort(8888);

        Address address2 = new Address();
        address2.setIp("localhost");
        address2.setPort(8889);

        List<Address> addresses = List.of(address1, address2);

        config.setAddresses(addresses);

        new Thread(() -> {
            try {
                Client.Main(8888);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                Assertions.assertFalse(Server.javaMain(config, primeNumbers));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        Thread.sleep(1000);
    }

    private final List<Integer> primeNumbers = List.of(
            1854438071,
            40167467,
            43965151,
            29927129,
            98648761,
            19730449,
            19730449,
            1854438071,
            1854438071,
            1854438071,
            1854438071,
            1854438071);
}
