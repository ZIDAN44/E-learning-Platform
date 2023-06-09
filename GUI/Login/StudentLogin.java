package GUI.Login;

import Entity.*;
import EntityList.*;
import GUI.*;
import GUI.Common.*;
import GUI.Payment.*;
import GUI.SignUP.StudentSignUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class StudentLogin extends JFrame implements ActionListener {
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton showPasswordButton;
    private JButton signUpButton, exitButton;

    public StudentLogin() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 800);
        setLocationRelativeTo(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("/GUI/Pictures/Common/Icon.png"));
        setIconImage(icon.getImage());

        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/GUI/Pictures/StudentLogin/Background.png"));
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(getWidth(), getHeight(),
                Image.SCALE_SMOOTH);
        backgroundIcon = new ImageIcon(backgroundImage);

        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(null);
        add(backgroundLabel);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(380, 315, 170, 30);

        textField = new JTextField();
        textField.setBackground(new Color(230, 230, 230));
        textField.setPreferredSize(new Dimension(150, 30));
        textField.setBounds(470, 315, 170, 30);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(380, 360, 170, 30);

        passwordField = new JPasswordField();
        passwordField.setBackground(new Color(230, 230, 230));
        passwordField.setPreferredSize(new Dimension(150, 30));
        passwordField.setBounds(470, 360, 170, 30);

        ImageIcon signUpIcon = new ImageIcon(getClass().getResource("/GUI/Pictures/StudentLogin/Signup.png"));
        Image scaledImage = signUpIcon.getImage().getScaledInstance(235, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        signUpButton = new RoundedCornerButton("", 45);
        signUpButton.setIcon(scaledIcon);
        signUpButton.addActionListener(this);
        signUpButton.setBounds(435, 450, 230, 35);

        ImageIcon loginIcon = new ImageIcon(getClass().getResource("/GUI/Pictures/Common/login.png"));
        loginButton = new JButton(loginIcon);
        loginButton.addActionListener(this);
        loginButton.setPreferredSize(new Dimension(50, 30));
        loginButton.setBounds(515, 407, 70, 30);

        ImageIcon exitIcon = new ImageIcon(getClass().getResource("/GUI/Pictures/Common/Exit.png"));
        exitButton = new JButton(exitIcon);
        exitButton.addActionListener(this);
        exitButton.setPreferredSize(new Dimension(25, 25));
        exitButton.setBounds(1015, 30, 45, 45);
        exitButton.setBackground(Color.decode("#A8251A"));
        backgroundLabel.add(exitButton);

        ImageIcon eyeIcon = new ImageIcon(getClass().getResource("/GUI/Pictures/Common/eye.png"));
        showPasswordButton = new JButton(eyeIcon);
        showPasswordButton.addActionListener(this);
        showPasswordButton.setBounds(650, 362, 25, 25);

        backgroundLabel.add(userLabel);
        backgroundLabel.add(textField);
        backgroundLabel.add(passwordLabel);
        backgroundLabel.add(passwordField);
        backgroundLabel.add(signUpButton);
        backgroundLabel.add(loginButton);
        backgroundLabel.add(showPasswordButton);

        setCursorIcon();
        setVisible(true);
    }

    private void setCursorIcon() {
        CustomCursor.setCustomCursor(this);
        CustomCursor.setHandCursor(showPasswordButton);
        CustomCursor.setHandCursor(textField);
        CustomCursor.setHandCursor(passwordField);
        CustomCursor.setHandCursor(signUpButton);
        CustomCursor.setHandCursor(loginButton);
        CustomCursor.setHandCursor(exitButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = textField.getText();
            String password = new String(passwordField.getPassword());

            java.util.List<Student> students = StudentList.loadStudentListFromFile();
            for (Student student : students) {
                if (student.getName().equals(username) && student.getPassword().equals(password)) {

                    LocalDate currentDate = LocalDate.now();
                    LocalDate createDate = student.getCreateDate();
                    LocalDate expiryDate = student.getExpiryDate();
                    if (currentDate.isBefore(createDate) || currentDate.isAfter(expiryDate)) {
                        JOptionPane.showMessageDialog(this, "Your account is not active! Pay Now!");

                        PaymentGUI paymentGUI = new PaymentGUI(this, student);
                        if (!paymentGUI.isCancelled()) {
                            LocalDate newExpiryDate = currentDate.plusDays(28);
                            student.setExpiryDate(newExpiryDate);
                            StudentList.saveStudentListToFile(StudentList.getStudentList());
                            dispose();
                            new StudentGUI(student);
                        }
                        return;
                    }
                    dispose();
                    new StudentGUI(student);
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "Invalid username or password!");
            textField.setText("");
            passwordField.setText("");
        } else if (e.getSource() == showPasswordButton) {
            if (passwordField.getEchoChar() == '\u2022') {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('\u2022');
            }
        } else if (e.getSource() == exitButton) {
            this.dispose();
            new HomePage();
        } else if (e.getSource() == signUpButton) {
            new StudentSignUp();
            dispose();
        }
    }
}
