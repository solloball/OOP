package ru.nsu.romanov.finder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FinderTest {

    @Test
    void testFind() {
        Finder f = new Finder();
        System.out.println(f.find("1", "test.txt"));
    }
}