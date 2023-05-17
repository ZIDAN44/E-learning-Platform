package GUI.Dialog.AdminDialog.Teacher;

import Entity.Teacher;
import EntityList.TeacherList;
import GUI.Common.CustomCursor;
import GUI.DataGUI.TeacherData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditTeacherDialog extends JDialog implements ActionListener {
    private TeacherData teacherData;
    private JScrollPane teacherScrollPane;
    private JTextField nameField, departmentField, emailField, salaryField;
    private JButton searchButton, saveButton, cancelButton;
    private JTable teacherTable;
    private DefaultTableModel tableModel;

    public EditTeacherDialog(TeacherData teacherData) {
        super(teacherData, "Edit Teacher", true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.teacherData = teacherData;

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.white);

        JLabel teacherLabel = new JLabel("Select Teacher:");
        teacherLabel.setBounds(10, 10, 120, 25);
        contentPane.add(teacherLabel);

        String[] columnNames = { "ID", "Name", "Email", "Department", "Salary" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        teacherTable = new JTable(tableModel);
        teacherTable.setRowHeight(40);
        teacherTable.setFont(new Font("Arial", Font.PLAIN, 14));
        updateTeacherTable();
        setColumnWidths();

        teacherTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                actionPerformed(new ActionEvent(teacherTable, ActionEvent.ACTION_PERFORMED, "tableSelection"));
            }
        });

        teacherScrollPane = new JScrollPane(teacherTable);
        teacherScrollPane.setBounds(130, 10, 820, 500);
        teacherScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        teacherScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(teacherScrollPane);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 520, 120, 25);
        contentPane.add(nameLabel);

        nameField = new JTextField(20);
        nameField.setBounds(130, 520, 200, 25);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPane.add(nameField);

        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setBounds(10, 560, 120, 25);
        contentPane.add(departmentLabel);

        departmentField = new JTextField(20);
        departmentField.setBounds(130, 560, 200, 25);
        departmentField.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPane.add(departmentField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 600, 120, 25);
        contentPane.add(emailLabel);

        emailField = new JTextField(20);
        emailField.setBounds(130, 600, 200, 25);
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPane.add(emailField);

        JLabel salaryLabel = new JLabel("Salary:");
        salaryLabel.setBounds(10, 640, 120, 25);
        contentPane.add(salaryLabel);

        salaryField = new JTextField(20);
        salaryField.setBounds(130, 640, 200, 25);
        salaryField.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPane.add(salaryField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBounds(-20, 680, 800, 45);
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
        setLocationRelativeTo(teacherData);

        // Disable fields until a teacher is selected
        nameField.setEnabled(false);
        departmentField.setEnabled(false);
        emailField.setEnabled(false);
        salaryField.setEnabled(false);
        saveButton.setEnabled(false);
    }

    private void setColumnWidths() {
        TableColumnModel columnModel = teacherTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(250);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(100);
    }

    private void updateTeacherTable() {
        tableModel.setRowCount(0);
        for (Teacher teacher : TeacherList.getTeacherList()) {
            Object[] rowData = { teacher.getID(), teacher.getName(), teacher.getEmail(),
                    teacher.getDepartment(), teacher.getSalary() };
            tableModel.addRow(rowData);
        }
    }

    private void setCursorIcon() {
        CustomCursor.setCustomCursor(this);
        CustomCursor.setHandCursor(saveButton);
        CustomCursor.setHandCursor(cancelButton);
        CustomCursor.setHandCursor(searchButton);
        CustomCursor.setHandCursor(nameField);
        CustomCursor.setHandCursor(departmentField);
        CustomCursor.setHandCursor(emailField);
        CustomCursor.setHandCursor(salaryField);
    }

    private void searchTeacherDialog() {
        SearchTeacher.showDialog(this, teacherTable, tableModel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == teacherTable) {
            int selectedRowIndex = teacherTable.getSelectedRow();

            if (selectedRowIndex != -1) {
                // Enable fields for editing and populate with selected Teacher's details
                nameField.setEnabled(true);
                departmentField.setEnabled(true);
                emailField.setEnabled(true);
                salaryField.setEnabled(true);
                saveButton.setEnabled(true);

                nameField.setText((String) tableModel.getValueAt(selectedRowIndex, 1));
                departmentField.setText((String) tableModel.getValueAt(selectedRowIndex, 3));
                emailField.setText((String) tableModel.getValueAt(selectedRowIndex, 2));
                salaryField.setText(String.valueOf((Double) tableModel.getValueAt(selectedRowIndex, 4)));
            }
        } else if (e.getSource() == saveButton) {
            int selectedRowIndex = teacherTable.getSelectedRow();
            if (selectedRowIndex != -1) {
                int id = (int) tableModel.getValueAt(selectedRowIndex, 0);
                Teacher selectedTeacher = TeacherList.findTeacherById(id);

                selectedTeacher.setName(nameField.getText());
                selectedTeacher.setDepartment(departmentField.getText());
                selectedTeacher.setEmail(emailField.getText());

                try {
                    double salary = Double.parseDouble(salaryField.getText());
                    if (salary < 0) {
                        throw new IllegalArgumentException("Salary cannot be negative");
                    }
                    selectedTeacher.setSalary(salary);

                    // Update Teacher list and close dialog
                    TeacherList.saveTeacherListToFile(TeacherList.getTeacherList());
                    updateTeacherTable();

                    JOptionPane.showMessageDialog(this,
                            "Teacher details updated successfully for " + selectedTeacher.getName());

                    teacherData.setVisible(true);
                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number for the salary");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        } else if (e.getSource() == searchButton) {
            searchTeacherDialog();
        } else if (e.getSource() == cancelButton) {
            teacherData.setVisible(true);
            dispose();
        }
    }
}