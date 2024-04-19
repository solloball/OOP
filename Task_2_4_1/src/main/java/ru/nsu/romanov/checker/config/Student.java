package ru.nsu.romanov.checker.config;

public class Student {
    public String name = "";
    public String repo = "";
    public String nickname = "";
    public String group = "";

    public void name(String name) {
        this.name = name;
    }

    public void repo(String repo) {
        this.repo = repo;
    }

    public void nickname(String nickname) {
        this.nickname = nickname;
    }

    public void group(String group) {
        this.group = group;
    }
}
