package GUI.DataGUI;

import EntityList.*;
import GUI.AdminGUI;
import GUI.Common.*;
import GUI.Dialog.AdminDialog.Course.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourseData extends JFrame implements ActionListener {
    private AdminGUI adminGUI;
    private JButton addCourseButton, removeCourseButton, editCourseButton, printCoursesButton, backButton;

    public CourseData(AdminGUI adminGUI) {
        setTitle("Course Data");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(860, 700);
        setLocationRelativeTo(adminGUI);

        this.adminGUI = adminGUI;

        // Load data
        CourseList.loadCourseListFromFile();

        // Load the image for the icon
        ImageIcon icon = new ImageIcon(getClass().getResource("/GUI/Pictures/Common/Icon.png"));
        setIconImage(icon.getImage());

        initButtons();
        initButtonColors();
        setCursorIcon();

        // Add background image
        ImageIcon bgImage = new ImageIcon(getClass().getResource("/GUI/Pictures/AdminGUI/Data_Background.png"));
        JLabel bgLabel = new JLabel(bgImage);
        bgLabel.setBounds(0, 0, getWidth(), getHeight());
        add(bgLabel);

        setVisible(true);
    }

    private void initButtons() {
        int buttonWidth = 140;
        int buttonHeight = 140;

        addCourseButton = createButton("Add Course", 50, 200, buttonWidth, buttonHeight);
        removeCourseButton = createButton("Remove Course", 250, 200, buttonWidth, buttonHeight);
        editCourseButton = createButton("Edit Course", 450, 200, buttonWidth, buttonHeight);
        printCoursesButton = createButton("Show Courses", 650, 200, buttonWidth, buttonHeight);
        backButton = createButton("Back", 700, 600, 100, 30);
    }

    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.DARK_GRAY);
        button.addActionListener(this);
        add(button);
        return button;
    }

    private void initButtonColors() {
        addCourseButton.setBackground(Color.decode("#BF2C69"));
        removeCourseButton.setBackground(Color.decode("#BF2C69"));
        editCourseButton.setBackground(Color.decode("#BF2C69"));
        printCoursesButton.setBackground(Color.decode("#BF2C69"));
        backButton.setBackground(Color.decode("#FFA07A"));
    }

    private void setCursorIcon() {
        CustomCursor.setCustomCursor(this);
        CustomCursor.setHandCursor(addCourseButton);
        CustomCursor.setHandCursor(removeCourseButton);
        CustomCursor.setHandCursor(editCourseButton);
        CustomCursor.setHandCursor(printCoursesButton);
        CustomCursor.setHandCursor(backButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addCourseButton) {
            addCourse();
        } else if (e.getSource() == removeCourseButton) {
            removeCourse();
        } else if (e.getSource() == editCourseButton) {
            editCourse();
        } else if (e.getSource() == printCoursesButton) {
            showCourse();
        } else if (e.getSource() == backButton) {
            adminGUI.setVisible(true);
            dispose();
        }
    }

    private void addCourse() {
        AddCourseDialog addCourseDialog = new AddCourseDialog(this);
        addCourseDialog.setVisible(true);
    }

    private void removeCourse() {
        RemoveCourseDialog removeCourseDialog = new RemoveCourseDialog(this);
        this.setVisible(false);
        removeCourseDialog.setVisible(true);
    }

    private void editCourse() {
        EditCourseDialog editCourseDialog = new EditCourseDialog(this);
        this.setVisible(false);
        editCourseDialog.setVisible(true);
    }

    private void showCourse() {
        ShowAllCourses showAllCourses = new ShowAllCourses(this);
        this.setVisible(false);
        showAllCourses.setVisible(true);
    }
}