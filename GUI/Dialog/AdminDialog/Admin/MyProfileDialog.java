package GUI.Dialog.AdminDialog.Admin;

import Entity.Admin;
import GUI.AdminGUI;
import GUI.Common.CustomCursor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyProfileDialog extends JDialog implements ActionListener {
    private Admin currentAdmin;
    private JButton editButton, closeButton;
    private JLabel nameValue, emailValue;

    public MyProfileDialog(AdminGUI parent, Admin admin) {
        super(parent, "My Profile", true);
        this.currentAdmin = admin;

        setSize(500, 350);
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

        JLabel idValue = new JLabel(String.valueOf(currentAdmin.getID()));
        idValue.setBounds(150, 30, 100, 30);
        idValue.setFont(valueFont);
        add(idValue);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 70, 100, 30);
        nameLabel.setFont(labelFont);
        add(nameLabel);

        nameValue = new JLabel(currentAdmin.getName());
        nameValue.setBounds(150, 70, 300, 30);
        nameValue.setFont(valueFont);
        add(nameValue);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 110, 100, 30);
        emailLabel.setFont(labelFont);
        add(emailLabel);

        emailValue = new JLabel(currentAdmin.getEmail());
        emailValue.setBounds(150, 110, 300, 30);
        emailValue.setFont(valueFont);
        add(emailValue);

        closeButton = new JButton("Close");
        closeButton.setBounds(260, 250, 100, 30);
        closeButton.addActionListener(this);
        closeButton.setFont(labelFont);
        closeButton.setBackground(new Color(59, 89, 152));
        closeButton.setForeground(Color.WHITE);
        add(closeButton);

        editButton = new JButton("Edit");
        editButton.setBounds(140, 250, 100, 30);
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
        EditProfileDialog editProfileDialog = new EditProfileDialog(this, currentAdmin);
        editProfileDialog.setVisible(true);
    }

    // Only for the display profile
    public void updateProfile(String name, String email) {
        nameValue.setText(name);
        emailValue.setText(email);
    }
}
