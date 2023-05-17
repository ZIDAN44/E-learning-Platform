package GUI.Dialog.AdminDialog.Student;

import Entity.Student;
import EntityList.StudentList;
import GUI.Common.CustomCursor;
import GUI.DataGUI.StudentData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class EditStudentDialog extends JDialog implements ActionListener {
    private StudentData studentData;
    private JScrollPane studentScrollPane;
    private JTextField nameField, majorField, emailField;
    private JButton searchButton, saveButton, cancelButton;
    private JTable studentTable;
    private DefaultTableModel tableModel;

    public EditStudentDialog(StudentData studentData) {
        super(studentData, "Edit Student", true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.studentData = studentData;

        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.white);

        JLabel studentLabel = new JLabel("Select Student:");
        studentLabel.setBounds(10, 10, 120, 25);
        contentPane.add(studentLabel);

        String[] columnNames = { "ID", "Name", "Email", "Major", "Create Date", "Expiry Date", "Status" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(40);
        studentTable.setFont(new Font("Arial", Font.PLAIN, 14));
        updateStudentTable();
        setColumnWidths();

        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                actionPerformed(new ActionEvent(studentTable, ActionEvent.ACTION_PERFORMED, "tableSelection"));
            }
        });

        studentScrollPane = new JScrollPane(studentTable);
        studentScrollPane.setBounds(130, 10, 820, 500);
        studentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        studentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(studentScrollPane);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 520, 120, 25);
        contentPane.add(nameLabel);

        nameField = new JTextField(20);
        nameField.setBounds(130, 520, 200, 25);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPane.add(nameField);

        JLabel majorLabel = new JLabel("Major:");
        majorLabel.setBounds(10, 560, 120, 25);
        contentPane.add(majorLabel);

        majorField = new JTextField(20);
        majorField.setBounds(130, 560, 200, 25);
        majorField.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPane.add(majorField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 600, 120, 25);
        contentPane.add(emailLabel);

        emailField = new JTextField(20);
        emailField.setBounds(130, 600, 200, 25);
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        contentPane.add(emailField);

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
        setLocationRelativeTo(studentData);

        // Disable fields until a student is selected
        nameField.setEnabled(false);
        majorField.setEnabled(false);
        emailField.setEnabled(false);
        saveButton.setEnabled(false);
    }

    private void setColumnWidths() {
        TableColumnModel columnModel = studentTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(250);
        columnModel.getColumn(3).setPreferredWidth(150);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(60);
    }

    private void updateStudentTable() {
        tableModel.setRowCount(0);
        for (Student student : StudentList.getStudentList()) {
            Object[] rowData = { student.getID(), student.getName(), student.getEmail(),
                    student.getMajor(), student.getCreateDate(), student.getExpiryDate(), getStatus(student) };
            tableModel.addRow(rowData);
        }
    }

    private String getStatus(Student student) {
        LocalDate currentDate = LocalDate.now();
        LocalDate createDate = student.getCreateDate();
        LocalDate expiryDate = student.getExpiryDate();

        if (currentDate.isBefore(createDate) || currentDate.isAfter(expiryDate)) {
            return "Inactive";
        } else {
            return "Active";
        }
    }

    private void setCursorIcon() {
        CustomCursor.setCustomCursor(this);
        CustomCursor.setHandCursor(saveButton);
        CustomCursor.setHandCursor(cancelButton);
        CustomCursor.setHandCursor(searchButton);
        CustomCursor.setHandCursor(nameField);
        CustomCursor.setHandCursor(majorField);
        CustomCursor.setHandCursor(emailField);
    }

    private void searchStudentDialog() {
        SearchStudent.showDialog(this, studentTable, tableModel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == studentTable) {
            int selectedRowIndex = studentTable.getSelectedRow();

            if (selectedRowIndex != -1) {
                // Enable fields for editing and populate with selected Student's details
                nameField.setEnabled(true);
                majorField.setEnabled(true);
                emailField.setEnabled(true);
                saveButton.setEnabled(true);

                nameField.setText((String) tableModel.getValueAt(selectedRowIndex, 1));
                majorField.setText((String) tableModel.getValueAt(selectedRowIndex, 3));
                emailField.setText((String) tableModel.getValueAt(selectedRowIndex, 2));
            }
        } else if (e.getSource() == saveButton) {
            int selectedRowIndex = studentTable.getSelectedRow();
            if (selectedRowIndex != -1) {
                int id = (int) tableModel.getValueAt(selectedRowIndex, 0);
                Student selectedStudent = StudentList.findStudentById(id);

                selectedStudent.setName(nameField.getText());
                selectedStudent.setMajor(majorField.getText());
                selectedStudent.setEmail(emailField.getText());

                    // Update Student list and close dialog
                    StudentList.saveStudentListToFile(StudentList.getStudentList());
                    updateStudentTable();

                    JOptionPane.showMessageDialog(this,
                            "Student details updated successfully for " + selectedStudent.getName());

                    studentData.setVisible(true);
                    dispose();
            }
        } else if (e.getSource() == searchButton) {
            searchStudentDialog();
        } else if (e.getSource() == cancelButton) {
            studentData.setVisible(true);
            dispose();
        }
    }
}