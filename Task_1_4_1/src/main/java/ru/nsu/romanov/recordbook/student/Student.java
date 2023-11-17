package ru.nsu.romanov.recordbook.student;

import ru.nsu.romanov.recordbook.Semester;

import java.util.Objects;

public class Student {
    private StudentIdx index;
    private String fullName;
    private Semester semester;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return Objects.equals(index, student.index) && Objects.equals(fullName, student.fullName) && semester == student.semester;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, fullName, semester);
    }
}
