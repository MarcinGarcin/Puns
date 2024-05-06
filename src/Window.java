import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Window extends JFrame {
    private Color grey = new Color(51, 51, 51);
    private Color darkerGrey = new Color(40, 40, 40);
    private DrawPanel drawPanel;
    private SlidePanel slidePanel;
    private int width = 1280;
    private int height = 720;
    private String ip ;
    private ChatPanel chatPanel;
    private JPanel gamePanel;
    private GameHandler gameHandler;

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
        //TODO add avatar photo, choosing username
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
                try {
                    startGame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        loginPanel.add(joinGame);
        add(loginPanel);
    }

    private void startGame() throws IOException, ClassNotFoundException {
        gamePanel = new JPanel();
        gamePanel.setBounds(0,0,width,height);
        gamePanel.setBackground(darkerGrey);
        gamePanel.setLayout(null);


        slidePanel = new SlidePanel();


        chatPanel = new ChatPanel(width / 5,height-50);
        chatPanel.setBackground(grey);
        chatPanel.setLayout(null);


        gameHandler = new GameHandler(ip,slidePanel,chatPanel);
        chatPanel.setGameHandler(gameHandler);


        drawPanel = new DrawPanel();
        drawPanel.setBounds(width / 5 + 20, 20, width - width / 5 - 40, height - 140);
        drawPanel.setBackground(Color.WHITE);


        gamePanel.add(slidePanel);
        gamePanel.add(chatPanel);
        gamePanel.add(drawPanel);
        add(gamePanel);
    }
}
