package GUI.Dialog.TeacherDialog;

import Entity.Teacher;
import EntityList.TeacherList;
import GUI.Common.CustomCursor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditProfileDialog extends JDialog implements ActionListener {
    private Teacher currentTeacher;
    private MyProfileDialog myProfileDialog;
    private JTextField nameField, emailField, departmentField;
    private JButton showPasswordButton, saveButton, cancelButton;
    private JPasswordField passwordField;

    public EditProfileDialog(MyProfileDialog myProfileDialog, Teacher teacher) {
        super(myProfileDialog, "Edit Profile", true);
        this.currentTeacher = teacher;
        this.myProfileDialog = myProfileDialog;

        initComponents();
        addListeners();
        createLayout();
        setCursorIcon();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(410, 350);
        setLocationRelativeTo(myProfileDialog);
    }

    private void initComponents() {
        nameField = new JTextField(currentTeacher.getName(), 20);
        emailField = new JTextField(currentTeacher.getEmail(), 20);
        departmentField = new JTextField(currentTeacher.getDepartment(), 20);
        passwordField = new JPasswordField("", 20);

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        saveButton.setBackground(new Color(89, 218, 89));
        cancelButton.setBackground(new Color(218, 89, 89));
    }

    private void addListeners() {
        saveButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }

    private void createLayout() {
        setLayout(null);

        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        Font fieldFont = new Font("Arial", Font.BOLD, 15);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 100, 30);
        nameLabel.setFont(labelFont);
        add(nameLabel);
        nameField.setBounds(150, 50, 200, 30);
        nameField.setFont(fieldFont);
        add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 90, 100, 30);
        emailLabel.setFont(labelFont);
        add(emailLabel);
        emailField.setBounds(150, 90, 200, 30);
        emailField.setFont(fieldFont);
        add(emailField);

        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setBounds(50, 130, 100, 30);
        departmentLabel.setFont(labelFont);
        add(departmentLabel);
        departmentField.setBounds(150, 130, 200, 30);
        departmentField.setFont(fieldFont);
        add(departmentField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 170, 100, 30);
        passwordLabel.setFont(labelFont);
        add(passwordLabel);
        passwordField.setBounds(150, 170, 200, 30);
        passwordField.setFont(fieldFont);
        add(passwordField);

        ImageIcon eyeIcon = new ImageIcon("GUI/Pictures/Common/eye.png");
        showPasswordButton = new JButton(eyeIcon);
        showPasswordButton.addActionListener(this);
        showPasswordButton.setBounds(355, 172, 25, 25);
        add(showPasswordButton);

        saveButton.setBounds(180, 230, 80, 30);
        add(saveButton);
        cancelButton.setBounds(270, 230, 80, 30);
        add(cancelButton);
    }

    private void setCursorIcon() {
        CustomCursor.setCustomCursor(this);
        CustomCursor.setHandCursor(showPasswordButton);
        CustomCursor.setHandCursor(saveButton);
        CustomCursor.setHandCursor(cancelButton);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to save?", "Confirm Save",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                currentTeacher.setName(nameField.getText());
                currentTeacher.setEmail(emailField.getText());
                currentTeacher.setDepartment(departmentField.getText());
                // Update the password if its not empty
                String newPassword = new String(passwordField.getPassword());
                if (!newPassword.isEmpty()) {
                    currentTeacher.setPassword(newPassword);
                }
                TeacherList.saveTeacherListToFile(TeacherList.getTeacherList());
                myProfileDialog.updateProfile(currentTeacher.getName(), currentTeacher.getEmail(),
                        currentTeacher.getDepartment());
            }
        } else if (e.getSource() == showPasswordButton) {
            if (passwordField.getEchoChar() == '\u2022') {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('\u2022');
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }
}
