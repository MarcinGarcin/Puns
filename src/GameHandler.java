import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class GameHandler implements Runnable {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private Player player;
    private String ip;
    private SlidePanel slidePanel;
    private ChatPanel chatPanel;
    private DrawPanel drawPanel;
    private ArrayList<String> chatMessages = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();

    public GameHandler(String ip, SlidePanel slidePanel, ChatPanel chatPanel, DrawPanel drawPanel) throws IOException, ClassNotFoundException {
        this.ip = ip;
        this.slidePanel = slidePanel;
        this.chatPanel = chatPanel;
        this.drawPanel = drawPanel;
        createPlayer();
        setupServerConnection();
        sendJoiningPing();
        new Thread(this).start();
    }

    @Override
    public void run() {
        listenForPacket();
    }

    private void setupServerConnection() {
        try {
            socket = new Socket(ip, 12345);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.err.println("Failed to connect to the server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void createPlayer() {
        player = new Player("Marcin");
    }

    private void sendJoiningPing() {
        try {
            out.writeObject(player);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void listenForPacket() {
        try {
            while (true) {
                Object packet = in.readObject();
                if (packet instanceof ArrayList<?>) {
                    players = (ArrayList<Player>) packet;
                    slidePanel.updatePlayerLabel(players);
                } else if (packet instanceof Message) {
                    Message message = (Message) packet;
                    chatMessages.add(message.getSender() + ": " + message.getContent());
                    chatPanel.updateChat(chatMessages);
                } else if (packet instanceof Player){
                    player = (Player) packet;
                    drawPanel.setDrawing(player.getDrawing());
                    if(player.getDrawing()){
                        System.out.println("Rysuhje");
                    }
                }
            }
        } catch (EOFException e) {
            System.out.println("End of stream reached");
            new Server();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String content) throws IOException {
        out.reset();
        out.writeObject(new Message(player.getName(), content));
        out.flush();
    }

    public ObjectOutputStream getOut() {
        return out;
    }
}
