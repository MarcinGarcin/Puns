import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.*;

public class DrawPanel extends JPanel {
    private Point lastPoint;
    private boolean isDrawing;
    private BufferedImage image;
    private Graphics2D g2d;
    private ObjectOutputStream out;

    public DrawPanel() {
        image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);

        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (isDrawing)
                    lastPoint = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (isDrawing && lastPoint != null) {
                    Point currentPoint = e.getPoint();
                    g2d.drawLine(lastPoint.x, lastPoint.y, currentPoint.x, currentPoint.y);
                    sendDrawData(lastPoint, currentPoint);
                    lastPoint = currentPoint;
                    repaint();
                }
            }
        });
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    private void sendDrawData(Point start, Point end) {
        System.out.println("Wysłano");
        try {
            out.writeObject(new DrawData(start, end));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    public void setDrawing(boolean isDrawer) {
        this.isDrawing = isDrawer;
    }

    public void clear() {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2d.setColor(Color.BLACK);
        repaint();
    }

    public void drawLine(Point start, Point end) {
        g2d.drawLine(start.x, start.y, end.x, end.y);
        repaint();
    }
}


