import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class SlidePanel extends JPanel {
    private int width = 100;
    private int initialX = -width + width / 10;
    private int height = 400;
    private int newX;
    private int mouseInitialX;
    private boolean isDragging = false;
    private Color borderColor = new Color(252,222,6);
    private Color darkerGrey = new Color(40, 40, 40);

    public SlidePanel() {
        setBackground(darkerGrey);
        setBorder(new LineBorder(Color.red));
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
                    setLocation(0, getY());
                } else {
                    setLocation(-width + width / 10, getY());
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging) {
                    newX = initialX + e.getX() - mouseInitialX;
                    if (newX > width / 8) {
                        setLocation(0, getY());
                        isDragging = false;
                    } else {
                        setLocation(newX, getY());
                    }

                }
            }
        });
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


}

