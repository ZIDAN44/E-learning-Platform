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
import java.util.List;

public class ShowSelectCoursesDialog extends JDialog {
    private Teacher currentTeacher;
    private JTable courseTable;
    private DefaultTableModel tableModel;

    public ShowSelectCoursesDialog(TeacherGUI parent, Teacher teacher) {
        super(parent, "Show Selected Courses", true);
        this.currentTeacher = teacher;

        initComponents();
        createLayout();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        // Load the full course list
        CourseList.loadCourseListFromFile();

        List<String> selectedCourseNames = CourseList.loadSelectedCoursesForTeacher(currentTeacher.getName());
        List<Course> selectedCourses = new ArrayList<>();
        for (String courseName : selectedCourseNames) {
            Course course = CourseList.getCoursesByName(courseName);
            if (course != null) {
                selectedCourses.add(course);
            }
        }

        String[] columnNames = { "Course ID", "Course Name", "Course Description" };
        Object[][] rowData = new Object[selectedCourses.size()][3];

        for (int i = 0; i < selectedCourses.size(); i++) {
            rowData[i][0] = selectedCourses.get(i).getID();
            rowData[i][1] = selectedCourses.get(i).getName();
            rowData[i][2] = selectedCourses.get(i).getDescription();
        }

        tableModel = new DefaultTableModel(rowData, columnNames);
        courseTable = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        courseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courseTable.setRowSelectionAllowed(true);
    }

    private void createLayout() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(courseTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int selectedIndex = courseTable.getSelectedRow();
                if (selectedIndex != -1) {
                    int confirmation = JOptionPane.showConfirmDialog(ShowSelectCoursesDialog.this,
                            "Are you sure you want to remove the selected course?",
                            "Confirm Remove",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    if (confirmation == JOptionPane.YES_OPTION) {
                        // Remove the selected course from the table model
                        tableModel.removeRow(selectedIndex);

                        // Update the selected courses for the teacher
                        List<Course> selectedCourses = new ArrayList<>();
                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            int courseId = (int) tableModel.getValueAt(i, 0);
                            String courseName = (String) tableModel.getValueAt(i, 1);
                            String courseDescription = (String) tableModel.getValueAt(i, 2);
                            selectedCourses.add(new Course(courseId, courseName, courseDescription));
                        }
                        currentTeacher.setSelectedCourses(selectedCourses);

                        // Update the teacher list and save it to the file
                        List<Teacher> teachers = TeacherList.loadTeacherListFromFile();
                        for (Teacher teacher : teachers) {
                            if (teacher.getID() == currentTeacher.getID()) {
                                teacher.setSelectedCourses(currentTeacher.getSelectedCourses());
                                break;
                            }
                        }
                        TeacherList.saveTeacherListToFile(teachers);

                        // Save the selected courses and teachers to the file
                        CourseList.saveSelectedCoursesForTeacher(selectedCourses, currentTeacher.getName());
                    }
                } else {
                    JOptionPane.showMessageDialog(ShowSelectCoursesDialog.this, "No course is selected!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}