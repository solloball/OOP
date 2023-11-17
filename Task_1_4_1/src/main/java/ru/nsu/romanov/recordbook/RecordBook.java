package ru.nsu.romanov.recordbook;

import ru.nsu.romanov.recordbook.student.Student;
import ru.nsu.romanov.recordbook.student.StudentIdx;
import ru.nsu.romanov.recordbook.subject.Subject;
import ru.nsu.romanov.recordbook.subject.SubjectIdx;

import java.util.*;

/**
 * Record book for FIT NSU.
 */
public class RecordBook {
    private final List<List<Mark>> marks = new ArrayList<>();
    private final Map<String, Student> students = new HashMap<>();
    private final Map<String, Subject> subjects = new HashMap<>();
    private final Queue<StudentIdx> freeStudentIdx = new LinkedList<>();
    private final Queue<SubjectIdx> freeSubjectIdx = new LinkedList<>();

    public void transferNextSemester(String studentFullName) {}

    public void transferSemester(String studentFullName, Semester semester) {}

    public void transferPrevSemester(String studentFullName) {}

    public void removeStudent(String studentFullName) {}

    public void StudentInfo(String studentFullName) {}

    public boolean isPossibleToHaveRedDiploma(String studentFullName) {
        return false;
    }

    public boolean isPossibleToHaveIncreasedScholarship(String studentFullName) {
        return false;
    }

    void addSubject(String subjectName, Semester semester) {}

    void removeSubject(String subjectName) {}

    void addStudentMark(String subjectName, String studentFullName, Mark mark) {}

    void changeStudentMark(String subjectName, String studentFullName, Mark mark) {}
}
