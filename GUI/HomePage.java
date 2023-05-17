package GUI;

import GUI.Common.*;
import GUI.Login.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomePage extends JFrame implements ActionListener {
    private final JButton adminButton;
    private final JButton teacherButton;
    private final JButton studentButton;

    public HomePage() {
        setTitle("E-Learning Platform");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 800);
        setLocationRelativeTo(null);
        setLayout(null);
		setResizable(false);

        ImageIcon icon = new ImageIcon(getClass().getResource("Pictures/Common/Icon.png"));
        setIconImage(icon.getImage());

        JLabel titleLabel = new JLabel("Welcome to E-Learning Platform!");
        titleLabel.setFont(new Font("Script MT Bold", Font.BOLD, 35));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(40, 40, 600, 40);
        add(titleLabel);

        adminButton = new JButton("Admin Login");
        adminButton.addActionListener(this);
        adminButton.setFont(new Font("Arial", Font.BOLD, 24));
        adminButton.setBackground(new Color(255, 149, 0));
        adminButton.setForeground(Color.WHITE);

        teacherButton = new JButton("Teacher Login");
        teacherButton.addActionListener(this);
        teacherButton.setFont(new Font("Arial", Font.BOLD, 24));
        teacherButton.setBackground(new Color(255, 221, 0));
        teacherButton.setForeground(Color.WHITE);

        studentButton = new JButton("Student Login");
        studentButton.addActionListener(this);
        studentButton.setFont(new Font("Arial", Font.BOLD, 24));
        studentButton.setBackground(new Color(0, 122, 204));
        studentButton.setForeground(Color.WHITE);

        
        teacherButton.setBounds(410, 220, 260, 70);
        studentButton.setBounds(410, 290, 260, 70);
        adminButton.setBounds(410, 360, 260, 70);

        add(adminButton);
        add(teacherButton);
        add(studentButton);

        ImageIcon bgImage = new ImageIcon(getClass().getResource("Pictures/Home/BG.png"));
        JLabel bgLabel = new JLabel(bgImage);
        bgLabel.setBounds(0, 0, getWidth(), getHeight());
        add(bgLabel);

        setCursorIcon();
        setVisible(true);
    }

    private void setCursorIcon() {
        CustomCursor.setCustomCursor(this);
        CustomCursor.setHandCursor(adminButton);
        CustomCursor.setHandCursor(teacherButton);
        CustomCursor.setHandCursor(studentButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == adminButton) {
            dispose();
            new AdminLogin();
        } else if (e.getSource() == teacherButton) {
            dispose();
            new TeacherLogin();
        } else if (e.getSource() == studentButton) {
            dispose();
            new StudentLogin();
        }
    }
}
