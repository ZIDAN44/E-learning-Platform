package GUI;

import Entity.*;
import EntityList.*;
import GUI.Common.*;
import GUI.Dialog.StudentDialog.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentGUI extends JFrame implements ActionListener {
    private Student currentStudent;
    private JButton myProfileButton, selectCourseButton, showCoursesButton, showMediaFilesButton;
    private JButton logoutButton;

    public StudentGUI(Student student) {
        setTitle("STUDENT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 800);
        setLocationRelativeTo(null);
        setLayout(null);

        this.currentStudent = student;

        // Load data
        CourseList.loadCourseListFromFile();
        TeacherList.loadTeacherListFromFile();

        ImageIcon icon = new ImageIcon(getClass().getResource("Pictures/Common/Icon.png"));
        setIconImage(icon.getImage());

        initButtons();
        initButtonColors();
        setCursorIcon();

        ImageIcon bgImage = new ImageIcon(getClass().getResource("Pictures/StudentGUI/Background.png"));
        JLabel bgLabel = new JLabel(bgImage);
        bgLabel.setBounds(0, 0, getWidth(), getHeight());
        add(bgLabel);

        setVisible(true);
    }

    private void initButtons() {
        int buttonWidth = 260;
        int buttonHeight = 50;

        myProfileButton = createButton("");
        ImageIcon myProfileIcon = new ImageIcon("GUI/Pictures/StudentGUI/My_Profile.png");
        myProfileButton.setIcon(myProfileIcon);
        myProfileButton.setBounds(90, 70, 150, 150);

        selectCourseButton = createButton("Select Courses");
        selectCourseButton.setBounds(410, 280, buttonWidth, buttonHeight);
        showCoursesButton = createButton("Show My Courses");
        showCoursesButton.setBounds(410, 340, buttonWidth, buttonHeight);
        showMediaFilesButton = createButton("Show Media Files");
        showMediaFilesButton.setBounds(410, 400, buttonWidth, buttonHeight);

        logoutButton = createButton("");
        ImageIcon logoutIcon = new ImageIcon(getClass().getResource("Pictures/Common/exit.png"));
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
        selectCourseButton.setBackground(Color.decode("#FCB97D"));
        showCoursesButton.setBackground(Color.decode("#FCB97D"));
        showMediaFilesButton.setBackground(Color.decode("#FCB97D"));
        logoutButton.setBackground(Color.decode("#FFA07A"));
    }

    private void setCursorIcon() {
        CustomCursor.setCustomCursor(this);
        CustomCursor.setHandCursor(myProfileButton);
        CustomCursor.setHandCursor(selectCourseButton);
        CustomCursor.setHandCursor(showCoursesButton);
        CustomCursor.setHandCursor(showMediaFilesButton);
        CustomCursor.setHandCursor(logoutButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == myProfileButton) {
            myProfile();
        } else if (e.getSource() == selectCourseButton) {
            selectCourse();
        } else if (e.getSource() == showCoursesButton) {
            showCourses();
        } else if (e.getSource() == showMediaFilesButton) {
            showMedia();
        } else if (e.getSource() == logoutButton) {
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Log out",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                dispose();
                new HomePage();
            }
        }
    }

    private void myProfile() {
        MyProfileDialog myProfileDialog = new MyProfileDialog(this, currentStudent);
        myProfileDialog.setVisible(true);
    }

    private void selectCourse() {
        SelectCourseDialog selectCourseDialog = new SelectCourseDialog(this, currentStudent);
        selectCourseDialog.setVisible(true);
    }

    private void showCourses() {
        java.util.List<String> selectedCourseNames = CourseList.loadSelectedCoursesForStudent(currentStudent.getName());

        if (selectedCourseNames.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You still haven't selected any course!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        ShowCoursesDialog showCoursesDialog = new ShowCoursesDialog(this, currentStudent);
        showCoursesDialog.setVisible(true);
    }

    private void showMedia() {
        ShowMediaFilesDialog showMediaFilesDialog = new ShowMediaFilesDialog(this, currentStudent);
        showMediaFilesDialog.setVisible(true);
    }
}