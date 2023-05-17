package GUI.Dialog.AdminDialog.Student;

import Entity.Student;
import EntityList.StudentList;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.Dialog.*;

public class SearchStudent {
    public static void showDialog(Component parent, JTable studentTable, DefaultTableModel tableModel) {
        JDialog searchDialog = new JDialog(SwingUtilities.getWindowAncestor(parent), "Search Student by ID",
                ModalityType.APPLICATION_MODAL);
        searchDialog.setLayout(new FlowLayout());
        searchDialog.setSize(300, 100);
        searchDialog.setLocationRelativeTo(parent);

        JLabel searchLabel = new JLabel("Student ID:");
        searchDialog.add(searchLabel);

        JTextField searchField = new JTextField(10);
        searchDialog.add(searchField);

        JButton searchConfirmButton = new JButton("Search");
        searchConfirmButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(searchField.getText());
                Student student = StudentList.findStudentById(id);

                if (student != null) {
                    int rowIndex = -1;
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        if ((int) tableModel.getValueAt(i, 0) == id) {
                            rowIndex = i;
                            break;
                        }
                    }
                    if (rowIndex != -1) {
                        studentTable.setRowSelectionInterval(rowIndex, rowIndex);
                        studentTable.scrollRectToVisible(studentTable.getCellRect(rowIndex, 0, true));
                        searchDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(searchDialog, "No Student found with the specified ID");
                    }

                } else {
                    JOptionPane.showMessageDialog(searchDialog, "No Student found with the specified ID");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(searchDialog, "Please enter a valid number for the ID");
            }
        });
        searchDialog.add(searchConfirmButton);
        searchDialog.setVisible(true);
    }
}
