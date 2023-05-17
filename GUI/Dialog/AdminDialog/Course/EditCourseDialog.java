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

public class EditCourseDialog extends JDialog implements ActionListener {
    private CourseData courseData;
    private JScrollPane courseScrollPane;
    private JTextField nameField, descriptionField;
    private JButton searchButton, saveButton, cancelButton;
    private JTable courseTable;
    private DefaultTableModel tableModel;

    public EditCourseDialog(CourseData courseData) {
        super(courseData, "Edit Course", true);
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

        courseTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                actionPerformed(new ActionEvent(courseTable, ActionEvent.ACTION_PERFORMED, "tableSelection"));
            }
        });

        courseScrollPane = new JScrollPane(courseTable);
        courseScrollPane.setBounds(130, 10, 820, 500);
        courseScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        courseScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(courseScrollPane);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 550, 120, 25);
        contentPane.add(nameLabel);

        nameField = new JTextField(20);
        nameField.setBounds(130, 550, 200, 25);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPane.add(nameField);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(10, 600, 120, 25);
        contentPane.add(descriptionLabel);

        descriptionField = new JTextField(20);
        descriptionField.setBounds(130, 600, 200, 25);
        descriptionField.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPane.add(descriptionField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBounds(100, 660, 800, 45);
        buttonPanel.setBackground(Color.white);
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        saveButton.setFont(new Font("Arial", Font.PLAIN, 14));
        saveButton.setForeground(Color.white);
        saveButton.setBackground(new Color(0, 128, 0));
        saveButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        buttonPanel.add(saveButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.setForeground(Color.white);
        cancelButton.setBackground(new Color(128, 0, 0));
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
        setSize(1000, 760);
        setLocationRelativeTo(courseData);

        // Disable fields until a Course is selected
        nameField.setEnabled(false);
        descriptionField.setEnabled(false);
        saveButton.setEnabled(false);
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

    private void setCursorIcon() {
        CustomCursor.setCustomCursor(this);
        CustomCursor.setHandCursor(saveButton);
        CustomCursor.setHandCursor(cancelButton);
        CustomCursor.setHandCursor(searchButton);
        CustomCursor.setHandCursor(nameField);
        CustomCursor.setHandCursor(descriptionField);
    }

    private void searchCourseDialog() {
        SearchCourse.showDialog(this, courseTable, tableModel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == courseTable) {
            int selectedRowIndex = courseTable.getSelectedRow();

            if (selectedRowIndex != -1) {
                // Enable fields for editing and populate with selected Course's details
                nameField.setEnabled(true);
                descriptionField.setEnabled(true);
                saveButton.setEnabled(true);

                nameField.setText((String) tableModel.getValueAt(selectedRowIndex, 1));
                descriptionField.setText((String) tableModel.getValueAt(selectedRowIndex, 2));
            }
        } else if (e.getSource() == saveButton) {
            int selectedRowIndex = courseTable.getSelectedRow();
            if (selectedRowIndex != -1) {
                int id = (int) tableModel.getValueAt(selectedRowIndex, 0);
                Course selectedCourse = CourseList.findCourseById(id);

                selectedCourse.setName(nameField.getText());
                selectedCourse.setDescription(descriptionField.getText());

                // Update Course list and close dialog
                CourseList.saveCourseListToFile(CourseList.getCourseList());
                updateCourseTable();

                JOptionPane.showMessageDialog(this,
                        "Course details updated successfully for " + selectedCourse.getName());

                courseData.setVisible(true);
                dispose();
            }
        } else if (e.getSource() == searchButton) {
            searchCourseDialog();
        } else if (e.getSource() == cancelButton) {
            courseData.setVisible(true);
            dispose();
        }
    }
}