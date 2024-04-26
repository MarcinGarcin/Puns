import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Window extends JFrame {
    private Color grey = new Color(51, 51, 51);
    private Color darkerGrey = new Color(40, 40, 40);
    private int width = 1280;
    private int height = 720;

    private JPanel drawPanel;

    public Window() {
        setupWindow();
        setupGamePanel();
        setupServerConnection(); // Call the function to setup server connection
    }

    public void setupWindow() {
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        getContentPane().setBackground(grey);
    }

    public void setupGamePanel() {
        JPanel chatPanel = new JPanel();
        chatPanel.setBounds(0, 0, width / 5, height);
        chatPanel.setBackground(darkerGrey);

        drawPanel = new DrawPanel();
        drawPanel.setBounds(width / 5 + 20, 20, width - width / 5 - 40, height - 140);
        drawPanel.setBackground(Color.WHITE);

        add(drawPanel);
        add(chatPanel);
    }

    public void setupServerConnection() {
        try {
            Socket socket = new Socket("127.0.0.1", 8888);

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            drawPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    try {
                        outputStream.writeObject(drawPanel);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            drawPanel.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    try {
                        outputStream.writeObject(drawPanel);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}