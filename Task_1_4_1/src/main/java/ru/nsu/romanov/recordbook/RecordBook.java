package ru.nsu.romanov.recordbook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import ru.nsu.romanov.recordbook.student.Student;
import ru.nsu.romanov.recordbook.student.StudentIdx;
import ru.nsu.romanov.recordbook.subject.Subject;
import ru.nsu.romanov.recordbook.subject.SubjectIdx;
import ru.nsu.romanov.recordbook.subject.TypeSubject;

/**
 * Record book for FIT NSU.
 * Name of students and subject must be unique.
 */
public class RecordBook {
    private final List<List<Mark>> marks = new ArrayList<>();
    private final Map<String, Student> students = new HashMap<>();
    private final Map<String, Subject> subjects = new HashMap<>();
    /**
     * Stores free students' indexes.
     * Add to this list after remove student.
     */
    private final Queue<StudentIdx> freeStudentIdx = new LinkedList<>();
    /**
     * Stores free subjects' indexes.
     */
    private final Queue<SubjectIdx> freeSubjectIdx = new LinkedList<>();

    /**
     * Constructor for recordBook.
     * Just made a first element in matrix mark.
     */
    public RecordBook() {
        marks.add(new ArrayList<>());
    }

    /**
     * Getter for student.
     *
     * @param studentFullName name of student.
     * @return student with name studentFullName.
     */
    public Student getStudent(String studentFullName) {
        return students.get(studentFullName);
    }

    /**
     * Getter fr subject.
     *
     * @param subjectName name of subject.
     * @return subject with name subjectName.
     */
    public Subject getSubject(String subjectName) {
        return subjects.get(subjectName);
    }

    /**
     * Method which add new student.
     * index will be taken from list freeIndexes or least free index.
     *
     * @param studentFullName name of student.
     * @param semester semester in which student studies.
     * @return true if student was made, false if there is a student with this name.
     */
    public boolean addStudent(String studentFullName, Semester semester) {
        if (students.containsKey(studentFullName)) {
            return false;
        }
        StudentIdx studentIndex = (freeStudentIdx.isEmpty())
                ? new StudentIdx(students.size()) : freeStudentIdx.remove();
        Student student = new Student(studentIndex, studentFullName, semester);
        students.put(studentFullName, student);

        setStudentMarkUnd(student, Semester.FIRST, semester);
        return true;
    }

    /**
     * Transfer student to Semester semester.
     *
     * @param studentFullName name of student.
     * @param semester semester to transfer.
     * @return true if student was transferred,
     *     false if there is no such student, or student is in this Semester.
     */
    public boolean transferSemester(String studentFullName, Semester semester) {
        Student student = getStudent(studentFullName);
        if (student == null) {
            return false;
        }
        if (student.getSemester().ordinal() > semester.ordinal()) {
            setStudentMarkUnd(student, semester, student.getSemester());
        } else if (student.getSemester().ordinal() < semester.ordinal()) {
            setStudentMarkUnd(student,
                    Semester.values()[student.getSemester().ordinal() + 1], semester);
        } else {
            return false;
        }
        student.setSemester(semester);
        return true;
    }

    /**
     * remove student and all his marks.
     *
     * @param studentFullName name of student.
     * @return true student is removed, false if there is no such student.
     */
    public boolean removeStudent(String studentFullName) {
        Student student = students.remove(studentFullName);
        if (student == null) {
            return false;
        }
        freeStudentIdx.add(student.getIndex());
        setStudentMarkUnd(student, Semester.FIRST, student.getSemester());
        return true;
    }

    /**
     * Get student with name studentName mark for subject with name subjectName.
     *
     * @param studentName name of student.
     * @param subjectName name of subject.
     * @return Mark (Undefined if there is no info about mark),
     *     null if this student or subject not exist.
     */
    public Mark getStudentMark(String studentName, String subjectName) {
        Student student = getStudent(studentName);
        Subject subject = getSubject(subjectName);
        if (student == null || subject == null) {
            return Mark.UNDEFINED;
        }
        int studentIdx = student.getIndex().idx();
        int subjectIdx = subject.getIndex().idx();
        if (marks.get(subjectIdx).size() <= studentIdx) {
            return Mark.UNDEFINED;
        }
        return marks.get(subjectIdx).get(studentIdx);
    }

