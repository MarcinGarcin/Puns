import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class SlidePanel extends JPanel {
    private int width = 100;
    private int initialX = -width + width / 10;
    private int height = 400;
    private int newX;
    private int mouseInitialX;
    private boolean isDragging = false;

    public SlidePanel() {
        setBackground(Color.BLUE);
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
    public void updatePlayerLabel(ArrayList<Player> playerAndPoints){
        for (int i = 0; i < playerAndPoints.size(); i++) {
            Player player = playerAndPoints.get(i);
            JLabel playerLabel = new JLabel(i+"."+player.getName()+":   "+player.getScore());
            playerLabel.setForeground(Color.WHITE);
            playerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            add(playerLabel);
        }

    }


}

