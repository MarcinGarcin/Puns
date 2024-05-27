import javax.swing.*;
import java.awt.*;


class CustomCheckBox extends JCheckBox {
    public CustomCheckBox(String text) {
        super(text);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getModel().isPressed() || getModel().isSelected()) {
            g.setColor(Color.WHITE);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }
}