package ru.nsu.romanov.checker.config;

import java.util.LinkedList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Contains config info.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Config {
    List<Student> students = new LinkedList<>();
    List<Task> tasks = new LinkedList<>();
    List<ExtraPoints> extraPoints = new LinkedList<>();
    List<Check> checks = new LinkedList<>();
    int coverage = 70;
}
