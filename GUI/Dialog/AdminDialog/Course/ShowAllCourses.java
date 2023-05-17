package GUI.Dialog.AdminDialog.Course;

import Entity.Course;
import EntityList.CourseList;
import GUI.DataGUI.CourseData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowAllCourses extends JDialog implements ActionListener {
    private CourseData courseData;
    private JScrollPane scrollPane;
    private JButton searchButton, closeButton;
    private JTable courseTable;
    private DefaultTableModel tableModel;

    public ShowAllCourses(CourseData courseData) {
        super(courseData, "All Courses", true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.courseData = courseData;

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(Color.white);

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

        scrollPane = new JScrollPane(courseTable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        contentPane.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.white);

        closeButton = new JButton("Close");
        closeButton.addActionListener(this);
        closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        closeButton.setForeground(Color.white);
        closeButton.setBackground(new Color(128, 0, 0));
        closeButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        buttonPanel.add(closeButton);

        searchButton = new JButton("Search");
        searchButton.setBounds(340, 480, 100, 25);
        searchButton.addActionListener(this);
        searchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        searchButton.setForeground(Color.white);
        searchButton.setBackground(new Color(0, 128, 128));
        searchButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        buttonPanel.add(searchButton);

        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPane);
        pack();
        setSize(1000, 700);
        setResizable(true);
        setLocationRelativeTo(courseData);
    }

    private void setColumnWidths() {
        TableColumnModel columnModel = courseTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(250);
    }

    private void updateCourseTable() {
        tableModel.setRowCount(0);
        for (Course course : CourseList.getCourseList()) {
            Object[] rowData = { course.getID(), course.getName(), course.getDescription() };
            tableModel.addRow(rowData);
        }
    }

    private void searchCourseDialog() {
        SearchCourse.showDialog(this, courseTable, tableModel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            searchCourseDialog();
        } else if (e.getActionCommand().equals("Close")) {
            courseData.setVisible(true);
            dispose();
        }
    }
}
