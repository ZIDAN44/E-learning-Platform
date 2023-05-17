package GUI.Dialog.StudentDialog;

import Entity.Course;
import Entity.Student;
import EntityList.*;
import GUI.StudentGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SelectCourseDialog extends JDialog implements ActionListener {
    private Student currentStudent;
    private JTable courseTable;
    private JButton saveButton;
    private JButton cancelButton;

    public SelectCourseDialog(StudentGUI parent, Student student) {
        super(parent, "Select Courses", true);
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        this.currentStudent = student;

        initComponents();
        addComponents();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        courseTable = new JTable();
        courseTable.setFillsViewportHeight(true);
        courseTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Course Name", "Teacher Name" }) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

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

    private void addComponents() {
        JScrollPane scrollPane = new JScrollPane(courseTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        DefaultTableModel model = (DefaultTableModel) courseTable.getModel();
        List<String> teacherNames = TeacherList.loadTeacherNamesFromFile();
        for (String teacherName : teacherNames) {
            List<String> teacherSelectedCourseNames = CourseList.loadSelectedCoursesForTeacher(teacherName);
            for (String courseName : teacherSelectedCourseNames) {
                model.addRow(new Object[] { courseName, teacherName });
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            saveAction();
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }

    private void saveAction() {
        int[] selectedRows = courseTable.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "No course selected!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        List<Course> selectedCourses = new ArrayList<>();
        for (int row : selectedRows) {
            String courseName = (String) courseTable.getValueAt(row, 0);
            Course course = CourseList.getCoursesByName(courseName);
            if (course != null) {
                selectedCourses.add(course);
            }
        }
        currentStudent.setEnrolledCourses(selectedCourses);
        CourseList.saveSelectedCoursesForStudent(selectedCourses, currentStudent.getName());

        dispose();
    }
}
