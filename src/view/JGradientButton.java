package view;

import javax.swing.*;
import java.awt.*;

public class JGradientButton extends JButton {
    Color color = Color.white;
    public JGradientButton(String name) {
        super(name);
        setContentAreaFilled(false);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        //setFocusPainted(false); // used for demonstration
    }

    @Override
    protected void paintComponent(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(new GradientPaint(
                new Point(0, 0),
                Color.WHITE,
                new Point(0, getHeight()),
                color));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();

        super.paintComponent(g);
    }

    public void setColor(Color color) {
        this.color = color;
        repaint();
    }
}