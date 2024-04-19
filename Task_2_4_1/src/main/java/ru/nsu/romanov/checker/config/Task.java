package ru.nsu.romanov.checker.config;

public class Task {
    public String name = "";
    public int score = 0;

    void name(String name) {
        this.name = name;
    }

    void score(int score) {
        this.score = score;
    }
}