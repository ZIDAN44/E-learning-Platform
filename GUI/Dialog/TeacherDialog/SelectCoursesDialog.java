package GUI.Dialog.TeacherDialog;

import Entity.Course;
import Entity.Teacher;
import EntityList.CourseList;
import EntityList.TeacherList;
import GUI.TeacherGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectCoursesDialog extends JDialog implements ActionListener {
    private Teacher currentTeacher;
    private JTable courseTable;
    private DefaultTableModel tableModel;
    private JButton saveButton;
    private JButton cancelButton;

    public SelectCoursesDialog(TeacherGUI parent, Teacher teacher) {
        super(parent, "Select Courses", true);
        this.currentTeacher = teacher;

        initComponents();
        createLayout();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        List<Course> allCourses = CourseList.loadCourseListFromFile();
        List<Course> availableCourses = new ArrayList<>();
        Set<String> selectedCourseNames = new HashSet<>();

        // Find the selected courses for other teachers
        List<Teacher> teachers = TeacherList.loadTeacherListFromFile();
        for (Teacher teacher : teachers) {
            if (teacher.getID() != currentTeacher.getID()) {
                selectedCourseNames.addAll(CourseList.loadSelectedCoursesForTeacher(teacher.getName()));
            }
        }

        // Select the available courses
        for (Course course : allCourses) {
            if (!selectedCourseNames.contains(course.getName())) {
                availableCourses.add(course);
            }
        }

        String[] columnNames = { "Course ID", "Course Name", "Course Description" };
        Object[][] rowData = new Object[availableCourses.size()][3];

        for (int i = 0; i < availableCourses.size(); i++) {
            rowData[i][0] = availableCourses.get(i).getID();
            rowData[i][1] = availableCourses.get(i).getName();
            rowData[i][2] = availableCourses.get(i).getDescription();
        }

        tableModel = new DefaultTableModel(rowData, columnNames);
        courseTable = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        courseTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        courseTable.setRowSelectionAllowed(true);

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        saveButton.addActionListener(this);
        cancelButton.addActionListener(this);

        Font tableFont = new Font("Arial", Font.PLAIN, 14);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        Color buttonColor = new Color(45, 139, 206);

        courseTable.setFont(tableFont);
        courseTable.getTableHeader().setFont(tableFont);
        courseTable.setRowHeight(20);
        courseTable.setSelectionBackground(buttonColor);

        saveButton.setFont(buttonFont);
        cancelButton.setFont(buttonFont);
        saveButton.setForeground(Color.WHITE);
        cancelButton.setForeground(Color.WHITE);
        saveButton.setBackground(buttonColor);
        cancelButton.setBackground(buttonColor);
    }

    private void createLayout() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(courseTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {

            int[] selectedRows = courseTable.getSelectedRows();
            if (selectedRows.length == 0) {
                JOptionPane.showMessageDialog(this, "No course selected!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Course> selectedCourses = new ArrayList<>();
            for (int selectedRow : selectedRows) {
                int courseId = (int) courseTable.getValueAt(selectedRow, 0);
                String courseName = (String) courseTable.getValueAt(selectedRow, 1);
                String courseDescription = (String) courseTable.getValueAt(selectedRow, 2);
                selectedCourses.add(new Course(courseId, courseName, courseDescription));
            }

            // Save the selected courses for the current teacher to the file
            CourseList.saveSelectedCoursesForTeacher(selectedCourses, currentTeacher.getName());
            dispose();
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }
}
