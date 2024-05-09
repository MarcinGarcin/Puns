import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class DrawPanel extends JPanel {
    private Point lastPoint;
    private boolean isDrawing;

    public DrawPanel() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (isDrawing)
                    lastPoint = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (isDrawing) {
                    Graphics g = getGraphics();
                    g.setColor(Color.BLACK);
                    g.drawLine(lastPoint.x, lastPoint.y, e.getX(), e.getY());
                    lastPoint = e.getPoint();
                }
            }
        });
    }

    public void setDrawing(boolean isDrawer) {
        this.isDrawing = isDrawer;
    }
}
