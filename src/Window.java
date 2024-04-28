import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

public class Window extends JFrame {
    private Color grey = new Color(51, 51, 51);
    private Color darkerGrey = new Color(40, 40, 40);
    private int width = 1280;
    private int height = 720;
    private Player player;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private JPanel drawPanel;

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
            }
        });
        loginPanel.add(joinGame);
        add(loginPanel);
    }

    private void setupGamePanel() {
        JPanel chatPanel = new JPanel();
        chatPanel.setBounds(0, 0, width / 5, height);
        chatPanel.setBackground(darkerGrey);

        drawPanel = new JPanel();
        drawPanel.setBounds(width / 5 + 20, 20, width - width / 5 - 40, height - 140);
        drawPanel.setBackground(Color.WHITE);

        SlidePanel slidePanel = new SlidePanel();

        add(slidePanel);
        add(drawPanel);
        add(chatPanel);
    }

    private void createPlayer(String userName) {
        player = new Player(userName);
    }

    private void setupServerConnection() {
        try {
            socket = new Socket("127.0.0.1", 8888);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(player);
            out.flush();

            in = new ObjectInputStream(socket.getInputStream());
            gameHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void gameHandler() {
        new Thread(() -> {
            try {
                while (true) {
                    Object object = in.readObject();
                    Message message = (Message) object;
                    System.out.println(message.getSender()+": "+message.getContent());
                }
            } catch (EOFException | OptionalDataException e) {
                // These exceptions are thrown when there are no more objects to read
                System.out.println("No more objects to read from the stream.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
