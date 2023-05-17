package GUI.Common;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedCornerButton extends JButton {
    private int cornerRadius;

    public RoundedCornerButton(String label, int cornerRadius) {
        super(label);
        this.cornerRadius = cornerRadius;
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));
        super.paintComponent(g);
    }
}
