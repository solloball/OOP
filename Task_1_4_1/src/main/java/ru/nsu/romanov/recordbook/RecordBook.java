package ru.nsu.romanov.recordbook;

import ru.nsu.romanov.recordbook.student.Student;
import ru.nsu.romanov.recordbook.student.StudentIdx;
import ru.nsu.romanov.recordbook.subject.Subject;
import ru.nsu.romanov.recordbook.subject.SubjectIdx;
import ru.nsu.romanov.recordbook.subject.TypeObject;

import java.rmi.NoSuchObjectException;
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

    public RecordBook() {
        marks.add(new ArrayList<>());
    }

    public Student getStudent(String studentFullName) {
        return students.get(studentFullName);
    }

    public Subject getSubject(String subjectName) {
        return subjects.get(subjectName);
    }

    public boolean addStudent(String studentFullName, Semester semester) {
        if (students.containsKey(studentFullName)) {
            return false;
        }
        StudentIdx studentIndex = (freeStudentIdx.isEmpty()) ?
                new StudentIdx(students.size()) : freeStudentIdx.remove();
        Student student = new Student(studentIndex, studentFullName, semester);
        students.put(studentFullName, student);

        setStudentMarkUnd(student, Semester.FIRST, semester);
        return true;
    }

    public boolean transferSemester(String studentFullName, Semester semester) {
        Student student = getStudent(studentFullName);
        if (student == null) {
            return false;
        }
        if (student.getSemester().ordinal() > semester.ordinal()) {
            setStudentMarkUnd(student, semester, student.getSemester());
        } else if (student.getSemester().ordinal() < semester.ordinal()) {
            setStudentMarkUnd(student, Semester.values()[student.getSemester().ordinal() + 1], semester);
        } else {
            return false;
        }
        student.setSemester(semester);
        return true;
    }

    public boolean removeStudent(String studentFullName) {
        Student student = students.remove(studentFullName);
        if (student == null) {
            return false;
        }
        freeStudentIdx.add(student.getIndex());
        setStudentMarkUnd(student, Semester.FIRST, student.getSemester());
        return true;
    }

    public Mark getStudentMark(String studentName, String subjectName) {
        Student student = getStudent(studentName);
        Subject subject = getSubject(subjectName);
        if (student == null || subject == null) {
            return Mark.UNDEFINED;
        }
        if (marks.get(subject.getIndex().idx()).size() <= student.getIndex().idx()) {
            return Mark.UNDEFINED;
        }
        return marks.get(subject.getIndex().idx()).get(student.getIndex().idx());
    }

    public String StudentInfo(String studentFullName) {
        Student student = getStudent(studentFullName);
        if (student == null) {
            return "No such student!";
        }
        StringBuilder res = new StringBuilder();
        res.append(student);
        res.append("\n");
        List<Subject> list = subList(Semester.FIRST, student.getSemester());
        for (Subject sub : list) {
            Mark mark = (marks.get(sub.getIndex().idx()).size() > student.getIndex().idx())
                    ? marks.get(sub.getIndex().idx()).get(student.getIndex().idx()) : Mark.UNDEFINED;
            res.append(sub);
            res.append(" Mark : ");

            if (mark == Mark.UNDEFINED) {
                res.append("There is no information\n");
                continue;
            }
            if (sub.getTypeObject() == TypeObject.TEST) {
                if (mark == Mark.TWO) {
                    res.append("Academic Debt\n");
                }
                else {
                    res.append("Pass\n");
                }
                continue;
            }
            res.append(mark);
            res.append("\n");
        }
        return res.toString();
    }

    public boolean isPossibleToHaveRedDiploma(String studentFullName) {
        Student student = getStudent(studentFullName);
        if (student == null) {
            return  false;
        }
        List<Subject> list = subList(Semester.FIRST, student.getSemester());
        float sumMarks = 0;
        int count = 0;
        final float minAverageMark = 4.8F;
        for (Subject sub : list) {
            Mark mark = (marks.get(sub.getIndex().idx()).size() > student.getIndex().idx())
                    ? marks.get(sub.getIndex().idx()).get(student.getIndex().idx()) : Mark.UNDEFINED;
            if (sub.getTypeObject() == TypeObject.TEST) {
                if (mark == Mark.TWO) {
                    return false;
                }
                continue;
            }
            switch (mark) {
                case UNDEFINED -> {}
                case TWO, THREE, THREE_RETAKED -> {
                    return false;
                }
                case FOUR_RETAKED , FOUR -> {
                    sumMarks += 4;
                    count++;
                }
                case FIVE_RETAKED, FIVE -> {
                    sumMarks += 5;
                    count++;
                }
            }
        }
        if (count == 0) {
            return true;
        }
        return sumMarks / count >= minAverageMark;
    }

    public boolean isPossibleToHaveIncreasedScholarship(String studentFullName) {
        Student student = getStudent(studentFullName);
        if (student == null) {
            return false;
        }
        if (student.getSemester() == Semester.FIRST || student.getSemester() == Semester.TENTH) {
            return false;
        }
        List<Subject> list = subList(student.getSemester(), student.getSemester());
        for (var sub : list) {
            Mark mark = (marks.get(sub.getIndex().idx()).size() > student.getIndex().idx())
                    ? marks.get(sub.getIndex().idx()).get(student.getIndex().idx()) : Mark.UNDEFINED;
            if (sub.getTypeObject() == TypeObject.TEST && mark == Mark.THREE) {
                continue;
            }
            if (mark == Mark.TWO || mark == Mark.THREE || mark == Mark.THREE_RETAKED
                || mark == Mark.FOUR_RETAKED || mark == Mark.FIVE_RETAKED) {
                return false;
            }
        }
        return true;
    }

    public boolean addSubject(String subjectName, Semester semester, TypeObject typeObject) {
        if (subjects.containsKey(subjectName)) {
            return false;
        }
        SubjectIdx index = (freeSubjectIdx.isEmpty()) ?
                new SubjectIdx(subjects.size()) : freeSubjectIdx.remove();
        subjects.put(subjectName, new Subject(subjectName, typeObject, semester, index));
        marks.add(new ArrayList<>());
        return true;
    }

    public boolean removeSubject(String subjectName) {
        Subject sub = subjects.remove(subjectName);
        if (sub == null) {
            return false;
        }
        freeSubjectIdx.add(sub.getIndex());
        marks.get(sub.getIndex().idx()).clear();
        return true;
    }

    public boolean setStudentMark(String subjectName, String studentFullName, Mark mark) {
        Student student = getStudent(studentFullName);
        Subject subject = getSubject(subjectName);
        if (student == null || subject == null) {
            return false;
        }
        setMark(subject.getIndex(), student.getIndex(), mark);
        return true;
    }

    private List<Subject> subList(Semester minSemester, Semester maxSemester) {
        return subjects.values().stream()
                .filter(t -> t.getSemester().ordinal() >= minSemester.ordinal()
                        && t.getSemester().ordinal() <= maxSemester.ordinal()).toList();
    }

    private void setStudentMarkUnd(Student student, Semester minSemester, Semester maxSemester) {
        List<Subject> subjectToAdd = subList(minSemester, maxSemester);
        subjectToAdd.forEach(sub -> setMark(sub.getIndex(), student.getIndex(), Mark.UNDEFINED));
    }

    private void setMark(SubjectIdx subIdx, StudentIdx studentIdx, Mark mark) {
        assert marks.size() > subIdx.idx(); // ERROR
        if (marks.get(subIdx.idx()).size() <= studentIdx.idx()) {
            int count = studentIdx.idx() - marks.get(subIdx.idx()).size() + 1;
            marks.get(subIdx.idx()).addAll(Collections.nCopies(count, Mark.UNDEFINED));
        }
        marks.get(subIdx.idx()).set(studentIdx.idx(), mark);
    }
}
