import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Window extends JFrame {
    private Color grey = new Color(51, 51, 51);
    private Color darkerGrey = new Color(40, 40, 40);
    private String ip;
    private Player player;
    private DrawPanel drawPanel;
    private SlidePanel slidePanel;
    private ServerConnection servCon;
    private int port = 32768;
    private int width = 1280;
    private int height = 720;

    public Window(String ip) {
        this.ip = ip;
        setupWindow();
        setupLoginPanel();
    }

    private void setupWindow() {
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(grey);
        setResizable(false);
        setVisible(true);
    }

    private void setupLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(darkerGrey);
        loginPanel.setLayout(null);
        loginPanel.setBounds(width / 2 - width / 6, height / 2 - height / 4, width / 3, height / 2);

        JButton joinGame = new JButton("Join Game");
        joinGame.setBounds(0, 0, 100, 50);
        joinGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginPanel.setVisible(false);
                createPlayer("Marcin");
                setupGamePanel();

                setupServerConnection();
                GameHandler gameHandler = new GameHandler(servCon.getInputStream(),servCon.getOutputStream(), slidePanel,player);
                new Thread(gameHandler).start();
            }
        });

        loginPanel.add(joinGame);
        add(loginPanel);
    }

    private void setupGamePanel() {
        JPanel chatPanel = new JPanel();
        chatPanel.setBounds(0, 0, width / 5, height);
        chatPanel.setBackground(darkerGrey);

        drawPanel = new DrawPanel();
        drawPanel.setBounds(width / 5 + 20, 20, width - width / 5 - 40, height - 140);
        drawPanel.setBackground(Color.WHITE);

        slidePanel = new SlidePanel();

        add(slidePanel);
        add(drawPanel);
        add(chatPanel);
    }

    private void createPlayer(String userName) {
        player = new Player(userName);
    }

    private void setupServerConnection() {
        servCon = new ServerConnection(ip, port, player);
    }

}
