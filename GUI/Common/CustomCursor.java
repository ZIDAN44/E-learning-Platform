package GUI.Common;

import javax.swing.*;
import java.awt.*;

public class CustomCursor {

    public static void setCustomCursor(JFrame frame) {
        ImageIcon cursorImage = new ImageIcon(CustomCursor.class.getResource("../Pictures/Common/Cursor.png"));
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursor = cursorImage.getImage();
        Point hotspot = new Point(0, 0);
        Cursor customCursor = toolkit.createCustomCursor(cursor, hotspot, "custom cursor");
        frame.setCursor(customCursor);
    }

    public static void setCustomCursor(JDialog dialog) {
        ImageIcon cursorImage = new ImageIcon(CustomCursor.class.getResource("../Pictures/Common/Cursor.png"));
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursor = cursorImage.getImage();
        Point hotspot = new Point(0, 0);
        Cursor customCursor = toolkit.createCustomCursor(cursor, hotspot, "custom cursor");
        dialog.setCursor(customCursor);
    }

    public static void setHandCursor(JButton button) {
        ImageIcon handCursorImage = new ImageIcon(CustomCursor.class.getResource("../Pictures/Common/HandCursor.png"));
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursor = handCursorImage.getImage();
        Point hotspot = new Point(0, 0);
        Cursor customCursor = toolkit.createCustomCursor(cursor, hotspot, "hand cursor");
        button.setCursor(customCursor);
    }

    public static void setHandCursor(JTextField field) {
        ImageIcon handCursorImage = new ImageIcon(CustomCursor.class.getResource("../Pictures/Common/HandCursor.png"));
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursor = handCursorImage.getImage();
        Point hotspot = new Point(0, 0);
        Cursor customCursor = toolkit.createCustomCursor(cursor, hotspot, "hand cursor");
        field.setCursor(customCursor);
    }
}