    /**
     * Write student info into string and return it.
     *
     * @param studentFullName name of student.
     * @return "" if this student not exist, otherwise info.
     */
    public String studentInfo(String studentFullName) {
        Student student = getStudent(studentFullName);
        if (student == null) {
            return "No such student!";
        }
        StringBuilder res = new StringBuilder();
        res.append(student);
        res.append("\n");
        List<Subject> list = subList(Semester.FIRST, student.getSemester());
        int studentIdx = student.getIndex().idx();
        for (Subject sub : list) {
            int subjectIdx = sub.getIndex().idx();
            Mark mark = getMark(subjectIdx, studentIdx);
            res.append(sub);
            res.append(" Mark : ");

            if (mark == Mark.UNDEFINED) {
                res.append("There is no information\n");
                continue;
            }
            if (sub.getTypeSubject() == TypeSubject.TEST) {
                if (mark == Mark.TWO) {
                    res.append("Academic Debt\n");
                } else {
                    res.append("Pass\n");
                }
                continue;
            }
            res.append(mark);
            res.append("\n");
        }
        return res.toString();
    }

    /**
     * Method to find  is possible to have red diploma.
     * if type of subject is Test mark not important expect TWO.
     * if mark is retake so it's not important.
     * if avr of marks is less than 4.8 then false.
     * if there is at least one three or two than false.
     *
     * @param studentFullName name of student.
     * @return true if is possible to have red diploma, otherwise false.
     */
    public boolean isPossibleToHaveRedDiploma(String studentFullName) {
        Student student = getStudent(studentFullName);
        if (student == null) {
            return  false;
        }
        List<Subject> list = subList(Semester.FIRST, student.getSemester());
        final float minAverageMark = 4.8F;
        int studentIdx = student.getIndex().idx();
        if (list.stream().anyMatch(subject -> {
            var mark = getMark(subject.getIndex().idx(), studentIdx);
            if (mark == Mark.TWO) {
                return true;
            }
            return (mark == Mark.THREE || mark == Mark.THREE_RETAKED)
                    && subject.getTypeSubject() != TypeSubject.TEST;
        })) {
            return false;
        }
        var set = Arrays.asList(Mark.FOUR_RETAKED, Mark.FIVE, Mark.FOUR, Mark.FIVE_RETAKED);
        List<Integer> ans = list.stream().filter(subject ->
                set.contains(getMark(subject.getIndex().idx(), studentIdx)))
                .map(subject -> getMark(subject.getIndex().idx(), studentIdx)
                .toInt()).toList();
        if (ans.isEmpty()) {
            return true;
        }
        return (float) ans.stream().mapToInt(Integer::valueOf).sum() / ans.size() >= minAverageMark;
    }

    /**
     * Method which calc is possible to hav increased scholarship.
     * if type of subject is Test mark not important expect TWO.
     * if mark is retake so false.
     * if there is at least one three or two than false.
     *
     * @param studentFullName name of student.
     * @return true if is possible to hav increased scholarship, otherwise false.
     */
    public boolean isPossibleToHaveIncreasedScholarship(String studentFullName) {
        Student student = getStudent(studentFullName);
        if (student == null) {
            return false;
        }
        if (student.getSemester() == Semester.FIRST || student.getSemester() == Semester.TENTH) {
            return false;
        }
        List<Subject> list = subList(student.getSemester(), student.getSemester());
        int studentIdx = student.getIndex().idx();
        var set = Arrays.asList(Mark.FIVE_RETAKED, Mark.FOUR_RETAKED, Mark.THREE_RETAKED, Mark.TWO);
        return list.stream().noneMatch(sub ->
                set.contains(getMark(sub.getIndex().idx(), studentIdx))
                || (getMark(sub.getIndex().idx(), studentIdx) == Mark.THREE
                && sub.getTypeSubject() != TypeSubject.TEST));
    }

