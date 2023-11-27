package ru.nsu.romanov.recordbook.subject;

import ru.nsu.romanov.recordbook.Semester;

/**
 * Class subject.
 * field name must be unique.
 * field subjectIdx must be unique.
 */
public class Subject {
    private String name;
    private TypeSubject typeSubject;
    private Semester semester;
    private final SubjectIdx index;

    /**
     * Constructor of class.
     *
     * @param name name of subject.
     * @param typeSubject typeOfSubject like exam, test or graded_test.
     * @param semester semester in which this subject taught.
     * @param subjectIdx index of this subject.
     */
    public Subject(String name, TypeSubject typeSubject, Semester semester, SubjectIdx subjectIdx) {
        this.name = name;
        this.index = subjectIdx;
        this.semester = semester;
        this.typeSubject = typeSubject;
    }

    /**
     * Setter of semester.
     *
     * @param semester semester to set.
     */
    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    /**
     * Getter of semester.
     *
     * @return semester.
     */
    public Semester getSemester() {
        return semester;
    }

    /**
     * Getter for name.
     *
     * @return name of subject.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for typeSubject
     *
     * @param typeObject type of Subject to set.
     */
    public void setTypeSubject(TypeSubject typeObject) {
        this.typeSubject = typeObject;
    }

    /**
     * Getter for Type Subject.
     *
     * @return Type of subject.
     */
    public TypeSubject getTypeSubject() {
        return typeSubject;
    }

    /**
     * Setter for subject name.
     *
     * @param name new name of subject to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for subject index.
     *
     * @return index of subject.
     */
    public SubjectIdx getIndex() {
        return index;
    }

    /**
     * Override default of toString.
     *
     * @return representation of class in string.
     */
    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", typeObject=" + typeSubject +
                ", semester=" + semester +
                '}';
    }
}
