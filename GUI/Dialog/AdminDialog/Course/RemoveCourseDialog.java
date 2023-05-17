package GUI.Dialog.AdminDialog.Course;

import Entity.Course;
import EntityList.CourseList;
import GUI.Common.CustomCursor;
import GUI.DataGUI.CourseData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveCourseDialog extends JDialog implements ActionListener {
    private CourseData courseData;
    private JTable courseTable;
    private JScrollPane courseScrollPane;
    private JButton searchButton, removeButton, cancelButton;
    private DefaultTableModel tableModel;

    public RemoveCourseDialog(CourseData courseData) {
        super(courseData, "Remove Course", true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.courseData = courseData;

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.white);

        JLabel courseLabel = new JLabel("Select Course:");
        courseLabel.setBounds(10, 10, 120, 25);
        contentPane.add(courseLabel);

        String[] columnNames = { "ID", "Name", "Description" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        courseTable = new JTable(tableModel);
        courseTable.setRowHeight(40);
        courseTable.setFont(new Font("Arial", Font.PLAIN, 14));
        updateCourseTable();
        setColumnWidths();

        courseScrollPane = new JScrollPane(courseTable);
        courseScrollPane.setBounds(130, 10, 820, 500);
        courseScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        courseScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(courseScrollPane);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBounds(100, 520, 800, 45);
        buttonPanel.setBackground(Color.white);

        removeButton = new JButton("Remove");
        removeButton.addActionListener(this);
        removeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        removeButton.setForeground(Color.white);
        removeButton.setBackground(new Color(128, 0, 0));
        removeButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        buttonPanel.add(removeButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.setForeground(Color.white);
        cancelButton.setBackground(new Color(0, 128, 0));
        cancelButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        buttonPanel.add(cancelButton);

        searchButton = new JButton("Search");
        searchButton.setBounds(340, 480, 100, 25);
        searchButton.addActionListener(this);
        searchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        searchButton.setForeground(Color.white);
        searchButton.setBackground(new Color(0, 128, 128));
        searchButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        buttonPanel.add(searchButton);

        setCursorIcon();
        contentPane.add(buttonPanel);
        setContentPane(contentPane);
        setSize(1000, 600);
        setLocationRelativeTo(courseData);
    }

    private void setColumnWidths() {
        TableColumnModel columnModel = courseTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(250);
        columnModel.getColumn(2).setPreferredWidth(250);
    }

    private void updateCourseTable() {
        tableModel.setRowCount(0);
        for (Course course : CourseList.getCourseList()) {
            Object[] row = { course.getID(), course.getName(), course.getDescription() };
            tableModel.addRow(row);
        }
    }

    private void setCursorIcon() {
        CustomCursor.setCustomCursor(this);
        CustomCursor.setHandCursor(searchButton);
        CustomCursor.setHandCursor(removeButton);
        CustomCursor.setHandCursor(cancelButton);
    }

    private void searchCourseDialog() {
        SearchCourse.showDialog(this, courseTable, tableModel);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == removeButton) {
            int selectedRow = courseTable.getSelectedRow();

            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to remove the selected Course ?",
                        "Confirm Remove Course", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    int id = (int) courseTable.getValueAt(selectedRow, 0);
                    Course course = CourseList.findCourseById(id);
                    CourseList.getCourseList().remove(course);
                    CourseList.saveCourseListToFile(CourseList.getCourseList());
                    updateCourseTable();
                    JOptionPane.showMessageDialog(this, "Course removed successfully.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No Course is selected");
            }
        } else if (e.getSource() == searchButton) {
            searchCourseDialog();
        } else if (source == cancelButton) {
            courseData.setVisible(true);
            dispose();
        }
    }
}