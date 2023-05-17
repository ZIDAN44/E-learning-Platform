package GUI.Dialog.AdminDialog.Teacher;

import Entity.Teacher;
import EntityList.TeacherList;
import GUI.DataGUI.TeacherData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowAllTeachers extends JDialog implements ActionListener {
    private TeacherData teacherData;
    private JScrollPane scrollPane;
    private JButton searchButton, closeButton;
    private JTable teacherTable;
    private DefaultTableModel tableModel;

    public ShowAllTeachers(TeacherData teacherData) {
        super(teacherData, "All Teachers", true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.teacherData = teacherData;

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(Color.white);

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

        scrollPane = new JScrollPane(teacherTable);
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
        setLocationRelativeTo(teacherData);
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

    private void searchTeacherDialog() {
        SearchTeacher.showDialog(this, teacherTable, tableModel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            searchTeacherDialog();
        } else if (e.getActionCommand().equals("Close")) {
            teacherData.setVisible(true);
            dispose();
        }
    }
}
