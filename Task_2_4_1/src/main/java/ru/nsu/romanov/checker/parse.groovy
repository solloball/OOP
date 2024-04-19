package ru.nsu.romanov.checker

import ru.nsu.romanov.checker.config.Student
import ru.nsu.romanov.checker.config.Task

def task(Closure cl) {
    Task t = new Task()
    cl.delegate = t
    cl.call()
    config.tasks.add(t)
}

def student(Closure cl) {
    Student s = new Student();
    cl.delegate = s
    cl.call()
    config.students.add(s)
}

