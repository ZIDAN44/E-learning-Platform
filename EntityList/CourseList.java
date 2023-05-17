package EntityList;

import Entity.*;
import Interface.IUniqueIdGenerator;

import java.util.*;
import java.io.*;

public class CourseList implements IUniqueIdGenerator {
    private static final String FILE_PATH = "Data/course_list.txt";
    private static List<Course> courses = new ArrayList<>();
    private static FileHandler courseFileHandler = new FileHandler(FILE_PATH);

    public CourseList() {
    }

    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public static Course findCourseById(int id) {
        for (Course course : courses) {
            if (course.getID() == id) {
                return course;
            }
        }
        return null;
    }

    public static List<Course> getCourseList() {
        return courses;
    }

    public static void saveCourseListToFile(List<Course> courses) {
        courseFileHandler.saveListToFile(courses);
    }

    public static List<Course> loadCourseListFromFile() {
        List<String[]> attributesList = courseFileHandler.loadListFromFile();
        courses.clear();
        for (String[] attributes : attributesList) {
            int id = Integer.parseInt(attributes[0]);
            String name = attributes[1];
            String description = attributes[2];
            Course course = new Course(id, name, description);
            courses.add(course);
        }
        return courses;
    }

    private static void saveSelectedCoursesToFile(List<Course> courses, String filePath, String name) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Course course : courses) {
                writer.write(name + "," + course.getName());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> loadSelectedCoursesFromFile(String filePath, String name) {
        List<String> selectedCourseNames = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(name)) {
                    selectedCourseNames.add(parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return selectedCourseNames;
    }

    public static void saveSelectedCoursesForTeacher(List<Course> courses, String teacherName) {
        String filePath = "Data/Teacher/" + teacherName + "_course_selection.txt";
        saveSelectedCoursesToFile(courses, filePath, teacherName);
    }

    public static List<String> loadSelectedCoursesForTeacher(String teacherName) {
        String filePath = "Data/Teacher/" + teacherName + "_course_selection.txt";
        return loadSelectedCoursesFromFile(filePath, teacherName);
    }

    public static void saveSelectedCoursesForStudent(List<Course> courses, String studentName) {
        String filePath = "Data/Student/" + studentName + "_course_selection.txt";
        saveSelectedCoursesToFile(courses, filePath, studentName);
    }

    public static List<String> loadSelectedCoursesForStudent(String studentName) {
        String filePath = "Data/Student/" + studentName + "_course_selection.txt";
        return loadSelectedCoursesFromFile(filePath, studentName);
    }

    public static Course getCoursesByName(String courseName) {
        for (Course course : courses) {
            if (course.getName().equals(courseName)) {
                return course;
            }
        }
        return null;
    }

    public int generateUniqueId() {
        int id = 1;
        while (findCourseById(id) != null) {
            id++;
        }
        return id;
    }
}
