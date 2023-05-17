package GUI.Dialog.StudentDialog;

import Entity.*;
import EntityList.*;
import GUI.StudentGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ShowMediaFilesDialog extends JDialog {
    private Student currentStudent;
    private JComboBox<String> courseComboBox;
    private JButton playVideoButton, playAudioButton;

    public ShowMediaFilesDialog(StudentGUI parent, Student student) {
        super(parent, "Media Files", true);
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        this.currentStudent = student;

        initComponents();
        addComponents();
        initListeners();
    }

    private void initComponents() {
        courseComboBox = new JComboBox<>();
        courseComboBox.setBounds(20, 20, 200, 30);

        playVideoButton = new JButton("Play Video");
        playVideoButton.setBounds(20, 60, 150, 30);

        playAudioButton = new JButton("Play Audio");
        playAudioButton.setBounds(180, 60, 150, 30);
    }

    private void addComponents() {
        JPanel panel = new JPanel(null);
        panel.add(new JLabel("Select Course:"));
        panel.add(courseComboBox);
        panel.add(playVideoButton);
        panel.add(playAudioButton);

        getContentPane().add(panel, BorderLayout.CENTER);

        List<String> selectedCourseNames = CourseList.loadSelectedCoursesForStudent(currentStudent.getName());
        for (String courseName : selectedCourseNames) {
            Course course = CourseList.getCoursesByName(courseName);
            if (course != null) {
                String teacherName = findTeacherByCourse(course);
                courseComboBox.addItem(course.getName() + " (by " + teacherName + ")");
            }
        }
    }

    private String findTeacherByCourse(Course course) {
        List<Teacher> teachers = TeacherList.getTeacherList();
        for (Teacher teacher : teachers) {
            List<String> selectedTeacherCourses = CourseList.loadSelectedCoursesForTeacher(teacher.getName());
            if (selectedTeacherCourses.contains(course.getName())) {
                return teacher.getName();
            }
        }
        return "N/A";
    }

    private void initListeners() {
        playAudioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String courseName = (String) courseComboBox.getSelectedItem();
                String teacherName = courseName.substring(courseName.indexOf("(by ") + 4, courseName.indexOf(")"));
                PlayMedia playMedia = new PlayMedia(courseName, teacherName);
                playMedia.playAudio();
            }
        });

        playVideoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String courseName = (String) courseComboBox.getSelectedItem();
                String teacherName = courseName.substring(courseName.indexOf("(by ") + 4, courseName.indexOf(")"));
                PlayMedia playMedia = new PlayMedia(courseName, teacherName);
                playMedia.playVideo();
            }
        });
    }
}
