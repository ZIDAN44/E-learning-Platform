package GUI.Dialog.TeacherDialog;

import Entity.Teacher;
import EntityList.CourseList;
import GUI.TeacherGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class UploadAudioDialog extends JDialog implements ActionListener {
    private Teacher currentTeacher;
    private JButton uploadButton;
    private JButton cancelButton;
    private JTextField filePathField;
    private JFileChooser fileChooser;

    public UploadAudioDialog(TeacherGUI parent, Teacher teacher) {
        super(parent, "Upload Audio", true);
        this.currentTeacher = teacher;

        initComponents();
        addListeners();
        createLayout();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(430, 150);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        filePathField = new JTextField(25);
        uploadButton = new JButton("Upload");
        uploadButton.setBackground(new Color(23, 162, 184));
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(220, 53, 69));
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    private void addListeners() {
        uploadButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }

    private void createLayout() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formPanel.setBackground(new Color(255, 255, 255));
        formPanel.add(new JLabel("Select File: "));
        formPanel.add(filePathField);
        formPanel.add(uploadButton);
        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(255, 255, 255));
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == uploadButton) {
            int returnValue = fileChooser.showOpenDialog(this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                filePathField.setText(selectedFile.getAbsolutePath());

                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to upload \"" + selectedFile.getName() + "\"?", "Confirm Upload",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    List<String> selectedCourseNames = CourseList
                            .loadSelectedCoursesForTeacher(currentTeacher.getName());
                    Object[] coursesArray = selectedCourseNames.toArray();
                    String selectedCourse = (String) JOptionPane.showInputDialog(this,
                            "For which course do you want to upload the audio?", "Select Course",
                            JOptionPane.PLAIN_MESSAGE, null, coursesArray, coursesArray[0]);

                    if (selectedCourse != null) {
                        if (selectedCourse != null && !selectedCourse.isEmpty()) {
                            File audioDir = new File("Data/Teacher/audio/" + currentTeacher.getName() + "/" + selectedCourse);
                            audioDir.mkdirs();

                            File destinationFile = new File(audioDir, selectedFile.getName());

                            try {
                                Files.copy(selectedFile.toPath(), destinationFile.toPath(),
                                        StandardCopyOption.REPLACE_EXISTING);
                                JOptionPane.showMessageDialog(this, "Audio uploaded successfully.", "Success",
                                        JOptionPane.INFORMATION_MESSAGE);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                                JOptionPane.showMessageDialog(this, "Error uploading the audio. Please try again.",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }
}