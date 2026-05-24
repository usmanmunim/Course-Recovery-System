package crs.models;

public class Student {
    public String studentId;
    public String name;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    @Override
    public String toString() {
        return studentId + " - " + name;
    }
}
