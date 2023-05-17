package GUI.Dialog.AdminDialog.Teacher;

import Entity.*;
import EntityList.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class AddTeacherDialog extends JDialog implements ActionListener {
    private JTextField nameField, emailField, departmentField, salaryField;
    private JPasswordField passwordField, retypePasswordField;

    private TeacherList em = new TeacherList(); // To use addTeacher & getTeacherList

    public AddTeacherDialog(JFrame parent) {
        super(parent, "Add Teacher", true);

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

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 60, 150, 20);
        contentPane.add(emailLabel);

        emailField = new JTextField(20);
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailField.setBounds(200, 60, 200, 20);
        contentPane.add(emailField);

        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setBounds(30, 90, 150, 20);
        contentPane.add(departmentLabel);

        departmentField = new JTextField(20);
        departmentField.setFont(new Font("Arial", Font.PLAIN, 14));
        departmentField.setBounds(200, 90, 200, 20);
        contentPane.add(departmentField);

        JLabel salaryLabel = new JLabel("Salary:");
        salaryLabel.setBounds(30, 120, 150, 20);
        contentPane.add(salaryLabel);

        salaryField = new JTextField(20);
        salaryField.setFont(new Font("Arial", Font.PLAIN, 14));
        salaryField.setBounds(200, 120, 200, 20);
        contentPane.add(salaryField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 150, 150, 20);
        contentPane.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBounds(200, 150, 200, 20);
        contentPane.add(passwordField);

        JLabel retypePasswordLabel = new JLabel("Re-type Password:");
        retypePasswordLabel.setBounds(30, 180, 150, 20);
        contentPane.add(retypePasswordLabel);

        retypePasswordField = new JPasswordField(20);
        retypePasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        retypePasswordField.setBounds(200, 180, 200, 20);
        contentPane.add(retypePasswordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.white);
        buttonPanel.setBounds(30, 240, 370, 40);

        JButton addButton = new JButton("Add Teacher");
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
        setSize(450, 340);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Teacher")) {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String department = departmentField.getText().trim();
            double salary;
            String password = new String(passwordField.getPassword());
            String retypePassword = new String(retypePasswordField.getPassword());

            try {
                salary = Double.parseDouble(salaryField.getText().trim());
                if (salary < 0) {
                    throw new IllegalArgumentException("Salary cannot be negative");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid salary format", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return; // stop further execution
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
                return; // stop further execution
            }

            if (!password.equals(retypePassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int uniqueId = em.generateUniqueId();
            Teacher newTeacher = new Teacher(uniqueId, name, email, department, salary, password);
            em.addTeacher(newTeacher);
            TeacherList.saveTeacherListToFile(TeacherList.getTeacherList());

            File file = new File("Data/Teacher/" + name + "_course_selection.txt");
            try {
                file.createNewFile();
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(this, "An error occurred while creating the file.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            JOptionPane.showMessageDialog(this, "Teacher added successfully", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else if (e.getActionCommand().equals("Cancel")) {
            dispose();
        }
    }
}