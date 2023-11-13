package ru.nsu.romanov.finder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Sample class to simulate 1.1 task functionality
 */
public class Finder {

    public List<Long> find(String  target, String path) {
        List<Long> ans = new ArrayList<>();
        String container = "";
        final int bufLen = 1000000;
        char[] buf = new char[bufLen];
        long lenTarget = target.length();
        long index;
        long indexContainer = 0;
        int count;

        System.out.println("start finding!\n");
        try (BufferedReader br = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {
            while ((count = br.read(buf)) != -1) {
                String currentString = new String(buf,0,count);
                currentString = container + currentString;

                while ((index = currentString.indexOf(target)) != -1) {
                    long offset = index + 1;
                    ans.add(indexContainer + index);
                    currentString = currentString.substring((int)offset);
                    indexContainer += offset;
                }

                container = currentString;
                if (lenTarget < container.length()) {
                    long offset = container.length() - lenTarget - 1;
                    container = container.substring((int)offset);
                    indexContainer += offset;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("finding finished!\n");

        return ans;
    }
}