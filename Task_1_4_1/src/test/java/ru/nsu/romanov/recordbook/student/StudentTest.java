package ru.nsu.romanov.recordbook.student;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.romanov.recordbook.Semester;

/**
 * tests for class Student.
 */
class StudentTest {

    @Test
    void getSemester() {
        Student student = new Student(new StudentIdx(1), "woww", Semester.FIRST);
        Assertions.assertEquals(Semester.FIRST, student.getSemester());
    }

    @Test
    void getFullName() {
        Student student = new Student(new StudentIdx(1), "woww", Semester.FIRST);
        Assertions.assertEquals("woww", student.getFullName());
    }

    @Test
    void getIndex() {
        StudentIdx idx = new StudentIdx(1);
        Student student = new Student(idx, "woww", Semester.FIRST);
        Assertions.assertEquals(idx, student.getIndex());
    }

    @Test
    void setFullName() {
        Student student = new Student(new StudentIdx(1), "woww", Semester.FIRST);
        student.setFullName("wow");
        Assertions.assertEquals("wow", student.getFullName());
    }

    @Test
    void setSemester() {
        Student student = new Student(new StudentIdx(1), "woww", Semester.FIRST);
        student.setSemester(Semester.SECOND);
        Assertions.assertEquals(Semester.SECOND, student.getSemester());
    }

    @Test
    void testToString() {
        Student student = new Student(new StudentIdx(1), "woww", Semester.FIRST);
        Assertions.assertEquals("Student{" +
                "fullName='" + "woww" + '\'' +
                ", semester=" + Semester.FIRST +
                '}', student.toString());
    }
}