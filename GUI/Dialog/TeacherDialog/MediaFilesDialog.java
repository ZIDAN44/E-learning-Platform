package GUI.Dialog.TeacherDialog;

import Entity.Teacher;
import EntityList.CourseList;
import GUI.TeacherGUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MediaFilesDialog extends JDialog {
    private Teacher currentTeacher;
    private JList<String> videoList;
    private JList<String> audioList;
    private String selectedCourse;

    public MediaFilesDialog(TeacherGUI parent, Teacher teacher) {
        super(parent, "Media Files", true);
        this.currentTeacher = teacher;

        // Get the selected course for the current teacher
        List<String> selectedCourseNames = CourseList.loadSelectedCoursesForTeacher(currentTeacher.getName());
        Object[] coursesArray = selectedCourseNames.toArray();
        selectedCourse = (String) JOptionPane.showInputDialog(this,
                "For which course do you want to view the media files?", "Select Course",
                JOptionPane.PLAIN_MESSAGE, null, coursesArray, coursesArray[0]);

        initComponents();
        createLayout();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        File videoDir = new File("Data/Teacher/video/" + currentTeacher.getName() + "/" + selectedCourse);
        videoDir.mkdirs();

        String[] videoFiles = videoDir
                .list((dir, name) -> name.toLowerCase().endsWith(".mp4") || name.toLowerCase().endsWith(".avi"));
        videoList = new JList<>(videoFiles);

        File audioDir = new File("Data/Teacher/audio/" + currentTeacher.getName() + "/" + selectedCourse);
        audioDir.mkdirs();

        String[] audioFiles = audioDir
                .list((dir, name) -> name.toLowerCase().endsWith(".mp3") || name.toLowerCase().endsWith(".wav"));
        audioList = new JList<>(audioFiles);
    }

    private void createLayout() {
        setLayout(null);

        JPanel videoPanel = createMediaPanel("Video Files", videoList);
        videoPanel.setBounds(20, 20, 300, 380);
        add(videoPanel);

        JPanel audioPanel = createMediaPanel("Audio Files", audioList);
        audioPanel.setBounds(340, 20, 300, 380);
        add(audioPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBounds(20, 420, 620, 50);

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> {
            boolean removed = false;
            if (!videoList.isSelectionEmpty()) {
                String videoName = videoList.getSelectedValue();
                if (videoName != null) {
                    File videoFile = new File(
                            "Data/Teacher/video/" + currentTeacher.getName() + "/" + selectedCourse + "/" + videoName);
                    int confirm = JOptionPane.showConfirmDialog(this,
                            "Are you sure you want to remove " + videoName + "?");
                    if (confirm == JOptionPane.YES_OPTION) {
                        videoFile.delete();
                        removed = true;
                    }
                }
            } else if (!audioList.isSelectionEmpty()) {
                String audioName = audioList.getSelectedValue();
                if (audioName != null) {
                    File audioFile = new File(
                            "Data/Teacher/audio/" + currentTeacher.getName() + "/" + selectedCourse + "/" + audioName);
                    int confirm = JOptionPane.showConfirmDialog(this,
                            "Are you sure you want to remove " + audioName + "?");
                    if (confirm == JOptionPane.YES_OPTION) {
                        audioFile.delete();
                        removed = true;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No video or audio file is selected.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            if (removed) {
                updateFileLists(); // Make sure it shows the updated list after removing a file
                JOptionPane.showMessageDialog(this, "Selected media files have been removed.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        buttonPanel.add(removeButton);
        add(buttonPanel);
    }

    private void updateFileLists() {
        File videoDir = new File("Data/Teacher/video/" + currentTeacher.getName() + "/" + selectedCourse);
        videoDir.mkdirs();

        String[] videoFiles = videoDir
                .list((dir, name) -> name.toLowerCase().endsWith(".mp4") || name.toLowerCase().endsWith(".avi"));
        videoList.setListData(videoFiles);

        File audioDir = new File("Data/Teacher/audio/" + currentTeacher.getName() + "/" + selectedCourse);
        audioDir.mkdirs();

        String[] audioFiles = audioDir
                .list((dir, name) -> name.toLowerCase().endsWith(".mp3") || name.toLowerCase().endsWith(".wav"));
        audioList.setListData(audioFiles);
    }

    private JPanel createMediaPanel(String title, JList<String> mediaList) {
        JPanel panel = new JPanel(null);
        panel.setBorder(BorderFactory.createTitledBorder(title));

        JScrollPane scrollPane = new JScrollPane(mediaList);
        scrollPane.setBounds(10, 30, 280, 300);
        panel.add(scrollPane);

        JButton playButton = new JButton("Play");
        playButton.setBounds(105, 335, 90, 30);
        playButton.addActionListener(e -> {
            String selectedFile = mediaList.getSelectedValue();
            if (selectedFile != null) {
                String filePath = title.startsWith("Video")
                        ? "Data/Teacher/video/" + currentTeacher.getName() + "/" + selectedCourse + "/" + selectedFile
                        : "Data/Teacher/audio/" + currentTeacher.getName() + "/" + selectedCourse + "/" + selectedFile;
                try {
                    Desktop.getDesktop().open(new File(filePath));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error opening file: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No " + title + " file is selected.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(playButton);
        return panel;
    }
}