package ru.nsu.romanov.recordbook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.romanov.recordbook.student.Student;
import ru.nsu.romanov.recordbook.student.StudentIdx;
import ru.nsu.romanov.recordbook.subject.Subject;
import ru.nsu.romanov.recordbook.subject.SubjectIdx;
import ru.nsu.romanov.recordbook.subject.TypeSubject;

/**
 * Tests for record book.
 */
class RecordBookTest {

    @Test
    void getStudentBasic() {
        RecordBook recordBook = new RecordBook();
        Student expStudent = new Student(new StudentIdx(0), "wiow", Semester.FIRST);
        recordBook.addStudent("wiow", Semester.FIRST);
        Student student = recordBook.getStudent("wiow");
        Assertions.assertEquals(expStudent.getIndex(), student.getIndex());
        Assertions.assertEquals(expStudent.getFullName(), student.getFullName());
        Assertions.assertEquals(expStudent.getSemester(), student.getSemester());
    }

    @Test
    void getStudentWithNotExistingStudent() {
        RecordBook recordBook = new RecordBook();
        Assertions.assertNull(recordBook.getStudent("wow"));
    }

    @Test
    void getSubjectBasic() {
        RecordBook recordBook = new RecordBook();
        recordBook.addSubject("wow", Semester.FIRST, TypeSubject.TEST);
        Subject expSubject = new Subject("wow", TypeSubject.TEST, Semester.FIRST, new SubjectIdx(0));
        Subject subject = recordBook.getSubject("wow");
        Assertions.assertEquals(expSubject.getIndex(), subject.getIndex());
        Assertions.assertEquals(expSubject.getName(), subject.getName());
        Assertions.assertEquals(expSubject.getSemester(), subject.getSemester());
        Assertions.assertEquals(expSubject.getTypeSubject(), subject.getTypeSubject());
    }
    @Test
    void getSubjectWithNotExistingSubject() {
        RecordBook recordBook = new RecordBook();
        Assertions.assertNull(recordBook.getSubject("NONE"));
    }

    @Test
    void addStudentBasic() {
        RecordBook recordBook = new RecordBook();
        Student expStudent = new Student(new StudentIdx(0), "wiow", Semester.FIRST);
        Assertions.assertTrue(recordBook.addStudent("wiow", Semester.FIRST));
        Student student = recordBook.getStudent("wiow");
        Assertions.assertEquals(expStudent.getIndex(), student.getIndex());
        Assertions.assertEquals(expStudent.getFullName(), student.getFullName());
        Assertions.assertEquals(expStudent.getSemester(), student.getSemester());
    }

    @Test
    void addStudentBasicTwoStudents() {
        RecordBook recordBook = new RecordBook();
        Assertions.assertTrue(recordBook.addStudent("wiow", Semester.SECOND));
        Assertions.assertTrue(recordBook.addStudent("wow", Semester.FIRST));
        Student expStudent2 = new Student(new StudentIdx(1), "wow", Semester.FIRST);
        Student student2 = recordBook.getStudent("wow");
        Assertions.assertEquals(expStudent2.getIndex(), student2.getIndex());
        Assertions.assertEquals(expStudent2.getFullName(), student2.getFullName());
        Assertions.assertEquals(expStudent2.getSemester(), student2.getSemester());
        Student expStudent1 = new Student(new StudentIdx(0), "wiow", Semester.SECOND);
        Student student1 = recordBook.getStudent("wiow");
        Assertions.assertEquals(expStudent1.getIndex(), student1.getIndex());
        Assertions.assertEquals(expStudent1.getFullName(), student1.getFullName());
        Assertions.assertEquals(expStudent1.getSemester(), student1.getSemester());
    }

    @Test
    void addStudentExistingStudentDifferentSemenster() {
        RecordBook recordBook = new RecordBook();
        Assertions.assertTrue(recordBook.addStudent("wow", Semester.SECOND));
        Assertions.assertFalse(recordBook.addStudent("wow", Semester.FIFTH));
    }

    @Test
    void addStudentExistingStudentEqSemester() {
        RecordBook recordBook = new RecordBook();
        Assertions.assertTrue(recordBook.addStudent("wow", Semester.SECOND));
        Assertions.assertFalse(recordBook.addStudent("wow", Semester.SECOND));
    }


