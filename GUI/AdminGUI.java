package GUI;

import Entity.Admin;
import GUI.Common.*;
import GUI.DataGUI.*;
import GUI.Dialog.AdminDialog.Admin.*;
import GUI.Login.AdminLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminGUI extends JFrame implements ActionListener {
    private JButton addAdminButton, teacherDataButton, studentDataButton, courseDataButton, logoutButton;
    private JButton myProfileButton;
    private Admin currentAdmin;

    public AdminGUI(Admin admin) {
        setTitle("ADMIN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 800);
        setLocationRelativeTo(null);

        this.currentAdmin = admin;

        ImageIcon icon = new ImageIcon(getClass().getResource("Pictures/Common/Icon.png"));
        setIconImage(icon.getImage());

        initButtons();
        initButtonColors();
        setCursorIcon();

        ImageIcon bgImage = new ImageIcon(getClass().getResource("Pictures/AdminGUI/A_Background.png"));
        JLabel bgLabel = new JLabel(bgImage);
        bgLabel.setBounds(0, 0, getWidth(), getHeight());
        add(bgLabel);

        setVisible(true);
    }

    private void initButtons() {
        int buttonWidth = 260;
        int buttonHeight = 50;

        myProfileButton = createButton("");
        ImageIcon myProfileIcon = new ImageIcon("GUI/Pictures/Common/My_Profile.png");
        myProfileButton.setIcon(myProfileIcon);
        myProfileButton.setBounds(90, 70, 150, 150);

        addAdminButton = createButton("Add Admin");
        addAdminButton.setBounds(420, 230, buttonWidth, buttonHeight);
        teacherDataButton = createButton("Teacher Data");
        teacherDataButton.setBounds(420, 290, buttonWidth, buttonHeight);
        studentDataButton = createButton("Student Data");
        studentDataButton.setBounds(420, 350, buttonWidth, buttonHeight);
        courseDataButton = createButton("Course Data");
        courseDataButton.setBounds(420, 410, buttonWidth, buttonHeight);

        logoutButton = createButton("");
        ImageIcon logoutIcon = new ImageIcon("GUI/Pictures/Common/exit.png");
        logoutButton.setIcon(logoutIcon);
        logoutButton.setBounds(1010, 15, 50, 50);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setForeground(Color.WHITE);
        button.addActionListener(this);
        add(button);
        return button;
    }

    private void initButtonColors() {
        addAdminButton.setBackground(Color.decode("#431391"));
        teacherDataButton.setBackground(Color.decode("#431391"));
        studentDataButton.setBackground(Color.decode("#431391"));
        courseDataButton.setBackground(Color.decode("#431391"));
        logoutButton.setBackground(Color.decode("#FFA07A"));
    }

    private void setCursorIcon() {
        CustomCursor.setCustomCursor(this);
        CustomCursor.setHandCursor(myProfileButton);
        CustomCursor.setHandCursor(addAdminButton);
        CustomCursor.setHandCursor(teacherDataButton);
        CustomCursor.setHandCursor(studentDataButton);
        CustomCursor.setHandCursor(courseDataButton);
        CustomCursor.setHandCursor(logoutButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == myProfileButton) {
            myProfile();
        } else if (e.getSource() == addAdminButton) {
            addAdmin();
        } else if (e.getSource() == teacherDataButton) {
            openTeacherData();
        } else if (e.getSource() == studentDataButton) {
            openStudentData();
        } else if (e.getSource() == courseDataButton) {
            openCourseData();
        } else if (e.getSource() == logoutButton) {
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Log out",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                dispose();
                new AdminLogin();
            }
        }
    }

    private void myProfile() {
        MyProfileDialog myProfileDialog = new MyProfileDialog(this, currentAdmin);
        myProfileDialog.setVisible(true);
    }

    private void addAdmin() {
        AddAdminDialog addAdminDialog = new AddAdminDialog(this);
        addAdminDialog.setVisible(true);
    }

    private void openTeacherData() {
        TeacherData teacherData = new TeacherData(this);
        this.setVisible(false);
        teacherData.setVisible(true);
    }

    private void openStudentData() {
        StudentData studentData = new StudentData(this);
        this.setVisible(false);
        studentData.setVisible(true);
    }

    private void openCourseData() {
        CourseData courseData = new CourseData(this);
        this.setVisible(false);
        courseData.setVisible(true);
    }
}