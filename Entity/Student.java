package Entity;

import java.time.LocalDate;

public class Student extends Person {
    private String major;
    private java.util.List<Course> enrolledCourses;
    private LocalDate createDate;
    private LocalDate expiryDate;

    public Student(int id, String name, String email, String major, String password) {
        super(id, name, email, password);
        this.major = major;
        this.enrolledCourses = new java.util.ArrayList<>();
        this.createDate = LocalDate.now();
        this.expiryDate = this.createDate.plusDays(28);
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public java.util.List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(java.util.List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String toString() {
        return "ID: " + super.getID() + "\n" +
                "Name: " + super.getName() + "\n" +
                "Email: " + super.getEmail() + "\n" +
                "Major: " + major + "\n" +
                "Create Date: " + createDate + "\n" +
                "Expiry Date: " + expiryDate + "\n" +
                "Password: " + super.getPassword();
    }
}
