package GUI.Dialog.AdminDialog.Course;

import Entity.*;
import EntityList.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCourseDialog extends JDialog implements ActionListener {
    private JTextField nameField, descriptionField;

    private CourseList em = new CourseList(); // To use addCourse & getCourseList

    public AddCourseDialog(JFrame parent) {
        super(parent, "Add Course", true);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(null);
        contentPane.setBackground(Color.white);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 30, 150, 20);
        contentPane.add(nameLabel);

        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setBounds(200, 30, 200, 20);
        contentPane.add(nameField);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(30, 60, 150, 20);
        contentPane.add(descriptionLabel);

        descriptionField = new JTextField(20);
        descriptionField.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionField.setBounds(200, 60, 200, 20);
        contentPane.add(descriptionField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.white);
        buttonPanel.setBounds(30, 130, 370, 40);

        JButton addButton = new JButton("Add Course");
        addButton.addActionListener(this);
        addButton.setFont(new Font("Arial", Font.PLAIN, 14));
        addButton.setForeground(Color.white);
        addButton.setBackground(new Color(0, 128, 0));
        addButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        buttonPanel.add(addButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.setForeground(Color.white);
        cancelButton.setBackground(new Color(128, 0, 0));
        cancelButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        buttonPanel.add(cancelButton);

        contentPane.add(buttonPanel);

        setContentPane(contentPane);
        setSize(450, 215);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Course")) {
            String name = nameField.getText().trim();
            String description = descriptionField.getText().trim();

            int uniqueId = em.generateUniqueId();
            Course newCourse = new Course(uniqueId, name, description);
            em.addCourse(newCourse);
            CourseList.saveCourseListToFile(CourseList.getCourseList());

            JOptionPane.showMessageDialog(this, "Course added successfully", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else if (e.getActionCommand().equals("Cancel")) {
            dispose();
        }
    }
}