package ru.nsu.romanov.recordbook.subject;

import ru.nsu.romanov.recordbook.Semester;

import java.util.Objects;

public class Subject {
    private String name;
    private TypeObject typeObject;
    private Semester semester;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Subject subject = (Subject) o;
        return Objects.equals(name, subject.name) && typeObject == subject.typeObject && semester == subject.semester;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, typeObject, semester);
    }
}
