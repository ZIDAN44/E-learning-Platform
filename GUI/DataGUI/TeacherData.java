package GUI.DataGUI;

import EntityList.*;
import GUI.AdminGUI;
import GUI.Common.*;
import GUI.Dialog.AdminDialog.Teacher.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TeacherData extends JFrame implements ActionListener {
    private AdminGUI adminGUI;
    private JButton addTeacherButton, removeTeacherButton, editTeacherButton, printTeachersButton, backButton;

    public TeacherData(AdminGUI adminGUI) {
        setTitle("Teacher Data");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(860, 700);
        setLocationRelativeTo(adminGUI);

        this.adminGUI = adminGUI;

        // Load data
        TeacherList.loadTeacherListFromFile();

        // Load the image for the icon
        ImageIcon icon = new ImageIcon(getClass().getResource("../Pictures/Common/Icon.png"));
        setIconImage(icon.getImage());

        initButtons();
        initButtonColors();
        setCursorIcon();

        // Add background image
        ImageIcon bgImage = new ImageIcon(getClass().getResource("../Pictures/AdminGUI/Data_Background.png"));
        JLabel bgLabel = new JLabel(bgImage);
        bgLabel.setBounds(0, 0, getWidth(), getHeight());
        add(bgLabel);

        setVisible(true);
    }

    private void initButtons() {
        int buttonWidth = 140;
        int buttonHeight = 140;

        addTeacherButton = createButton("Add Teacher", 50, 200, buttonWidth, buttonHeight);
        removeTeacherButton = createButton("Remove Teacher", 250, 200, buttonWidth, buttonHeight);
        editTeacherButton = createButton("Edit Teacher", 450, 200, buttonWidth, buttonHeight);
        printTeachersButton = createButton("Show Teachers", 650, 200, buttonWidth, buttonHeight);
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
        addTeacherButton.setBackground(Color.decode("#BF2C69"));
        removeTeacherButton.setBackground(Color.decode("#BF2C69"));
        editTeacherButton.setBackground(Color.decode("#BF2C69"));
        printTeachersButton.setBackground(Color.decode("#BF2C69"));
        backButton.setBackground(Color.decode("#FFA07A"));
    }

    private void setCursorIcon() {
        CustomCursor.setCustomCursor(this);
        CustomCursor.setHandCursor(addTeacherButton);
        CustomCursor.setHandCursor(removeTeacherButton);
        CustomCursor.setHandCursor(editTeacherButton);
        CustomCursor.setHandCursor(printTeachersButton);
        CustomCursor.setHandCursor(backButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addTeacherButton) {
            addTeacher();
        } else if (e.getSource() == removeTeacherButton) {
            removeTeacher();
        } else if (e.getSource() == editTeacherButton) {
            editTeacher();
        } else if (e.getSource() == printTeachersButton) {
            showTeacher();
        } else if (e.getSource() == backButton) {
            adminGUI.setVisible(true);
            dispose();
        }
    }

    private void addTeacher() {
        AddTeacherDialog addTeacherDialog = new AddTeacherDialog(this);
        addTeacherDialog.setVisible(true);
    }

    private void removeTeacher() {
        RemoveTeacherDialog removeTeacherDialog = new RemoveTeacherDialog(this);
        this.setVisible(false);
        removeTeacherDialog.setVisible(true);
    }

    private void editTeacher() {
        EditTeacherDialog editTeacherDialog = new EditTeacherDialog(this);
        this.setVisible(false);
        editTeacherDialog.setVisible(true);
    }

    private void showTeacher() {
        ShowAllTeachers showAllTeachers = new ShowAllTeachers(this);
        this.setVisible(false);
        showAllTeachers.setVisible(true);
    }
}