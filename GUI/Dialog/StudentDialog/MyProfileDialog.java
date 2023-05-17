package GUI.Dialog.StudentDialog;

import Entity.Student;
import GUI.StudentGUI;
import GUI.Common.CustomCursor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyProfileDialog extends JDialog implements ActionListener {
    private Student currentStudent;
    private JButton closeButton, editButton;
    private JLabel nameValue, emailValue, majorValue, createDateValue, expiryDateValue;

    public MyProfileDialog(StudentGUI parent, Student student) {
        super(parent, "My Profile", true);
        this.currentStudent = student;

        setSize(500, 370);
        setLayout(null);
        setLocationRelativeTo(parent);
        setModal(true);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        Font valueFont = new Font("Arial", Font.BOLD, 15);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(30, 30, 100, 30);
        idLabel.setFont(labelFont);
        add(idLabel);

        JLabel idValue = new JLabel(String.valueOf(currentStudent.getID()));
        idValue.setBounds(150, 30, 100, 30);
        idValue.setFont(valueFont);
        add(idValue);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 70, 100, 30);
        nameLabel.setFont(labelFont);
        add(nameLabel);

        nameValue = new JLabel(currentStudent.getName());
        nameValue.setBounds(150, 70, 300, 30);
        nameValue.setFont(valueFont);
        add(nameValue);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 110, 100, 30);
        emailLabel.setFont(labelFont);
        add(emailLabel);

        emailValue = new JLabel(currentStudent.getEmail());
        emailValue.setBounds(150, 110, 300, 30);
        emailValue.setFont(valueFont);
        add(emailValue);

        JLabel majorLabel = new JLabel("Major:");
        majorLabel.setBounds(30, 150, 100, 30);
        majorLabel.setFont(labelFont);
        add(majorLabel);

        majorValue = new JLabel(currentStudent.getMajor());
        majorValue.setBounds(150, 150, 300, 30);
        majorValue.setFont(valueFont);
        add(majorValue);

        JLabel createDateLabel = new JLabel("Create Date:");
        createDateLabel.setBounds(30, 190, 100, 30);
        createDateLabel.setFont(labelFont);
        add(createDateLabel);

        createDateValue = new JLabel(String.valueOf(currentStudent.getCreateDate()));
        createDateValue.setBounds(150, 190, 300, 30);
        createDateValue.setFont(valueFont);
        add(createDateValue);

        JLabel expiryDateLabel = new JLabel("Expiry Date:");
        expiryDateLabel.setBounds(30, 230, 100, 30);
        expiryDateLabel.setFont(labelFont);
        add(expiryDateLabel);

        expiryDateValue = new JLabel(String.valueOf(currentStudent.getExpiryDate()));
        expiryDateValue.setBounds(150, 230, 300, 30);
        expiryDateValue.setFont(valueFont);
        add(expiryDateValue);

        closeButton = new JButton("Close");
        closeButton.setBounds(260, 280, 100, 30);
        closeButton.addActionListener(this);
        closeButton.setFont(labelFont);
        closeButton.setBackground(new Color(59, 89, 152));
        closeButton.setForeground(Color.WHITE);
        add(closeButton);

        editButton = new JButton("Edit");
        editButton.setBounds(140, 280, 100, 30);
        editButton.addActionListener(this);
        editButton.setFont(labelFont);
        editButton.setBackground(new Color(59, 89, 152));
        editButton.setForeground(Color.WHITE);
        add(editButton);

        setCursorIcon();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeButton) {
            dispose();
        } else if (e.getSource() == editButton) {
            editProfile();
        }
    }

    private void setCursorIcon() {
        CustomCursor.setCustomCursor(this);
        CustomCursor.setHandCursor(closeButton);
        CustomCursor.setHandCursor(editButton);
    }

    private void editProfile() {
        EditProfileDialog editProfileDialog = new EditProfileDialog(this, currentStudent);
        editProfileDialog.setVisible(true);
    }

    // Only for the display profile
    public void updateProfile(String name, String email, String major) {
        nameValue.setText(name);
        emailValue.setText(email);
        majorValue.setText(major);
    }
}