    /**
     * Add new subject.
     * index is taken from list free indexes if it's not empty, otherwise is taken the least.
     *
     * @param subjectName name of subject.
     * @param semester semester of subject.
     * @param typeObject type of this subject.
     * @return true if subject is added, false if there is a subject with this name.
     */
    public boolean addSubject(String subjectName, Semester semester, TypeSubject typeObject) {
        if (subjects.containsKey(subjectName)) {
            return false;
        }
        SubjectIdx index = (freeSubjectIdx.isEmpty())
                ? new SubjectIdx(subjects.size()) : freeSubjectIdx.remove();
        subjects.put(subjectName, new Subject(subjectName, typeObject, semester, index));
        marks.add(new ArrayList<>());
        return true;
    }

    /**
     * Remove subject from record book.
     * add this index to free index list.
     * also remove all marks.
     *
     * @param subjectName name of subject.
     * @return true if this subject was removed, false if there is no subject with name subjectName.
     */
    public boolean removeSubject(String subjectName) {
        Subject sub = subjects.remove(subjectName);
        if (sub == null) {
            return false;
        }
        freeSubjectIdx.add(sub.getIndex());
        marks.get(sub.getIndex().idx()).clear();
        return true;
    }

    /**
     * Set student mark for subject subjectName for student studentFullName.
     *
     * @param subjectName name of subject.
     * @param studentFullName name of student.
     * @param mark mark to set.
     * @return true if mark was set, false if there is no subject or student.
     */
    public boolean setStudentMark(String subjectName, String studentFullName, Mark mark) {
        Student student = getStudent(studentFullName);
        Subject subject = getSubject(subjectName);
        if (student == null || subject == null) {
            return false;
        }
        setMark(subject.getIndex(), student.getIndex(), mark);
        return true;
    }

    /**
     * find all subject with semester,
     *     which minSemester <= semester <= maxSemester and return in list.
     *
     * @param minSemester min semester.
     * @param maxSemester max semester.
     * @return list of subject.
     */
    private List<Subject> subList(Semester minSemester, Semester maxSemester) {
        return subjects.values().stream()
                .filter(t -> t.getSemester().ordinal() >= minSemester.ordinal()
                        && t.getSemester().ordinal() <= maxSemester.ordinal()).toList();
    }

    /**
     * Set for student with name student, mark undefined for all subject with semester,
     * which minSemester <= semester <= maxSemester.
     * there is no checking for student, this responsibility is on external method.
     *
     * @param student name of student.
     * @param minSemester min semester.
     * @param maxSemester max semester.
     */
    private void setStudentMarkUnd(Student student, Semester minSemester, Semester maxSemester) {
        List<Subject> subjectToAdd = subList(minSemester, maxSemester);
        subjectToAdd.forEach(sub -> setMark(sub.getIndex(), student.getIndex(), Mark.UNDEFINED));
    }

    /**
     * set for student with studentIdx mark to subject with subIdx.
     * if there is no place to write, this method implements list, with value mark undefined.
     *
     * @param subIdx index of subject.
     * @param studentIdx index of student.
     * @param mark mark to set.
     */
    private void setMark(SubjectIdx subIdx, StudentIdx studentIdx, Mark mark) {
        assert marks.size() > subIdx.idx(); // ERROR
        int subjectIndex = subIdx.idx();
        int studentIndex = studentIdx.idx();
        if (marks.get(subjectIndex).size() <= studentIndex) {
            int count = studentIndex - marks.get(subjectIndex).size() + 1;
            marks.get(subjectIndex).addAll(Collections.nCopies(count, Mark.UNDEFINED));
        }
        marks.get(subjectIndex).set(studentIndex, mark);
    }

    /**
     * Healer method for getting mark.
     * if there is no info about mark, return undefined.
     *
     * @param subIdx index of subject.
     * @param studentIdx index of student.
     * @return Mark.
     */
    private Mark getMark(int subIdx, int studentIdx) {
        return (marks.get(subIdx).size() > studentIdx)
                ? marks.get(subIdx).get(studentIdx)
                : Mark.UNDEFINED;
    }
}