package ru.nsu.romanov.checker.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class Config{
    List<Student> students = new LinkedList<>();
    List<Task> tasks = new LinkedList<>();
}
