package ru.nsu.romanov.recordbook.student;

import ru.nsu.romanov.recordbook.Semester;

/**
 * Class for students.
 * field index is unique for all students.
 * field fullName is unique for all students.
 */
public class Student {
    private final StudentIdx index;
    private String fullName;
    private Semester semester;

    /**
     * Constructor of class.
     *
     * @param index unique index of student.
     * @param fullName unique index of student.
     * @param semester semester of student.
     */
    public Student(StudentIdx index, String fullName, Semester semester) {
        this.fullName = fullName;
        this.semester = semester;
        this.index = index;
    }

    /**
     * Getter semester.
     *
     * @return semester.
     */
    public Semester getSemester() {
        return semester;
    }

    /**
     * Getter for fullName.
     *
     * @return fullName.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Getter for index.
     *
     * @return index of student.
     */
    public StudentIdx getIndex() {
        return index;
    }


    /**
     * Setter for fullName.
     *
     * @param fullName fullName to set.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Setter for semester.
     *
     * @param semester semester to set.
     */
    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    /**
     * Override standard toString.
     *
     * @return String representation of class.
     */
    @Override
    public String toString() {
        return "Student{"
                + "fullName='" + fullName + '\''
                + ", semester=" + semester
                + '}';
    }
}
