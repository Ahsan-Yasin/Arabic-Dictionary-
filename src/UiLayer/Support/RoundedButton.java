package UiLayer.Support;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedButton extends JButton {
    private final Color color;

    public RoundedButton(String text, Color color) {
        super(text);
        this.color = color;
        setFocusPainted(false);
        setContentAreaFilled(false);
        setForeground(Color.WHITE);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
        super.paintComponent(g2);
        g2.dispose();
    }
}