package Entity;

public class Teacher extends Person {
    private String department;
    private double salary;
    private java.util.List<Course> selectedCourses;

    public Teacher(int id, String name, String email, String department, double salary, String password) {
        super(id, name, email, password);
        this.department = department;
        this.salary = salary;
        this.selectedCourses = new java.util.ArrayList<>();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public java.util.List<Course> getSelectedCourses() {
        return selectedCourses;
    }

    public void setSelectedCourses(java.util.List<Course> selectedCourses) {
        this.selectedCourses = selectedCourses;
    }

    public String toString() {
        return "ID: " + super.getID() + "\n" +
                "Name: " + super.getName() + "\n" +
                "Email: " + super.getEmail() + "\n" +
                "Department: " + department + "\n" +
                "Salary: " + salary + "\n" +
                "Password: " + super.getPassword();
    }
}
