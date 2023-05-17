package EntityList;

import Entity.*;
import Interface.IUniqueIdGenerator;

import java.time.LocalDate;
import java.util.*;

public class StudentList implements IUniqueIdGenerator {
    private static final String FILE_PATH = "Data/student_list.txt";
    private static List<Student> students = new ArrayList<>();
    private static FileHandler studentFileHandler = new FileHandler(FILE_PATH);

    public StudentList() {
    }

    public void addStudent(Student student) {
        if (!students.contains(student)) {
            students.add(student);
        }
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public static Student findStudentById(int id) {
        for (Student student : students) {
            if (student.getID() == id) {
                return student;
            }
        }
        return null;
    }

    public void printStudents() {
        System.out.println("List of students:");
        for (Student student : students) {
            System.out.println(student);
        }
    }

    public static List<Student> getStudentList() {
        return students;
    }

    public static void saveStudentListToFile(List<Student> students) {
        studentFileHandler.saveListToFile(students);
    }

    public static List<Student> loadStudentListFromFile() {
        List<String[]> attributesList = studentFileHandler.loadListFromFile();
        students.clear();
        for (String[] attributes : attributesList) {
            int id = Integer.parseInt(attributes[0]);
            String name = attributes[1];
            String email = attributes[2];
            String major = attributes[3];
            String createDateString = attributes[4];
            String expiryDateString = attributes[5];
            String password = attributes[6];
            
            LocalDate createDate = LocalDate.parse(createDateString);
            LocalDate expiryDate = LocalDate.parse(expiryDateString);

            Student student = new Student(id, name, email, major, password);
            student.setCreateDate(createDate);
            student.setExpiryDate(expiryDate);

            students.add(student);
        }
        return students;
    }

    public int generateUniqueId() {
        int id = 1;
        while (findStudentById(id) != null) {
            id++;
        }
        return id;
    }
}