    @Test
    void transferSemesterBasicDowner() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.FIFTH);
        recordBook.transferSemester("wow", Semester.SECOND);
        Assertions.assertEquals(Semester.SECOND, recordBook.getStudent("wow").getSemester());
    }

    @Test
    void transferSemesterBasicUpper() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.FIFTH);
        recordBook.transferSemester("wow", Semester.SIXTH);
        Assertions.assertEquals(Semester.SIXTH, recordBook.getStudent("wow").getSemester());
    }

    @Test
    void transferSemesterNotExistingStudent() {
        RecordBook recordBook = new RecordBook();
        Assertions.assertFalse(recordBook.transferSemester("wow", Semester.SIXTH));
    }

    @Test
    void transferSemesterDownerWithMarks() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.FIFTH);
        recordBook.addSubject("sub1", Semester.FIFTH, TypeSubject.GRADED_TEST);
        recordBook.addSubject("sub2", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub1", "wow", Mark.FIVE);
        recordBook.setStudentMark("sub2", "wow", Mark.TWO);
        recordBook.transferSemester("wow", Semester.FOURTH);
        Assertions.assertEquals(Mark.UNDEFINED, recordBook.getStudentMark("wow", "sub1"));
        Assertions.assertEquals(Mark.TWO, recordBook.getStudentMark("wow", "sub2"));
    }

    @Test
    void transferSemesterUpperWithMarks() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.FIFTH);
        recordBook.addSubject("sub1", Semester.FIFTH, TypeSubject.EXAM);
        recordBook.addSubject("sub2", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub1", "wow", Mark.FIVE);
        recordBook.setStudentMark("sub2", "wow", Mark.TWO);
        recordBook.transferSemester("wow", Semester.SIXTH);
        Assertions.assertEquals(Mark.FIVE, recordBook.getStudentMark("wow", "sub1"));
        Assertions.assertEquals(Mark.TWO, recordBook.getStudentMark("wow", "sub2"));
    }

    @Test
    void removeStudentBasic() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.FIFTH);
        Assertions.assertTrue(recordBook.removeStudent("wow"));
        Assertions.assertNull(recordBook.getStudent("wow"));
    }

    @Test
    void removeNotExistingStudent() {
        RecordBook recordBook = new RecordBook();
        Assertions.assertFalse(recordBook.removeStudent("wow"));
    }

    @Test
    void removeStudentIndex() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("stud", Semester.FIRST);
        recordBook.removeStudent("stud");
        recordBook.addStudent("stud", Semester.FIRST);
        Student expStudent = new Student(new StudentIdx(0), "stud", Semester.FIRST);
        recordBook.addStudent("stud", Semester.FIRST);
        Student student = recordBook.getStudent("stud");
        Assertions.assertEquals(expStudent.getIndex(), student.getIndex());
        Assertions.assertEquals(expStudent.getFullName(), student.getFullName());
        Assertions.assertEquals(expStudent.getSemester(), student.getSemester());
    }

    @Test
    void removeStudentIndexDifferentName() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("stud", Semester.FIRST);
        recordBook.removeStudent("stud");
        recordBook.addStudent("stud2", Semester.FIRST);
        Student expStudent = new Student(new StudentIdx(0), "stud2", Semester.FIRST);
        Student student = recordBook.getStudent("stud2");
        Assertions.assertEquals(expStudent.getIndex(), student.getIndex());
        Assertions.assertEquals(expStudent.getFullName(), student.getFullName());
        Assertions.assertEquals(expStudent.getSemester(), student.getSemester());
    }

    @Test
    void removeStudentWithMarks() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("stud", Semester.FIRST);
        recordBook.addSubject("subj", Semester.FIRST, TypeSubject.EXAM);
        recordBook.addSubject("subj2", Semester.FIRST, TypeSubject.EXAM);
        recordBook.setStudentMark("subj", "stud", Mark.TWO);
        recordBook.setStudentMark("subj2", "stud", Mark.FIVE);
        recordBook.removeStudent("stud");
        recordBook.addStudent("stud", Semester.FIRST);
        Assertions.assertEquals(Mark.UNDEFINED, recordBook.getStudentMark("stud", "subj"));
        Assertions.assertEquals(Mark.UNDEFINED, recordBook.getStudentMark("stud", "subj2"));
    }

    @Test
    void removeStudentWithMarksWithDifferentNames() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("stud", Semester.FIRST);
        recordBook.addSubject("subj", Semester.FIRST, TypeSubject.EXAM);
        recordBook.addSubject("subj2", Semester.FIRST, TypeSubject.EXAM);
        recordBook.setStudentMark("subj", "stud", Mark.TWO);
        recordBook.setStudentMark("subj2", "stud", Mark.FIVE);
        recordBook.removeStudent("stud");
        recordBook.addStudent("anotherStud", Semester.FIRST);
        Assertions.assertEquals(Mark.UNDEFINED, recordBook.getStudentMark("anotherStud", "subj"));
        Assertions.assertEquals(Mark.UNDEFINED, recordBook.getStudentMark("anotherStud", "subj2"));
    }

    @Test
    void isPossibleToHaveIncreasedScholarshipBasic() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.SECOND);
        Assertions.assertTrue(recordBook.isPossibleToHaveIncreasedScholarship("wow"));
    }

    @Test
    void isPossibleToHaveIncreasedScholarshipBasicWithSubject() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.SECOND);
        recordBook.addSubject("sub", Semester.SECOND, TypeSubject.EXAM);
        Assertions.assertTrue(recordBook.isPossibleToHaveIncreasedScholarship("wow"));
    }

    @Test
    void isPossibleToHaveIncreasedScholarshipBasicWithSubjectWithMarks() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.SECOND);
        recordBook.addSubject("sub", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub", "wow", Mark.FOUR);
        Assertions.assertTrue(recordBook.isPossibleToHaveIncreasedScholarship("wow"));
    }

    @Test
    void isPossibleToHaveIncreasedScholarshipBasicWithSubjectWithBadMarks() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.SECOND);
        recordBook.addSubject("sub", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub", "wow", Mark.TWO);
        Assertions.assertFalse(recordBook.isPossibleToHaveIncreasedScholarship("wow"));
    }

    @Test
    void isPossibleToHaveIncreasedScholarshipWithNotExistingStudent() {
        RecordBook recordBook = new RecordBook();
        Assertions.assertFalse(recordBook.isPossibleToHaveIncreasedScholarship("wow"));
    }

    @Test
    void isPossibleToHaveIncreasedScholarshipWithStudentOnFirstSemester() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.FIRST);
        Assertions.assertFalse(recordBook.isPossibleToHaveIncreasedScholarship("wow"));
    }

    @Test
    void isPossibleToHaveIncreasedScholarshipWithStudentOnFirstSemesterWithGoodMarks() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.FIRST);
        recordBook.addSubject("sub", Semester.FIRST,TypeSubject.EXAM);
        recordBook.setStudentMark("sub", "wow", Mark.FIVE);
        Assertions.assertFalse(recordBook.isPossibleToHaveIncreasedScholarship("wow"));
    }

    @Test
    void isPossibleToHaveIncreasedScholarshipWithStudentOnLastCourse() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.TENTH);
        Assertions.assertFalse(recordBook.isPossibleToHaveIncreasedScholarship("wow"));
    }

    @Test
    void isPossibleToHaveIncreasedScholarshipWithStudentOnLastSemesterWithGoodMarks() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.TENTH);
        recordBook.addSubject("sub", Semester.TENTH,TypeSubject.EXAM);
        recordBook.setStudentMark("sub", "wow", Mark.FIVE);
        Assertions.assertFalse(recordBook.isPossibleToHaveIncreasedScholarship("wow"));
    }

    @Test
    void isPossibleToHaveIncreasedScholarshipWithBadMarksInPrevSemester() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.SECOND);
        recordBook.addSubject("sub", Semester.FIRST,TypeSubject.EXAM);
        recordBook.setStudentMark("sub", "wow", Mark.TWO);
        Assertions.assertTrue(recordBook.isPossibleToHaveIncreasedScholarship("wow"));
    }

    @Test
    void isPossibleToHaveIncreasedScholarshipWithBadMarksInNextSemester() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.SECOND);
        recordBook.addSubject("sub", Semester.FIFTH,TypeSubject.EXAM);
        recordBook.setStudentMark("sub", "wow", Mark.TWO);
        Assertions.assertTrue(recordBook.isPossibleToHaveIncreasedScholarship("wow"));
    }

    @Test
    void isPossibleToHaveIncreasedScholarshipWithRetakeFour() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.SECOND);
        recordBook.addSubject("sub", Semester.SECOND,TypeSubject.EXAM);
        recordBook.setStudentMark("sub", "wow", Mark.FOUR_RETAKED);
        Assertions.assertFalse(recordBook.isPossibleToHaveIncreasedScholarship("wow"));
    }

    @Test
    void isPossibleToHaveIncreasedScholarshipWithRetakeFive() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.SECOND);
        recordBook.addSubject("sub", Semester.SECOND,TypeSubject.EXAM);
        recordBook.setStudentMark("sub", "wow", Mark.FIVE_RETAKED);
        Assertions.assertFalse(recordBook.isPossibleToHaveIncreasedScholarship("wow"));
    }

    @Test
    void isPossibleToHaveIncreasedScholarshipWithThreeInTest() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.SECOND);
        recordBook.addSubject("sub", Semester.SECOND,TypeSubject.TEST);
        recordBook.setStudentMark("sub", "wow", Mark.THREE);
        Assertions.assertTrue(recordBook.isPossibleToHaveIncreasedScholarship("wow"));
    }

    @Test
    void isPossibleToHaveRedDiplomaBasic() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.SECOND);
        Assertions.assertTrue(recordBook.isPossibleToHaveRedDiploma("wow"));
    }

    @Test
    void isPossibleToHaveRedDiplomaBasicWithMarks() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.SECOND);
        recordBook.addSubject("sub", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub", "wow", Mark.FIVE);
        Assertions.assertTrue(recordBook.isPossibleToHaveRedDiploma("wow"));
    }

    @Test
    void isPossibleToHaveRedDiplomaBasicWithThreeInTest() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.SECOND);
        recordBook.addSubject("sub", Semester.SECOND, TypeSubject.TEST);
        recordBook.setStudentMark("sub", "wow", Mark.THREE);
        Assertions.assertTrue(recordBook.isPossibleToHaveRedDiploma("wow"));
    }

    @Test
    void isPossibleToHaveRedDiplomaBasicWithThree() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.SECOND);
        recordBook.addSubject("sub", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub", "wow", Mark.THREE);
        Assertions.assertFalse(recordBook.isPossibleToHaveRedDiploma("wow"));
    }

    @Test
    void isPossibleToHaveRedDiplomaBasicWithGoodMarks() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.SECOND);
        recordBook.addSubject("sub1", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub1", "wow", Mark.FIVE);
        recordBook.addSubject("sub2", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub2", "wow", Mark.FIVE);
        recordBook.addSubject("sub3", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub3", "wow", Mark.FIVE);
        recordBook.addSubject("sub4", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub4", "wow", Mark.FIVE);
        recordBook.addSubject("sub5", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub5", "wow", Mark.FOUR);
        Assertions.assertTrue(recordBook.isPossibleToHaveRedDiploma("wow"));
    }

    @Test
    void isPossibleToHaveRedDiplomaBasicWithBadMarks() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.SECOND);
        recordBook.addSubject("sub1", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub1", "wow", Mark.FIVE);
        recordBook.addSubject("sub2", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub2", "wow", Mark.FIVE);
        recordBook.addSubject("sub3", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub3", "wow", Mark.FIVE);
        recordBook.addSubject("sub4", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub4", "wow", Mark.FOUR);
        recordBook.addSubject("sub5", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub5", "wow", Mark.FOUR);
        Assertions.assertFalse(recordBook.isPossibleToHaveRedDiploma("wow"));
    }

    @Test
    void isPossibleToHaveRedDiplomaBasicWithRetakeMarks() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.SECOND);
        recordBook.addSubject("sub1", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub1", "wow", Mark.FIVE_RETAKED);
        recordBook.addSubject("sub2", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub2", "wow", Mark.FIVE_RETAKED);
        recordBook.addSubject("sub3", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub3", "wow", Mark.FIVE_RETAKED);
        recordBook.addSubject("sub4", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub4", "wow", Mark.FIVE_RETAKED);
        recordBook.addSubject("sub5", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub5", "wow", Mark.FOUR_RETAKED);
        Assertions.assertTrue(recordBook.isPossibleToHaveRedDiploma("wow"));
    }

    @Test
    void isPossibleToHaveRedDiplomaBasicWithOneThree() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("wow", Semester.SIXTH);
        recordBook.addSubject("sub1", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub1", "wow", Mark.FIVE_RETAKED);
        recordBook.addSubject("sub2", Semester.THIRD, TypeSubject.EXAM);
        recordBook.setStudentMark("sub2", "wow", Mark.FIVE_RETAKED);
        recordBook.addSubject("sub3", Semester.THIRD, TypeSubject.EXAM);
        recordBook.setStudentMark("sub3", "wow", Mark.FIVE_RETAKED);
        recordBook.addSubject("sub4", Semester.THIRD, TypeSubject.EXAM);
        recordBook.setStudentMark("sub4", "wow", Mark.FIVE_RETAKED);
        recordBook.addSubject("sub5", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub5", "wow", Mark.FIVE_RETAKED);
        recordBook.addSubject("sub6", Semester.SIXTH, TypeSubject.EXAM);
        recordBook.setStudentMark("sub6", "wow", Mark.FIVE_RETAKED);
        recordBook.addSubject("sub7", Semester.SIXTH, TypeSubject.EXAM);
        recordBook.setStudentMark("sub7", "wow", Mark.FIVE_RETAKED);
        recordBook.addSubject("sub8", Semester.SIXTH, TypeSubject.EXAM);
        recordBook.setStudentMark("sub8", "wow", Mark.FIVE_RETAKED);
        recordBook.addSubject("sub9", Semester.FIFTH, TypeSubject.EXAM);
        recordBook.setStudentMark("sub9", "wow", Mark.FIVE_RETAKED);
        recordBook.addSubject("sub10", Semester.FIRST, TypeSubject.EXAM);
        recordBook.setStudentMark("sub10", "wow", Mark.THREE);
        Assertions.assertFalse(recordBook.isPossibleToHaveRedDiploma("wow"));
    }

    @Test
    void StudentInfoBasic() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("stud", Semester.FIFTH);
        Student student = new Student(new StudentIdx(0), "stud", Semester.FIFTH);
        Assertions.assertEquals(student.toString() + "\n", recordBook.studentInfo("stud"));
    }

    @Test
    void StudentInfoBasicWithsSubject() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("stud", Semester.FIFTH);
        recordBook.addSubject("sub", Semester.SECOND, TypeSubject.EXAM);
        Subject subject = new Subject("sub", TypeSubject.EXAM, Semester.SECOND, new SubjectIdx(0));
        Student student = new Student(new StudentIdx(0), "stud", Semester.FIFTH);
        Assertions.assertEquals(student + "\n" + subject + " Mark : There is no information\n",
                recordBook.studentInfo("stud"));
    }

    @Test
    void StudentInfoBasicWithsSubjectWithMark() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("stud", Semester.FIFTH);
        recordBook.addSubject("sub", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub", "stud", Mark.THREE);
        Subject subject = new Subject("sub", TypeSubject.EXAM, Semester.SECOND, new SubjectIdx(0));
        Student student = new Student(new StudentIdx(0), "stud", Semester.FIFTH);
        Assertions.assertEquals(student + "\n" + subject + " Mark : " + Mark.THREE + "\n",
                recordBook.studentInfo("stud"));
    }

    @Test
    void StudentInfoBasicWithsSubjectWithMarkTest() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("stud", Semester.FIFTH);
        recordBook.addSubject("sub", Semester.SECOND, TypeSubject.TEST);
        recordBook.setStudentMark("sub", "stud", Mark.THREE);
        Subject subject = new Subject("sub", TypeSubject.TEST, Semester.SECOND, new SubjectIdx(0));
        Student student = new Student(new StudentIdx(0), "stud", Semester.FIFTH);
        Assertions.assertEquals(student + "\n" + subject + " Mark : Pass\n",
                recordBook.studentInfo("stud"));
    }

    @Test
    void StudentInfoBasicWithsSubjectWithFiveTest() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("stud", Semester.FIFTH);
        recordBook.addSubject("sub", Semester.SECOND, TypeSubject.TEST);
        recordBook.setStudentMark("sub", "stud", Mark.FIVE);
        Subject subject = new Subject("sub", TypeSubject.TEST, Semester.SECOND, new SubjectIdx(0));
        Student student = new Student(new StudentIdx(0), "stud", Semester.FIFTH);
        Assertions.assertEquals(student + "\n" + subject + " Mark : Pass\n",
                recordBook.studentInfo("stud"));
    }

    @Test
    void StudentInfoBasicWithsSubjectWithFourTest() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("stud", Semester.FIFTH);
        recordBook.addSubject("sub", Semester.SECOND, TypeSubject.TEST);
        recordBook.setStudentMark("sub", "stud", Mark.FOUR);
        Subject subject = new Subject("sub", TypeSubject.TEST, Semester.SECOND, new SubjectIdx(0));
        Student student = new Student(new StudentIdx(0), "stud", Semester.FIFTH);
        Assertions.assertEquals(student + "\n" + subject + " Mark : Pass\n",
                recordBook.studentInfo("stud"));
    }

    @Test
    void StudentInfoBasicWithsSubjectWithTwoTest() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("stud", Semester.FIFTH);
        recordBook.addSubject("sub", Semester.SECOND, TypeSubject.TEST);
        recordBook.setStudentMark("sub", "stud", Mark.TWO);
        Subject subject = new Subject("sub", TypeSubject.TEST, Semester.SECOND, new SubjectIdx(0));
        Student student = new Student(new StudentIdx(0), "stud", Semester.FIFTH);
        Assertions.assertEquals(student + "\n" + subject + " Mark : Academic Debt\n",
                recordBook.studentInfo("stud"));
    }

    @Test
    void StudentInfoBasicWithsSubjectWithRetakeMark() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("stud", Semester.FIFTH);
        recordBook.addSubject("sub", Semester.SECOND, TypeSubject.GRADED_TEST);
        recordBook.setStudentMark("sub", "stud", Mark.FIVE_RETAKED);
        Subject subject = new Subject("sub", TypeSubject.GRADED_TEST, Semester.SECOND, new SubjectIdx(0));
        Student student = new Student(new StudentIdx(0), "stud", Semester.FIFTH);
        Assertions.assertEquals(student + "\n" + subject + " Mark : " + Mark.FIVE_RETAKED + "\n",
                recordBook.studentInfo("stud"));
    }

    @Test
    void StudentInfoBasicWithTwoSubjects() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("stud", Semester.FIFTH);
        recordBook.addSubject("sub", Semester.SECOND, TypeSubject.GRADED_TEST);
        recordBook.setStudentMark("sub", "stud", Mark.FIVE_RETAKED);
        recordBook.addSubject("sub2", Semester.SECOND, TypeSubject.EXAM);
        recordBook.setStudentMark("sub2", "stud", Mark.FOUR);
        Subject subject = new Subject("sub", TypeSubject.GRADED_TEST, Semester.SECOND, new SubjectIdx(0));
        Subject subject2 = new Subject("sub2", TypeSubject.EXAM, Semester.SECOND, new SubjectIdx(0));
        Student student = new Student(new StudentIdx(0), "stud", Semester.FIFTH);
        Assertions.assertEquals(student + "\n" + subject + " Mark : " + Mark.FIVE_RETAKED + "\n"
                + subject2 + " Mark : " + Mark.FOUR + "\n",
                recordBook.studentInfo("stud"));
    }

    @Test
    void addSubjectBasic() {
        RecordBook recordBook = new RecordBook();
        Assertions.assertTrue(recordBook.addSubject("wow", Semester.SECOND, TypeSubject.EXAM));
        Subject subject = recordBook.getSubject("wow");
        Subject expSubject = new Subject("wow", TypeSubject.EXAM, Semester.SECOND, new SubjectIdx(0));
        Assertions.assertEquals(expSubject.getTypeSubject(), subject.getTypeSubject());
        Assertions.assertEquals(expSubject.getSemester(), subject.getSemester());
        Assertions.assertEquals(expSubject.getIndex(), subject.getIndex());
        Assertions.assertEquals(expSubject.getTypeSubject(), subject.getTypeSubject());
    }

    @Test
    void addTwoSubjectBasic() {
        RecordBook recordBook = new RecordBook();
        Assertions.assertTrue(recordBook.addSubject("wow", Semester.SECOND, TypeSubject.EXAM));
        Assertions.assertTrue(recordBook.addSubject("woiw", Semester.FIFTH, TypeSubject.TEST));
        Subject subject = recordBook.getSubject("wow");
        Subject expSubject = new Subject("wow", TypeSubject.EXAM, Semester.SECOND, new SubjectIdx(0));
        Assertions.assertEquals(expSubject.getTypeSubject(), subject.getTypeSubject());
        Assertions.assertEquals(expSubject.getSemester(), subject.getSemester());
        Assertions.assertEquals(expSubject.getIndex(), subject.getIndex());
        Assertions.assertEquals(expSubject.getTypeSubject(), subject.getTypeSubject());
        Subject subject2 = recordBook.getSubject("woiw");
        Subject expSubject2 = new Subject("woiw", TypeSubject.TEST, Semester.FIFTH, new SubjectIdx(1));
        Assertions.assertEquals(expSubject2.getTypeSubject(), subject2.getTypeSubject());
        Assertions.assertEquals(expSubject2.getSemester(), subject2.getSemester());
        Assertions.assertEquals(expSubject2.getIndex(), subject2.getIndex());
        Assertions.assertEquals(expSubject2.getTypeSubject(), subject2.getTypeSubject());
    }

    @Test
    void addExistingEqualSubject() {
        RecordBook recordBook = new RecordBook();
        Assertions.assertTrue(recordBook.addSubject("wow", Semester.SECOND, TypeSubject.EXAM));
        Assertions.assertFalse(recordBook.addSubject("wow", Semester.SECOND, TypeSubject.EXAM));
    }

    @Test
    void addExistingNotEqualSubject() {
        RecordBook recordBook = new RecordBook();
        Assertions.assertTrue(recordBook.addSubject("wow", Semester.SECOND, TypeSubject.EXAM));
        Assertions.assertFalse(recordBook.addSubject("wow", Semester.FIFTH, TypeSubject.EXAM));
        Assertions.assertFalse(recordBook.addSubject("wow", Semester.SECOND, TypeSubject.TEST));
    }

    @Test
    void removeSubjectBasic() {
        RecordBook recordBook = new RecordBook();
        recordBook.addSubject("test", Semester.FIFTH, TypeSubject.GRADED_TEST);
        Assertions.assertTrue(recordBook.removeSubject("test"));
        Assertions.assertNull(recordBook.getSubject("test"));
    }

    @Test
    void removeNotExistingSubject() {
        RecordBook recordBook = new RecordBook();
        Assertions.assertFalse(recordBook.removeSubject("test"));
    }

    @Test
    void removeSubjectIndex() {
        RecordBook recordBook = new RecordBook();
        recordBook.addSubject("test", Semester.FIFTH, TypeSubject.EXAM);
        recordBook.removeSubject("test");
        recordBook.addSubject("test", Semester.FIFTH, TypeSubject.EXAM);
        Subject subject = recordBook.getSubject("test");
        Subject expSubject = new Subject("test", TypeSubject.EXAM, Semester.FIFTH, new SubjectIdx(0));
        Assertions.assertEquals(expSubject.getTypeSubject(), subject.getTypeSubject());
        Assertions.assertEquals(expSubject.getSemester(), subject.getSemester());
        Assertions.assertEquals(expSubject.getIndex(), subject.getIndex());
        Assertions.assertEquals(expSubject.getTypeSubject(), subject.getTypeSubject());
    }

    @Test
    void removeSubjectIndexWithDifferentName() {
        RecordBook recordBook = new RecordBook();
        recordBook.addSubject("test", Semester.FIFTH, TypeSubject.EXAM);
        recordBook.removeSubject("test");
        recordBook.addSubject("test2", Semester.FIFTH, TypeSubject.EXAM);
        Subject subject = recordBook.getSubject("test2");
        Subject expSubject = new Subject("test2", TypeSubject.EXAM, Semester.FIFTH, new SubjectIdx(0));
        Assertions.assertEquals(expSubject.getTypeSubject(), subject.getTypeSubject());
        Assertions.assertEquals(expSubject.getSemester(), subject.getSemester());
        Assertions.assertEquals(expSubject.getIndex(), subject.getIndex());
        Assertions.assertEquals(expSubject.getTypeSubject(), subject.getTypeSubject());
    }

    @Test
    void removeSubjectWithMarks() {
        RecordBook recordBook = new RecordBook();
        recordBook.addSubject("test", Semester.FIFTH, TypeSubject.EXAM);
        recordBook.addStudent("stud", Semester.FIFTH);
        recordBook.addStudent("stud2", Semester.FIFTH);
        recordBook.setStudentMark("test", "stud", Mark.FIVE);
        recordBook.setStudentMark("test", "stud2", Mark.TWO);
        recordBook.removeSubject("test");
        recordBook.addSubject("test", Semester.FIFTH, TypeSubject.EXAM);
        Assertions.assertEquals(Mark.UNDEFINED, recordBook.getStudentMark("stud", "test"));
        Assertions.assertEquals(Mark.UNDEFINED, recordBook.getStudentMark("stud2", "test"));
    }

    @Test
    void getStudentMarkWithNotSettingMark() {
        RecordBook recordBook = new RecordBook();
        recordBook.addSubject("subj", Semester.FIRST, TypeSubject.EXAM);
        recordBook.addStudent("stud", Semester.FIRST);
        Assertions.assertEquals(Mark.UNDEFINED, recordBook.getStudentMark("stud", "subj"));
    }

    @Test
    void getStudentMarkWithNotSettingDifferentSemester() {
        RecordBook recordBook = new RecordBook();
        recordBook.addSubject("subj", Semester.FIRST, TypeSubject.EXAM);
        recordBook.addStudent("stud", Semester.FIFTH);
        Assertions.assertEquals(Mark.UNDEFINED, recordBook.getStudentMark("stud", "subj"));
    }

    @Test
    void getStudentMarkWithNotSettingDifferentSemesterWithStudent() {
        RecordBook recordBook = new RecordBook();
        recordBook.addSubject("subj", Semester.FIFTH, TypeSubject.EXAM);
        recordBook.addStudent("stud", Semester.FIRST);
        Assertions.assertEquals(Mark.UNDEFINED, recordBook.getStudentMark("stud", "subj"));
    }

    @Test
    void setStudentMarkToExamSub() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("stud", Semester.FIRST);
        recordBook.addSubject("subj", Semester.FIRST, TypeSubject.EXAM);
        Assertions.assertTrue(recordBook.setStudentMark("subj", "stud", Mark.FIVE));
        Assertions.assertEquals(Mark.FIVE, recordBook.getStudentMark("stud", "subj"));
    }

    @Test
    void setStudentMarkToExamTest() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("stud", Semester.FIRST);
        recordBook.addSubject("subj", Semester.FIRST, TypeSubject.TEST);
        Assertions.assertTrue(recordBook.setStudentMark("subj", "stud", Mark.TWO));
        Assertions.assertEquals(Mark.TWO, recordBook.getStudentMark("stud", "subj"));
    }

    @Test
    void setStudentMarkToExamGradedTest() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("stud", Semester.FIRST);
        recordBook.addSubject("subj", Semester.FIRST, TypeSubject.GRADED_TEST);
        Assertions.assertTrue(recordBook.setStudentMark("subj", "stud", Mark.TWO));
        Assertions.assertEquals(Mark.TWO, recordBook.getStudentMark("stud", "subj"));
    }

    @Test
    void setStudentWithNotExistingMark() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("stud", Semester.FIRST);
        recordBook.addSubject("subj", Semester.FIFTH, TypeSubject.GRADED_TEST);
        Assertions.assertTrue(recordBook.setStudentMark("subj", "stud", Mark.TWO));
        Assertions.assertEquals(Mark.TWO, recordBook.getStudentMark("stud", "subj"));
    }

    @Test
    void setStudentWithNotExistingStudent() {
        RecordBook recordBook = new RecordBook();
        recordBook.addSubject("subj", Semester.FIFTH, TypeSubject.GRADED_TEST);
        Assertions.assertFalse(recordBook.setStudentMark("subj", "None", Mark.TWO));
    }

    @Test
    void setStudentWithNotExistingSubject() {
        RecordBook recordBook = new RecordBook();
        recordBook.addStudent("stud", Semester.FIRST);
        Assertions.assertFalse(recordBook.setStudentMark("none", "stud", Mark.TWO));
    }
}