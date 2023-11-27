package ru.nsu.romanov.recordbook.student;

import ru.nsu.romanov.recordbook.Semester;

import java.util.Objects;

public class Student {
    private final StudentIdx index;
    private String fullName;
    private Semester semester;

    public Student(StudentIdx index, String fullName, Semester semester) {
        this.fullName = fullName;
        this.semester = semester;
        this.index = index;
    }

    public Semester getSemester() {
        return semester;
    }

    public String getFullName() {
        return fullName;
    }

    public StudentIdx getIndex() {
        return index;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "Student{" +
                "fullName='" + fullName + '\'' +
                ", semester=" + semester +
                '}';
    }
}
