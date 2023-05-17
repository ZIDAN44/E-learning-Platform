package GUI.DataGUI;

import EntityList.*;
import GUI.AdminGUI;
import GUI.Common.*;
import GUI.Dialog.AdminDialog.Student.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentData extends JFrame implements ActionListener {
    private AdminGUI adminGUI;
    private JButton addStudentButton, removeStudentButton, editStudentButton, printStudentsButton, backButton;

    public StudentData(AdminGUI adminGUI) {
        setTitle("Student Data");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(860, 700);
        setLocationRelativeTo(adminGUI);

        this.adminGUI = adminGUI;

        // Load data
        StudentList.loadStudentListFromFile();

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

        addStudentButton = createButton("Add Student", 50, 200, buttonWidth, buttonHeight);
        removeStudentButton = createButton("Remove Student", 250, 200, buttonWidth, buttonHeight);
        editStudentButton = createButton("Edit Student", 450, 200, buttonWidth, buttonHeight);
        printStudentsButton = createButton("Show Students", 650, 200, buttonWidth, buttonHeight);
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
        addStudentButton.setBackground(Color.decode("#BF2C69"));
        removeStudentButton.setBackground(Color.decode("#BF2C69"));
        editStudentButton.setBackground(Color.decode("#BF2C69"));
        printStudentsButton.setBackground(Color.decode("#BF2C69"));
        backButton.setBackground(Color.decode("#FFA07A"));
    }

    private void setCursorIcon() {
        CustomCursor.setCustomCursor(this);
        CustomCursor.setHandCursor(addStudentButton);
        CustomCursor.setHandCursor(removeStudentButton);
        CustomCursor.setHandCursor(editStudentButton);
        CustomCursor.setHandCursor(printStudentsButton);
        CustomCursor.setHandCursor(backButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addStudentButton) {
            addStudent();
        } else if (e.getSource() == removeStudentButton) {
            removeStudent();
        } else if (e.getSource() == editStudentButton) {
            editStudent();
        } else if (e.getSource() == printStudentsButton) {
            showStudent();
        } else if (e.getSource() == backButton) {
            adminGUI.setVisible(true);
            dispose();
        }
    }

    private void addStudent() {
        AddStudentDialog addStudentDialog = new AddStudentDialog(this);
        addStudentDialog.setVisible(true);
    }

    private void removeStudent() {
        RemoveStudentDialog removeStudentDialog = new RemoveStudentDialog(this);
        this.setVisible(false);
        removeStudentDialog.setVisible(true);
    }

    private void editStudent() {
        EditStudentDialog editStudentDialog = new EditStudentDialog(this);
        this.setVisible(false);
        editStudentDialog.setVisible(true);
    }

    private void showStudent() {
        ShowAllStudents showAllStudents = new ShowAllStudents(this);
        this.setVisible(false);
        showAllStudents.setVisible(true);
    }
}