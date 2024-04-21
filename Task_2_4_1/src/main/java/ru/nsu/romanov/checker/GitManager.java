package ru.nsu.romanov.checker;

import ru.nsu.romanov.checker.config.Config;

import java.io.IOException;

public class GitManager {
    public void gitClone(Config config) {
        System.out.println("Start cloning...");
        config.getStudents().forEach(student -> {
            System.out.println(
                    "Student: "
                    + student.name
                    + " "
                    + student.repo
                    + " "
                    + student.nickname);
            if (student.name.isEmpty()
                    || student.group.isEmpty()
                    || student.repo.isEmpty()
                    || student.nickname.isEmpty()) {
                throw new IllegalStateException("Empty student's field in config");
            }

            String cloneDirectoryPath = "repos/"
                    + student.group
                    + '/' + student.nickname;
            System.out.println("Cloning "
                    + student.repo
                    + " into "
                    + cloneDirectoryPath);

            try {
                Runtime.getRuntime().exec("git clone "
                        + student.repo
                        + " "
                        + cloneDirectoryPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("Successfully finished!");
    }


}
