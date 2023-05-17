package GUI.Dialog.AdminDialog.Admin;

import Entity.*;
import EntityList.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAdminDialog extends JDialog implements ActionListener {
    private JTextField nameField, emailField;
    private JPasswordField passwordField, retypePasswordField;

    private AdminList a = new AdminList(); // To use addAdmin & getAdminList

    public AddAdminDialog(JFrame parent) {
        super(parent, "Add Admin", true);

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

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 90, 150, 20);
        contentPane.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBounds(200, 90, 200, 20);
        contentPane.add(passwordField);

        JLabel retypePasswordLabel = new JLabel("Re-type Password:");
        retypePasswordLabel.setBounds(30, 120, 150, 20);
        contentPane.add(retypePasswordLabel);

        retypePasswordField = new JPasswordField(20);
        retypePasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        retypePasswordField.setBounds(200, 120, 200, 20);
        contentPane.add(retypePasswordField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.white);
        buttonPanel.setBounds(30, 160, 370, 40);

        JButton addButton = new JButton("Add Admin");
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
        setSize(450, 240);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Admin")) {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String retypePassword = new String(retypePasswordField.getPassword());

            if (!password.equals(retypePassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Admin admin = new Admin(AdminList.getAdminList().size() + 1, name, email, password);
            a.addAdmin(admin);
            AdminList.saveAdminListToFile(AdminList.getAdminList());

            JOptionPane.showMessageDialog(this, "Admin added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else if (e.getActionCommand().equals("Cancel")) {
            dispose();
        }
    }
}