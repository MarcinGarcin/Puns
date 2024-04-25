import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private Color grey = new Color(51, 51, 51);
    private Color darkerGrey = new Color(40, 40, 40);
    private int width = 1280;
    private int height = 720;

    public Window() {
        setupWindow();
        setupJoinGamePanel();
    }

    public void setupWindow() {
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        getContentPane().setBackground(grey);
    }

    public void setupJoinGamePanel() {
        JPanel joinGamePanel = new JPanel();
        joinGamePanel.setBackground(Color.red);
        joinGamePanel.setBounds(width/2 - width/6,height/2 - height/3, width/6,height/3);


        add(joinGamePanel);
    }
}