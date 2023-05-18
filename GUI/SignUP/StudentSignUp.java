package GUI.SignUP;

import Entity.*;
import EntityList.*;
import GUI.Common.*;
import GUI.Login.StudentLogin;
import GUI.Payment.PaymentGUI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentSignUp extends JFrame implements ActionListener {

    private JLabel usernameLabel, emailLabel, majorLabel;
    private JTextField usernameField, emailField, majorField;
    private JLabel passwordLabel, retypePasswordLabel;
    private JPasswordField passwordField, retypePasswordField;
    private JButton signUpButton, backButton;

    private StudentList em = new StudentList();

    public StudentSignUp() {
        setTitle("Student Sign Up");
        setSize(1100, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load data
        StudentList.loadStudentListFromFile();

        ImageIcon icon = new ImageIcon(getClass().getResource("/GUI/Pictures/Common/Icon.png"));
        setIconImage(icon.getImage());

        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/GUI/Pictures/StudentSingUP/Background.png"));
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(getWidth(), getHeight(),
                Image.SCALE_SMOOTH);
        backgroundIcon = new ImageIcon(backgroundImage);

        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(null);
        add(backgroundLabel);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setBounds(360, 225, 270, 30);
        usernameField = new JTextField();
        usernameField.setBounds(470, 225, 270, 30);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setBounds(360, 270, 270, 30);
        passwordField = new JPasswordField();
        passwordField.setBounds(470, 270, 270, 30);

        retypePasswordLabel = new JLabel("Re-type Pass:");
        retypePasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        retypePasswordLabel.setBounds(360, 315, 270, 30);
        retypePasswordLabel.setForeground(Color.BLACK);
        retypePasswordField = new JPasswordField();
        retypePasswordField.setBounds(470, 315, 270, 30);

        emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        emailLabel.setForeground(Color.BLACK);
        emailLabel.setBounds(360, 360, 270, 30);
        emailField = new JTextField();
        emailField.setBounds(470, 360, 270, 30);

        majorLabel = new JLabel("Major:");
        majorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        majorLabel.setForeground(Color.BLACK);
        majorLabel.setBounds(360, 405, 270, 30);
        majorField = new JTextField();
        majorField.setBounds(470, 405, 270, 30);

        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(this);
        signUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        signUpButton.setForeground(Color.BLACK);
        signUpButton.setBounds(490, 450, 100, 35);

        backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setForeground(Color.BLACK);
        backButton.setBounds(600, 450, 100, 35);

        backgroundLabel.add(usernameLabel);
        backgroundLabel.add(usernameField);
        backgroundLabel.add(passwordLabel);
        backgroundLabel.add(passwordField);
        backgroundLabel.add(retypePasswordLabel);
        backgroundLabel.add(retypePasswordField);
        backgroundLabel.add(emailLabel);
        backgroundLabel.add(emailField);
        backgroundLabel.add(majorLabel);
        backgroundLabel.add(majorField);
        backgroundLabel.add(signUpButton);
        backgroundLabel.add(backButton);

        add(backgroundLabel);

        setCursorIcon();
        setVisible(true);
    }

    private void setCursorIcon() {
        CustomCursor.setCustomCursor(this);
        CustomCursor.setHandCursor(usernameField);
        CustomCursor.setHandCursor(passwordField);
        CustomCursor.setHandCursor(retypePasswordField);
        CustomCursor.setHandCursor(emailField);
        CustomCursor.setHandCursor(majorField);
        CustomCursor.setHandCursor(signUpButton);
        CustomCursor.setHandCursor(backButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signUpButton) {
            String name = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String major = majorField.getText().trim();
            String password = new String(passwordField.getPassword());
            String retypePassword = new String(retypePasswordField.getPassword());

            if (name.isEmpty() || password.isEmpty() || retypePassword.isEmpty()
                    || email.isEmpty() || major.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(retypePassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int uniqueId = em.generateUniqueId();
            Student newStudent = new Student(uniqueId, name, email, major, password);
            
            // Open the payment GUI
            PaymentGUI paymentGUI = new PaymentGUI(this, newStudent);
            if (!paymentGUI.isCancelled()) {
                em.addStudent(newStudent);
                StudentList.saveStudentListToFile(StudentList.getStudentList());
                JOptionPane.showMessageDialog(this, "Student added successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new StudentLogin();
            } else {
                JOptionPane.showMessageDialog(this, "Payment was unsuccessful!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == backButton) {
            dispose();
            new StudentLogin();
        }
    }
}