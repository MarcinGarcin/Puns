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
    private String ip;
    private String nickName;
    private ChatPanel chatPanel;
    private JPanel gamePanel;
    private GameHandler gameHandler;
    private DrawControlPanel drawControlPanel;

    public Window() {
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
        JPanel loginMainPanel = new JPanel();
        loginMainPanel.setBackground(darkerGrey);
        loginMainPanel.setLayout(null);
        loginMainPanel.setVisible(true);

        RoundedPanel loginPanel = new RoundedPanel(30);
        loginPanel.setBackground(grey);
        loginPanel.setLayout(null);
        loginPanel.setVisible(true);
        loginPanel.setBounds(width/2-200, height/2-200, 400, 400);

        JTextArea nickArea = new JTextArea("Nickname");
        nickArea.setEditable(true);
        nickArea.setBackground(darkerGrey);
        nickArea.setForeground(Color.WHITE);
        nickArea.setFont(new Font("Arial", Font.BOLD, 20));
        nickArea.setBounds(100, 75, 200, 30);
        nickArea.setVisible(true);


        JTextArea ipArea = new JTextArea();
        ipArea.setEditable(true);
        ipArea.setBackground(darkerGrey);
        ipArea.setForeground(Color.WHITE);
        ipArea.setFont(new Font("Arial", Font.BOLD, 20));
        ipArea.setBounds(100, 125, 200, 30);
        ipArea.setVisible(true);

        JButton joinGame = new JButton("Join Game");
        joinGame.setBounds(75, 175, 250, 50);
        joinGame.setVisible(true);
        joinGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginMainPanel.setVisible(false);
                try {
                    ip = ipArea.getText();
                    nickName = nickArea.getText();
                    startGame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        loginPanel.add(ipArea);
        loginPanel.add(joinGame);
        loginPanel.add(nickArea);

        loginMainPanel.add(loginPanel);
        loginMainPanel.validate();
        loginMainPanel.repaint();

        add(loginMainPanel);
        validate();
        repaint();

        //TODO polish login panel
    }

    private void startGame() throws IOException, ClassNotFoundException {
        gamePanel = new JPanel();
        gamePanel.setBounds(0,0,width,height);
        gamePanel.setBackground(darkerGrey);
        gamePanel.setLayout(null);

        slidePanel = new SlidePanel();

        chatPanel = new ChatPanel(width / 5, height - 50);
        chatPanel.setBackground(grey);
        chatPanel.setLayout(null);


        drawPanel = new DrawPanel();
        drawPanel.setBounds(width / 5 + 20, 20, width - width / 5 - 40, height - 140);
        drawPanel.setBackground(Color.WHITE);
        drawPanel.setDrawing(true);

        drawControlPanel = new DrawControlPanel(drawPanel);
        drawControlPanel.setBounds(width/2-120,height-110,500,60);
        drawControlPanel.setBackground(grey);
        drawControlPanel.setVisible(true);

        gameHandler = new GameHandler(ip, slidePanel, chatPanel,drawPanel, nickName);
        chatPanel.setGameHandler(gameHandler);
        drawPanel.setOut(gameHandler.getOut());

        gamePanel.add(slidePanel);
        gamePanel.add(chatPanel);
        gamePanel.add(drawPanel);
        gamePanel.add(drawControlPanel);
        add(gamePanel);
    }
}
