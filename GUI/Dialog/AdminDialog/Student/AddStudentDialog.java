package GUI.Dialog.AdminDialog.Student;

import Entity.*;
import EntityList.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddStudentDialog extends JDialog implements ActionListener {
    private JTextField nameField, emailField, majorField;
    private JPasswordField passwordField, retypePasswordField;

    private StudentList em = new StudentList(); // To use addStudent & getStudentList

    public AddStudentDialog(JFrame parent) {
        super(parent, "Add Student", true);

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

        JLabel majorLabel = new JLabel("Major:");
        majorLabel.setBounds(30, 90, 150, 20);
        contentPane.add(majorLabel);

        majorField = new JTextField(20);
        majorField.setFont(new Font("Arial", Font.PLAIN, 14));
        majorField.setBounds(200, 90, 200, 20);
        contentPane.add(majorField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 120, 150, 20);
        contentPane.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBounds(200, 120, 200, 20);
        contentPane.add(passwordField);

        JLabel retypePasswordLabel = new JLabel("Re-type Password:");
        retypePasswordLabel.setBounds(30, 150, 150, 20);
        contentPane.add(retypePasswordLabel);

        retypePasswordField = new JPasswordField(20);
        retypePasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        retypePasswordField.setBounds(200, 150, 200, 20);
        contentPane.add(retypePasswordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.white);
        buttonPanel.setBounds(30, 200, 370, 40);

        JButton addButton = new JButton("Add Student");
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
        setSize(450, 315);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Student")) {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String major = majorField.getText().trim();
            String password = new String(passwordField.getPassword());
            String retypePassword = new String(retypePasswordField.getPassword());

            if (!password.equals(retypePassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int uniqueId = em.generateUniqueId();
            Student newStudent = new Student(uniqueId, name, email, major, password);
            em.addStudent(newStudent);
            StudentList.saveStudentListToFile(StudentList.getStudentList());

            JOptionPane.showMessageDialog(this, "Student added successfully", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else if (e.getActionCommand().equals("Cancel")) {
            dispose();
        }
    }
}