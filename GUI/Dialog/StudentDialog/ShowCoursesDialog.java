package GUI.Dialog.StudentDialog;

import Entity.*;
import EntityList.*;
import GUI.StudentGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ShowCoursesDialog extends JDialog {
    private Student currentStudent;
    private JTable courseTable;
    private JScrollPane scrollPane;

    public ShowCoursesDialog(StudentGUI parent, Student student) {
        super(parent, "My Courses", true);
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        this.currentStudent = student;

        initComponents();
        addComponents();
    }

    private void initComponents() {
        courseTable = new JTable();
        courseTable.setFillsViewportHeight(true);
        courseTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Course Name", "Teacher Name" }) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        scrollPane = new JScrollPane(courseTable);
    }

    private void addComponents() {
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        List<String> selectedCourseNames = CourseList.loadSelectedCoursesForStudent(currentStudent.getName());
        DefaultTableModel model = (DefaultTableModel) courseTable.getModel();

        for (String courseName : selectedCourseNames) {
            Course course = CourseList.getCoursesByName(courseName);
            if (course != null) {
                String teacherName = findTeacherByCourse(course);
                model.addRow(new Object[] { course.getName(), teacherName });
            }
        }
    }

    private String findTeacherByCourse(Course course) {
        List<Teacher> teachers = TeacherList.getTeacherList();
        for (Teacher teacher : teachers) {
            List<String> selectedTeacherCourses = CourseList.loadSelectedCoursesForTeacher(teacher.getName());
            if (selectedTeacherCourses.contains(course.getName())) {
                return teacher.getName();
            }
        }
        return "N/A";
    }
}