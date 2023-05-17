package EntityList;

import Entity.*;
import Interface.IUniqueIdGenerator;

import java.util.*;

public class TeacherList implements IUniqueIdGenerator {
    private static final String FILE_PATH = "Data/teacher_list.txt";
    private static List<Teacher> teachers = new ArrayList<>();
    private static FileHandler teacherFileHandler = new FileHandler(FILE_PATH);

    public TeacherList() {
    }

    public void addTeacher(Teacher teacher) {
        if (!teachers.contains(teacher)) {
            teachers.add(teacher);
            saveTeacherListToFile(teachers);
        }
    }

    public void editTeacher(Teacher teacher) {
        Teacher existingTeacher = findTeacherById(teacher.getID());
        if (existingTeacher != null) {
            teachers.set(teachers.indexOf(existingTeacher), teacher);
            saveTeacherListToFile(teachers);
        }
    }

    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
        saveTeacherListToFile(teachers);
    }

    public static Teacher findTeacherById(int id) {
        for (Teacher teacher : teachers) {
            if (teacher.getID() == id) {
                return teacher;
            }
        }
        return null;
    }

    public void printTeachers() {
        System.out.println("List of teachers:");
        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
    }

    public static List<Teacher> getTeacherList() {
        return teachers;
    }

    public static List<String> loadTeacherNamesFromFile() {
        List<String> teacherNames = new ArrayList<>();
        List<Teacher> teacherList = loadTeacherListFromFile();
        for (Teacher teacher : teacherList) {
            teacherNames.add(teacher.getName());
        }
        return teacherNames;
    }

    public static void saveTeacherListToFile(List<Teacher> teachers) {
        teacherFileHandler.saveListToFile(teachers);
    }

    public static List<Teacher> loadTeacherListFromFile() {
        List<String[]> attributesList = teacherFileHandler.loadListFromFile();
        teachers.clear();
        for (String[] attributes : attributesList) {
            int id = Integer.parseInt(attributes[0]);
            String name = attributes[1];
            String email = attributes[2];
            String department = attributes[3];
            double salary = Double.parseDouble(attributes[4]);
            String password = attributes[5];
            Teacher teacher = new Teacher(id, name, email, department, salary, password);
            teachers.add(teacher);
        }
        return teachers;
    }

    public int generateUniqueId() {
        int id = 1;
        while (findTeacherById(id) != null) {
            id++;
        }
        return id;
    }
}
