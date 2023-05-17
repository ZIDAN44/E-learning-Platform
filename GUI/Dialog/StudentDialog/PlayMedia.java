package GUI.Dialog.StudentDialog;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PlayMedia {
    private String courseName;
    private String teacherName;

    public PlayMedia(String courseName, String teacherName) {
        this.courseName = courseName;
        this.teacherName = teacherName;
    }

    public void playMedia(String mediaType, String fileExtension, String messageType) {
        String filePath = String.format("Data/Teacher/%s/%s/%s/", mediaType, teacherName,
                courseName.substring(0, courseName.indexOf(" (by ")));

        File fileDir = new File(filePath);
        if (fileDir.exists()) {
            File[] files = fileDir.listFiles((dir, name) -> name.endsWith(fileExtension));
            if (files != null && files.length > 0) {
                String[] options = new String[files.length];
                for (int i = 0; i < options.length; i++) {
                    options[i] = "File " + (i + 1);
                }
                int choice = JOptionPane.showOptionDialog(null, "Select a file to play", "Play " + mediaType,
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                if (choice >= 0 && choice < files.length) {
                    File selectedFile = files[choice];
                    if (selectedFile.exists()) {
                        try {
                            Desktop.getDesktop().open(selectedFile);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Selected " + mediaType.toLowerCase() + " file not found.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No " + mediaType.toLowerCase() + " files found for this course.");
            }
        } else {
            JOptionPane.showMessageDialog(null, mediaType + " directory not found.");
        }
    }

    public void playAudio() {
        playMedia("audio", ".mp3", "Audio");
    }

    public void playVideo() {
        playMedia("video", ".mp4", "Video");
    }
}
