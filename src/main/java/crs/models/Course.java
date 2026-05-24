package crs.models;

public class Course {
    public String courseId;
    public String courseName;
    public String failedComponent;

    public Course(String courseId, String courseName, String failedComponent) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.failedComponent = failedComponent;
    }

    @Override
    public String toString() {
        return courseId + " - " + courseName + " (" + failedComponent + ")";
    }
}
