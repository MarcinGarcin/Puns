import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class DrawPanel extends JPanel {
    private Point lastPoint;
    private boolean isDrawing;
    private BufferedImage image;
    private Graphics2D g2d;
    private ObjectOutputStream out;
    private Color drawColor = Color.BLACK;
    private int drawSize = 5;
    private boolean isEraser = false;

    public DrawPanel() {
        image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(drawColor);
        g2d.setStroke(new BasicStroke(drawSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

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
                    if (isEraser) {
                        g2d.setColor(Color.WHITE);
                        g2d.setStroke(new BasicStroke(drawSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    } else {
                        g2d.setColor(drawColor);
                        g2d.setStroke(new BasicStroke(drawSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    }
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
        g2d.setColor(drawColor);
        repaint();
    }

    public void drawLine(Point start, Point end) {
        g2d.drawLine(start.x, start.y, end.x, end.y);
        repaint();
    }

    public void drawData(DrawData data) {
        drawLine(data.getStart(), data.getEnd());
    }

    public void setDrawColor(Color color) {
        this.drawColor = color;
    }

    public void setDrawSize(int size) {
        this.drawSize = size;
    }

    public void setEraser(boolean isEraser) {
        this.isEraser = isEraser;
    }
}