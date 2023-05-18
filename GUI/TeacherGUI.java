package GUI;

import Entity.*;
import EntityList.*;
import GUI.Common.*;
import GUI.Dialog.TeacherDialog.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeacherGUI extends JFrame implements ActionListener {
    private Teacher currentTeacher;
    private JButton selectCourseButton, uploadVideoButton, uploadAudioButton, showMediaFilesButton,
            showSelectedCoursesButton;
    private JButton myProfileButton, logoutButton;

    public TeacherGUI(Teacher teacher) {
        setTitle("TEACHER");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 800);
        setLocationRelativeTo(null);
        setLayout(null);

        this.currentTeacher = teacher;

        ImageIcon icon = new ImageIcon(getClass().getResource("/GUI/Pictures/Common/Icon.png"));
        setIconImage(icon.getImage());

        initButtons();
        initButtonColors();
        setCursorIcon();

        ImageIcon bgImage = new ImageIcon(getClass().getResource("/GUI/Pictures/TeacherGUI/Background.png"));
        JLabel bgLabel = new JLabel(bgImage);
        bgLabel.setBounds(0, 0, getWidth(), getHeight());
        add(bgLabel);

        setVisible(true);
    }

    private void initButtons() {
        int buttonWidth = 260;
        int buttonHeight = 50;

        myProfileButton = createButton("");
        ImageIcon myProfileIcon = new ImageIcon(getClass().getResource("/GUI/Pictures/TeacherGUI/My_Profile.png"));
        myProfileButton.setIcon(myProfileIcon);
        myProfileButton.setBounds(90, 70, 150, 150);

        selectCourseButton = createButton("Select Courses");
        selectCourseButton.setBounds(410, 220, buttonWidth, buttonHeight);
        uploadVideoButton = createButton("Upload Video");
        uploadVideoButton.setBounds(410, 280, buttonWidth, buttonHeight);
        uploadAudioButton = createButton("Upload Audio");
        uploadAudioButton.setBounds(410, 340, buttonWidth, buttonHeight);
        showMediaFilesButton = createButton("Show Media Files");
        showMediaFilesButton.setBounds(410, 400, buttonWidth, buttonHeight);
        showSelectedCoursesButton = createButton("Show Selected Courses");
        showSelectedCoursesButton.setBounds(410, 460, buttonWidth, buttonHeight);

        logoutButton = createButton("");
        ImageIcon logoutIcon = new ImageIcon(getClass().getResource("/GUI/Pictures/Common/exit.png"));
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
        selectCourseButton.setBackground(Color.decode("#275E60"));
        uploadVideoButton.setBackground(Color.decode("#275E60"));
        uploadAudioButton.setBackground(Color.decode("#275E60"));
        showMediaFilesButton.setBackground(Color.decode("#275E60"));
        showSelectedCoursesButton.setBackground(Color.decode("#275E60"));
        logoutButton.setBackground(Color.decode("#FFA07A"));
    }

    private void setCursorIcon() {
        CustomCursor.setCustomCursor(this);
        CustomCursor.setHandCursor(myProfileButton);
        CustomCursor.setHandCursor(selectCourseButton);
        CustomCursor.setHandCursor(uploadVideoButton);
        CustomCursor.setHandCursor(uploadAudioButton);
        CustomCursor.setHandCursor(showMediaFilesButton);
        CustomCursor.setHandCursor(showSelectedCoursesButton);
        CustomCursor.setHandCursor(logoutButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == myProfileButton) {
            myProfile();
        } else if (e.getSource() == selectCourseButton) {
            selectCourses();
        } else if (e.getSource() == uploadVideoButton) {
            uploadVideo();
        } else if (e.getSource() == uploadAudioButton) {
            uploadAudio();
        } else if (e.getSource() == showMediaFilesButton) {
            showMediaFiles();
        } else if (e.getSource() == showSelectedCoursesButton) {
            showSelectedCourses();
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
        MyProfileDialog myProfileDialog = new MyProfileDialog(this, currentTeacher);
        myProfileDialog.setVisible(true);
    }

    private void selectCourses() {
        SelectCoursesDialog selectCoursesDialog = new SelectCoursesDialog(this, currentTeacher);
        selectCoursesDialog.setVisible(true);
    }

    private void uploadVideo() {
        java.util.List<String> selectedCourseNames = CourseList.loadSelectedCoursesForTeacher(currentTeacher.getName());

        if (selectedCourseNames.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You still haven't selected any course!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        UploadVideoDialog uploadVideoDialog = new UploadVideoDialog(this, currentTeacher);
        uploadVideoDialog.setVisible(true);
    }

    private void uploadAudio() {
        java.util.List<String> selectedCourseNames = CourseList.loadSelectedCoursesForTeacher(currentTeacher.getName());

        if (selectedCourseNames.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You still haven't selected any course!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        UploadAudioDialog uploadAudioDialog = new UploadAudioDialog(this, currentTeacher);
        uploadAudioDialog.setVisible(true);
    }

    private void showMediaFiles() {
        java.util.List<String> selectedCourseNames = CourseList.loadSelectedCoursesForTeacher(currentTeacher.getName());

        if (selectedCourseNames.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You still haven't selected any course!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        MediaFilesDialog mediaFilesDialog = new MediaFilesDialog(this, currentTeacher);
        mediaFilesDialog.setVisible(true);
    }

    private void showSelectedCourses() {
        ShowSelectCoursesDialog showSelectCoursesDialog = new ShowSelectCoursesDialog(this, currentTeacher);
        showSelectCoursesDialog.setVisible(true);
    }
}