package ru.nsu.romanov.checker.config;

/**
 * Contains all info about student.
 */
public class Student {
    public String name = "";
    public String repo = "";
    public String nickname = "";
    public String group = "";

    /**
     * Setter for name.
     *
     * @param name name to set.
     */
    public void name(String name) {
        this.name = name;
    }

    /**
     * Setter for repo.
     *
     * @param repo repo to set.
     */
    public void repo(String repo) {
        this.repo = repo;
    }

    /**
     * Setter for nickname.
     *
     * @param nickname nickname to set.
     */
    public void nickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Setter for group.
     *
     * @param group group to set.
     */
    public void group(String group) {
        this.group = group;
    }
}
