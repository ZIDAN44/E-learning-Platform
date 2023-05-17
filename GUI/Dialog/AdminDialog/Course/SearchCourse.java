package GUI.Dialog.AdminDialog.Course;

import Entity.Course;
import EntityList.CourseList;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.Dialog.*;

public class SearchCourse {
    public static void showDialog(Component parent, JTable courseTable, DefaultTableModel tableModel) {
        JDialog searchDialog = new JDialog(SwingUtilities.getWindowAncestor(parent), "Search Course by ID",
                ModalityType.APPLICATION_MODAL);
        searchDialog.setLayout(new FlowLayout());
        searchDialog.setSize(300, 100);
        searchDialog.setLocationRelativeTo(parent);

        JLabel searchLabel = new JLabel("Course ID:");
        searchDialog.add(searchLabel);

        JTextField searchField = new JTextField(10);
        searchDialog.add(searchField);

        JButton searchConfirmButton = new JButton("Search");
        searchConfirmButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(searchField.getText());
                Course course = CourseList.findCourseById(id);

                if (course != null) {
                    int rowIndex = -1;
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        if ((int) tableModel.getValueAt(i, 0) == id) {
                            rowIndex = i;
                            break;
                        }
                    }
                    if (rowIndex != -1) {
                        courseTable.setRowSelectionInterval(rowIndex, rowIndex);
                        courseTable.scrollRectToVisible(courseTable.getCellRect(rowIndex, 0, true));
                        searchDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(searchDialog, "No Course found with the specified ID");
                    }

                } else {
                    JOptionPane.showMessageDialog(searchDialog, "No Course found with the specified ID");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(searchDialog, "Please enter a valid number for the ID");
            }
        });
        searchDialog.add(searchConfirmButton);
        searchDialog.setVisible(true);
    }
}
