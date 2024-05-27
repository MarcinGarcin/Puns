import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class SlidePanel extends JPanel {
    private int width = 140;
    private int initialX = -width + width / 10;
    private int height = 400;
    private int targetX;
    private int mouseInitialX;
    private boolean isDragging = false;
    private Color borderColor = new Color(252, 222, 6);
    private Color darkerGrey = new Color(40, 40, 40);
    private Timer timer;
    private int animationDuration = 300; // Animation duration in milliseconds

    public SlidePanel() {
        setBackground(darkerGrey);
        setBorder(new LineBorder(borderColor));
        setBounds(initialX, 50, width, height);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isDragging = true;
                mouseInitialX = e.getX();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDragging = false;
                if (getX() >= -width / 2) {
                    targetX = 0;
                } else {
                    targetX = -width + width / 10;
                }
                startAnimation();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging) {
                    int newX = getX() + e.getX() - mouseInitialX;
                    if (newX > width / 8) {
                        setLocation(0, getY());
                        isDragging = false;
                    } else {
                        setLocation(newX, getY());
                    }
                    mouseInitialX = e.getX();
                }
            }
        });

        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentX = getX();
                int distance = targetX - currentX;
                int delta = (int) (distance * 0.1);
                if (Math.abs(delta) > 0) {
                    setLocation(currentX + delta, getY());
                } else {
                    setLocation(targetX, getY());
                    timer.stop();
                }
            }
        });
    }

    private void startAnimation() {
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 12));
        String text = "↓ P L A Y E R S";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        int x = (getWidth() - textHeight)+10;
        int y = (getHeight() + textWidth) / 2;


        g2d.rotate(-Math.PI / 2, x, y);
        g2d.drawString(text, x, y);

        g2d.dispose();
    }


    public void updatePlayerLabel(ArrayList<Player> playerList) {
        this.removeAll();

        Collections.sort(playerList, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return Integer.compare(p2.getScore(), p1.getScore());
            }
        });

        for (int i = 0; i < playerList.size(); i++) {
            Player player = playerList.get(i);
            JLabel playerLabel = new JLabel((i + 1) + ". " + player.getName() + ": " + player.getScore());
            playerLabel.setForeground(Color.WHITE);
            playerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            add(playerLabel);
        }

        revalidate();
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
}
