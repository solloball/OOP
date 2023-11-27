package ru.nsu.romanov.recordbook.subject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.romanov.recordbook.Semester;
import ru.nsu.romanov.recordbook.student.Student;
import ru.nsu.romanov.recordbook.student.StudentIdx;

import static org.junit.jupiter.api.Assertions.*;

class SubjectTest {

    @Test
    void setSemester() {
        Subject subject = new Subject("wow", TypeObject.EXAM, Semester.SECOND, new SubjectIdx(1));
        subject.setSemester(Semester.FIRST);
        Assertions.assertEquals(Semester.FIRST, subject.getSemester());
    }

    @Test
    void getSemester() {
        Subject subject = new Subject("wow", TypeObject.EXAM, Semester.FIRST, new SubjectIdx(1));
        Assertions.assertEquals(Semester.FIRST, subject.getSemester());
    }

    @Test
    void getName() {
        Subject subject = new Subject("wow", TypeObject.EXAM, Semester.FIRST, new SubjectIdx(1));
        Assertions.assertEquals("wow", subject.getName());
    }

    @Test
    void setTypeObject() {
        Subject subject = new Subject("wow", TypeObject.EXAM, Semester.FIRST, new SubjectIdx(1));
        subject.setTypeObject(TypeObject.TEST);
        Assertions.assertEquals(TypeObject.TEST, subject.getTypeObject());
    }

    @Test
    void getTypeObject() {
        Subject subject = new Subject("wow", TypeObject.TEST, Semester.FIRST, new SubjectIdx(1));
        Assertions.assertEquals(TypeObject.TEST, subject.getTypeObject());
    }

    @Test
    void setName() {
        Subject subject = new Subject("wow", TypeObject.TEST, Semester.FIRST, new SubjectIdx(1));
        subject.setName("woww");
        Assertions.assertEquals("woww", subject.getName());
    }

    @Test
    void getIndex() {
        Subject subject = new Subject("wow", TypeObject.TEST, Semester.FIRST, new SubjectIdx(1));
        Assertions.assertEquals(new SubjectIdx(1), subject.getIndex());
    }

    @Test
    void testToString() {
        Subject subject = new Subject("wow", TypeObject.TEST, Semester.FIRST, new SubjectIdx(1));
        Assertions.assertEquals("Subject{" +
                "name='" + "wow" + '\'' +
                ", typeObject=" + TypeObject.TEST +
                ", semester=" + Semester.FIRST +
                '}', subject.toString());
    }
}