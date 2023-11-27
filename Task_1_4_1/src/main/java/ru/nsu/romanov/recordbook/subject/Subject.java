package ru.nsu.romanov.recordbook.subject;

import ru.nsu.romanov.recordbook.Semester;

import java.util.Objects;

public class Subject {
    private String name;
    private TypeObject typeObject;
    private Semester semester;
    private final SubjectIdx index;

    public Subject(String name, TypeObject typeObject, Semester semester, SubjectIdx subjectIdx) {
        this.name = name;
        this.index = subjectIdx;
        this.semester = semester;
        this.typeObject = typeObject;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Semester getSemester() {
        return semester;
    }

    public String getName() {
        return name;
    }

    public void setTypeObject(TypeObject typeObject) {
        this.typeObject = typeObject;
    }

    public TypeObject getTypeObject() {
        return typeObject;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubjectIdx getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", typeObject=" + typeObject +
                ", semester=" + semester +
                '}';
    }
}
