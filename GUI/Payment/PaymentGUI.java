package GUI.Payment;

import Entity.Student;
import EntityList.StudentList;
import GUI.Common.CustomCursor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentGUI extends JDialog implements ActionListener {
    private boolean paymentCancelled;
    private JTextField cardNumberField, cardUserNameField, cvcField, validityDateField;
    private JButton payButton, cancelButton;

    public PaymentGUI(Frame parent, Student student) {
        super(parent, "Payment", true);
        this.paymentCancelled = false;

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(700, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("../Pictures/Common/PaymentIcon.png"));
        setIconImage(icon.getImage());

        ImageIcon backgroundIcon = new ImageIcon("GUI/Pictures/Common/PaymentBG.png");
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(getWidth(), getHeight(),
                Image.SCALE_SMOOTH);
        backgroundIcon = new ImageIcon(backgroundImage);

        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.BOLD, 15);
        Font infoFont = new Font("Arial", Font.BOLD, 20);
        Color fontColor = Color.WHITE;
        Color infoFontColor = Color.decode("#D1D1D1");

        JLabel infoLabel = new JLabel("Online Payment System");
        infoLabel.setBounds(250, 60, 240, 80);
        infoLabel.setFont(infoFont);
        infoLabel.setForeground(infoFontColor);

        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberLabel.setBounds(165, 200, 140, 40);
        cardNumberLabel.setFont(labelFont);
        cardNumberLabel.setForeground(fontColor);
        cardNumberField = new JTextField();
        cardNumberField.setBounds(300, 200, 200, 40);
        cardNumberField.setFont(fieldFont);

        JLabel cardUserNameLabel = new JLabel("Card User Name:");
        cardUserNameLabel.setBounds(165, 260, 140, 40);
        cardUserNameLabel.setFont(labelFont);
        cardUserNameLabel.setForeground(fontColor);
        cardUserNameField = new JTextField();
        cardUserNameField.setBounds(300, 260, 200, 40);
        cardUserNameField.setFont(fieldFont);

        JLabel cvcLabel = new JLabel("CVC:");
        cvcLabel.setBounds(165, 320, 140, 40);
        cvcLabel.setFont(labelFont);
        cvcLabel.setForeground(fontColor);
        cvcField = new JTextField();
        cvcField.setBounds(300, 320, 200, 40);
        cvcField.setFont(fieldFont);

        JLabel validityDateLabel = new JLabel("Card Validity Date:");
        validityDateLabel.setBounds(165, 380, 140, 40);
        validityDateLabel.setFont(labelFont);
        validityDateLabel.setForeground(fontColor);
        validityDateField = new JTextField();
        validityDateField.setBounds(300, 380, 200, 40);
        validityDateField.setFont(fieldFont);

        payButton = new JButton("Pay");
        payButton.setFont(labelFont);
        payButton.setBackground(Color.decode("#4BAB2E"));
        payButton.addActionListener(this);
        payButton.setBounds(310, 450, 85, 30);

        cancelButton = new JButton("Cancel");
        cancelButton.setFont(labelFont);
        cancelButton.setBackground(Color.decode("#E33123"));
        cancelButton.addActionListener(this);
        cancelButton.setBounds(415, 450, 85, 30);

        backgroundLabel.add(infoLabel);
        backgroundLabel.add(cardNumberLabel);
        backgroundLabel.add(cardNumberField);
        backgroundLabel.add(cardUserNameLabel);
        backgroundLabel.add(cardUserNameField);
        backgroundLabel.add(cvcLabel);
        backgroundLabel.add(cvcField);
        backgroundLabel.add(validityDateLabel);
        backgroundLabel.add(validityDateField);
        backgroundLabel.add(payButton);
        backgroundLabel.add(cancelButton);

        setCursorIcon();
        setVisible(true);
    }

    private void setCursorIcon() {
        CustomCursor.setCustomCursor(this);
        CustomCursor.setHandCursor(payButton);
        CustomCursor.setHandCursor(cancelButton);
        CustomCursor.setHandCursor(cardNumberField);
        CustomCursor.setHandCursor(cardUserNameField);
        CustomCursor.setHandCursor(cvcField);
        CustomCursor.setHandCursor(validityDateField);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == payButton) {
            String cardNumber = cardNumberField.getText();
            String cardUserName = cardUserNameField.getText();
            String cvc = cvcField.getText();
            String validityDate = validityDateField.getText();

            // (dummy check)
            if (cardNumber.isEmpty() || cardUserName.isEmpty() || cvc.isEmpty() || validityDate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all the credit card details.");
                return;
            }

            boolean paymentSuccess = performDummyPaymentProcess(cardNumber, cardUserName, cvc, validityDate);

            if (paymentSuccess) {
                StudentList.saveStudentListToFile(StudentList.getStudentList());
                JOptionPane.showMessageDialog(this,
                        "Payment successful! Your account has been activated!!");
            } else {
                JOptionPane.showMessageDialog(this, "Payment failed. Please try again.");
            }

            setVisible(false);
        } else if (e.getSource() == cancelButton) {
            paymentCancelled = true;
            setVisible(false);
        }
    }

    public boolean isCancelled() {
        return paymentCancelled;
    }

    private boolean performDummyPaymentProcess(String cardNumber, String cardUserName, String cvc,
            String validityDate) {
        return true;
    }
}