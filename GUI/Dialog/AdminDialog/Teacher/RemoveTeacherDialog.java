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
import java.io.File;

public class RemoveTeacherDialog extends JDialog implements ActionListener {
    private TeacherData teacherData;
    private JTable teacherTable;
    private JScrollPane teacherScrollPane;
    private JButton searchButton, removeButton, cancelButton;
    private DefaultTableModel tableModel;

    public RemoveTeacherDialog(TeacherData teacherData) {
        super(teacherData, "Remove Teacher", true);
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

        teacherScrollPane = new JScrollPane(teacherTable);
        teacherScrollPane.setBounds(130, 10, 820, 500);
        teacherScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        teacherScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(teacherScrollPane);

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
        setLocationRelativeTo(teacherData);
    }

    private void setColumnWidths() {
        TableColumnModel columnModel = teacherTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(250);
        columnModel.getColumn(2).setPreferredWidth(250);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(100);
    }

    private void updateTeacherTable() {
        tableModel.setRowCount(0);
        for (Teacher teacher : TeacherList.getTeacherList()) {
            Object[] row = { teacher.getID(), teacher.getName(), teacher.getEmail(), teacher.getDepartment(),
                    teacher.getSalary() };
            tableModel.addRow(row);
        }
    }

    private void setCursorIcon() {
        CustomCursor.setCustomCursor(this);
        CustomCursor.setHandCursor(searchButton);
        CustomCursor.setHandCursor(removeButton);
        CustomCursor.setHandCursor(cancelButton);
    }

    private void searchTeacherDialog() {
        SearchTeacher.showDialog(this, teacherTable, tableModel);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == removeButton) {
            int selectedRow = teacherTable.getSelectedRow();

            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to remove the selected teacher?",
                        "Confirm Remove Teacher", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    int id = (int) teacherTable.getValueAt(selectedRow, 0);
                    Teacher teacher = TeacherList.findTeacherById(id);
                    TeacherList.getTeacherList().remove(teacher);
                    TeacherList.saveTeacherListToFile(TeacherList.getTeacherList());
                    updateTeacherTable();

                    File file = new File("Data/Teacher/" + teacher.getName() + "_course_selection.txt");
                    if (file.exists()) {
                        file.delete();
                    }

                    JOptionPane.showMessageDialog(this, "Teacher removed successfully.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No Teacher is selected");
            }
        } else if (e.getSource() == searchButton) {
            searchTeacherDialog();
        } else if (source == cancelButton) {
            teacherData.setVisible(true);
            dispose();
        }
    }
}