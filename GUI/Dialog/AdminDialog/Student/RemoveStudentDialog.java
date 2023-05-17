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

public class RemoveStudentDialog extends JDialog implements ActionListener {
    private StudentData studentData;
    private JTable studentTable;
    private JScrollPane studentScrollPane;
    private JButton searchButton, removeButton, cancelButton;
    private DefaultTableModel tableModel;

    public RemoveStudentDialog(StudentData studentData) {
        super(studentData, "Remove Student", true);
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

        studentScrollPane = new JScrollPane(studentTable);
        studentScrollPane.setBounds(130, 10, 820, 500);
        studentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        studentScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(studentScrollPane);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBounds(-20, 520, 800, 45);
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
        setLocationRelativeTo(studentData);
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
        CustomCursor.setHandCursor(searchButton);
        CustomCursor.setHandCursor(removeButton);
        CustomCursor.setHandCursor(cancelButton);
    }

    private void searchStudentDialog() {
        SearchStudent.showDialog(this, studentTable, tableModel);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == removeButton) {
            int selectedRow = studentTable.getSelectedRow();

            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to remove the selected student?",
                        "Confirm Remove Student", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    int id = (int) studentTable.getValueAt(selectedRow, 0);
                    Student student = StudentList.findStudentById(id);
                    StudentList.getStudentList().remove(student);
                    StudentList.saveStudentListToFile(StudentList.getStudentList());
                    updateStudentTable();
                    JOptionPane.showMessageDialog(this, "Student removed successfully.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No Student is selected");
            }
        } else if (e.getSource() == searchButton) {
            searchStudentDialog();
        } else if (source == cancelButton) {
            studentData.setVisible(true);
            dispose();
        }
    }
}