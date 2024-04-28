import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Window extends JFrame {
    private Color grey = new Color(51, 51, 51);
    private Color darkerGrey = new Color(40, 40, 40);
    private int width = 1280;
    private int height = 720;
    public Player player;

    private JPanel drawPanel;

    public Window() {
        setupWindow();
        setupLoginPanel();
    }

    private void setupWindow() {
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        getContentPane().setBackground(grey);
    }
    private void setupLoginPanel(){
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(darkerGrey);
        loginPanel.setLayout(null);
        loginPanel.setBounds(width/2-width/6, height/2-height/4, width/3, height/2);

        JButton joinGame = new JButton("join game");
        joinGame.setBounds(0,0,100,50);
        joinGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginPanel.setVisible(false);
                createPlayer("Marcin");
                setupGamePanel();
                setupServerConnection();
                repaint();
                revalidate();
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

        add(drawPanel);
        add(chatPanel);
    }
    private void createPlayer(String userName){
        player = new Player(userName);
    }

    private void setupServerConnection() {

        try {
            Socket socket = new Socket("127.0.0.1", 8888);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            outputStream.writeObject(player);
            outputStream.flush();

            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
