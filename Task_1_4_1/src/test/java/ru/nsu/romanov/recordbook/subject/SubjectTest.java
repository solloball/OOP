package ru.nsu.romanov.recordbook.subject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.romanov.recordbook.Semester;

/**
 * Tests for class Subject.
 */
class SubjectTest {

    @Test
    void setSemester() {
        Subject subject = new Subject("wow", TypeSubject.EXAM, Semester.SECOND, new SubjectIdx(1));
        subject.setSemester(Semester.FIRST);
        Assertions.assertEquals(Semester.FIRST, subject.getSemester());
    }

    @Test
    void getSemester() {
        Subject subject = new Subject("wow", TypeSubject.EXAM, Semester.FIRST, new SubjectIdx(1));
        Assertions.assertEquals(Semester.FIRST, subject.getSemester());
    }

    @Test
    void getName() {
        Subject subject = new Subject("wow", TypeSubject.EXAM, Semester.FIRST, new SubjectIdx(1));
        Assertions.assertEquals("wow", subject.getName());
    }

    @Test
    void setTypeObject() {
        Subject subject = new Subject("wow", TypeSubject.EXAM, Semester.FIRST, new SubjectIdx(1));
        subject.setTypeSubject(TypeSubject.TEST);
        Assertions.assertEquals(TypeSubject.TEST, subject.getTypeSubject());
    }

    @Test
    void getTypeObject() {
        Subject subject = new Subject("wow", TypeSubject.TEST, Semester.FIRST, new SubjectIdx(1));
        Assertions.assertEquals(TypeSubject.TEST, subject.getTypeSubject());
    }

    @Test
    void setName() {
        Subject subject = new Subject("wow", TypeSubject.TEST, Semester.FIRST, new SubjectIdx(1));
        subject.setName("woww");
        Assertions.assertEquals("woww", subject.getName());
    }

    @Test
    void getIndex() {
        Subject subject = new Subject("wow", TypeSubject.TEST, Semester.FIRST, new SubjectIdx(1));
        Assertions.assertEquals(new SubjectIdx(1), subject.getIndex());
    }

    @Test
    void testToString() {
        Subject subject = new Subject("wow", TypeSubject.TEST, Semester.FIRST, new SubjectIdx(1));
        Assertions.assertEquals("Subject{"
                + "name='" + "wow" + '\''
                + ", typeObject=" + TypeSubject.TEST
                + ", semester=" + Semester.FIRST
                + '}', subject.toString());
    }
}