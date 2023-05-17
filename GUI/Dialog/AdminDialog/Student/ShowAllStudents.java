package GUI.Dialog.AdminDialog.Student;

import Entity.Student;
import EntityList.StudentList;
import GUI.DataGUI.StudentData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class ShowAllStudents extends JDialog implements ActionListener {
    private StudentData studentData;
    private JScrollPane scrollPane;
    private JButton searchButton, closeButton;
    private JTable studentTable;
    private DefaultTableModel tableModel;

    public ShowAllStudents(StudentData studentData) {
        super(studentData, "All Students", true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.studentData = studentData;

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(Color.white);

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

        scrollPane = new JScrollPane(studentTable);
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
        columnModel.getColumn(6).setPreferredWidth(50);
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

    private void searchStudentDialog() {
        SearchStudent.showDialog(this, studentTable, tableModel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            searchStudentDialog();
        } else if (e.getActionCommand().equals("Close")) {
            studentData.setVisible(true);
            dispose();
        }
    }
}
