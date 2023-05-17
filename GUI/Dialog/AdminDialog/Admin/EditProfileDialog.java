package GUI.Dialog.AdminDialog.Admin;

import Entity.Admin;
import EntityList.AdminList;
import GUI.Common.CustomCursor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditProfileDialog extends JDialog implements ActionListener {
    private Admin currentAdmin;
    private MyProfileDialog myProfileDialog;
    private JTextField nameField, emailField;
    private JButton showPasswordButton, saveButton, cancelButton;
    private JPasswordField passwordField;

    public EditProfileDialog(MyProfileDialog myProfileDialog, Admin admin) {
        super(myProfileDialog, "Edit Profile", true);
        this.currentAdmin = admin;
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
        nameField = new JTextField(currentAdmin.getName(), 20);
        emailField = new JTextField(currentAdmin.getEmail(), 20);
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

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 130, 100, 30);
        passwordLabel.setFont(labelFont);
        add(passwordLabel);
        passwordField.setBounds(150, 130, 200, 30);
        passwordField.setFont(fieldFont);
        add(passwordField);

        ImageIcon eyeIcon = new ImageIcon("GUI/Pictures/Common/eye.png");
        showPasswordButton = new JButton(eyeIcon);
        showPasswordButton.addActionListener(this);
        showPasswordButton.setBounds(355, 132, 25, 25);
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
                currentAdmin.setName(nameField.getText());
                currentAdmin.setEmail(emailField.getText());
                // Only update the password if it is not empty
                String newPassword = new String(passwordField.getPassword());
                if (!newPassword.isEmpty()) {
                    currentAdmin.setPassword(newPassword);
                }
                AdminList.saveAdminListToFile(AdminList.getAdminList());
                dispose();
                myProfileDialog.updateProfile(currentAdmin.getName(), currentAdmin.getEmail());
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
