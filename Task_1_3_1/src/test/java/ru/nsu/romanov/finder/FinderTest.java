package ru.nsu.romanov.finder;

import static java.lang.Math.abs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
Tests for class Finder.
 */
class FinderTest {

    @Test
    void testFindBasic() {
        Finder f = new Finder();
        List<Long> expectedList = new ArrayList<>();
        expectedList.add((long) 5);
        Assertions.assertEquals(expectedList, f.find("ab", Objects.requireNonNull(getClass()
                .getClassLoader().getResource("basic")).getPath()));
    }

    @Test
    void testFindArabic() {
        Finder f = new Finder();
        List<Long> expectedList = new ArrayList<>();
        expectedList.add((long) 8);
        Assertions.assertEquals(expectedList, f.find("۞", Objects.requireNonNull(getClass()
                .getClassLoader().getResource("arabicSymbols")).getPath()));
    }

    @Test
    void testFindHieroglyph() {
        Finder f = new Finder();
        List<Long> expectedList = new ArrayList<>();
        expectedList.add((long) 19);
        Assertions.assertEquals(expectedList, f.find("⿓", Objects.requireNonNull(getClass()
                .getClassLoader().getResource("hieroglyph")).getPath()));
    }

    @Test
    void testFindBetweenLines() {
        Finder f = new Finder();
        List<Long> expectedList = new ArrayList<>();
        expectedList.add((long) 4);
        Assertions.assertEquals(expectedList, f.find("ab", Objects.requireNonNull(getClass()
                .getClassLoader().getResource("targetBetweenLines")).getPath()));
    }

    @Test
    void testFindBetweenTarget() {
        Finder f = new Finder();
        List<Long> expectedList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            expectedList.add((long) i);
        }
        Assertions.assertEquals(expectedList, f.find("aaa", Objects.requireNonNull(getClass()
                .getClassLoader().getResource("betweenTarget")).getPath()));
    }


    @Test
    void testBetweenButch() {
        Finder f = new Finder();
        long sizeButch = 1000000;
        List<Long> expectedList = new ArrayList<>();
        expectedList.add(sizeButch - 1);
        try {
            File file = new File("testBetweenButch");
            if (!file.createNewFile()) {
                throw new IOException("failed to create file");
            }
            file.deleteOnExit();

            try (FileWriter wr = new FileWriter(file)) {
                for (int i = 0; i < sizeButch; i++) {
                    wr.write("a");
                }
                wr.write("b");
            }

            Assertions.assertEquals(expectedList, f.find("ab", file.getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testNumber() {
        Finder f = new Finder();
        List<Long> expectedList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            expectedList.add((long) 10 * i + 3);
        }
        try {
            File file = new File("testNumber");
            if (!file.createNewFile()) {
                throw new IOException("failed to create file");
            }
            file.deleteOnExit();
            try (FileWriter wr = new FileWriter(file)) {
                for (int i = 0; i < 100; i++) {
                    for (int j = 0; j <= 9; j++) {
                        wr.write('0' + j);
                    }
                }
            }
            Assertions.assertEquals(expectedList, f.find("3", file.getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void realBigFile() {
        Finder f = new Finder();
        List<Long> expectedList = new ArrayList<>();
        expectedList.add((long) 100);
        expectedList.add((long) 1024 * 1024);
        expectedList.add((long) 1900000050);
        try {
            File file = new File("largeFile");
            file.deleteOnExit();
            Random rd = new Random();
            long largeFileCountSymbols = 12_000_000_000L;
            if (!file.createNewFile()) {
                throw new IOException("failed to create file");
            }
            try (FileWriter wr = new FileWriter(file)) {
                for (long i = 0; i < largeFileCountSymbols; i++) {
                    if (i == 100 || i == 1024 * 1024 || i == 1900000050) {
                        wr.write("1");
                    } else {
                        wr.write((abs(rd.nextInt()) % 100) + 50);
                    }
                }
                Assertions.assertEquals(expectedList, f.find("1", file.getPath()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}