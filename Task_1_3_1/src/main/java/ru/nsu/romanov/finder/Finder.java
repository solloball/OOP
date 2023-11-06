package ru.nsu.romanov.finder;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Sample class to simulate 1.1 task functionality
 */
public class Finder {

    String lastString = "";
    public List<Integer> find(String  target, String path) {
        List<Integer> ans = new ArrayList<>();
        int lenTarget = target.length();
        int index;
        int indexContainer = 0;

        System.out.println("start finding!\n");
        try (FileInputStream inputStream = new FileInputStream(path);
                Scanner sc = new Scanner(inputStream, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                String currentString = sc.nextLine();
                currentString = lastString + currentString;

                while ((index = currentString.indexOf(target)) != -1) {
                    int offset = index + 1;
                    ans.add(indexContainer + index);
                    currentString = currentString.substring(offset);
                    indexContainer += offset;
                }

                lastString = currentString;
                if (lenTarget < lastString.length()) {
                    int offset = lastString.length() - lenTarget - 1;
                    lastString = lastString.substring(offset);
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