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
    private ArrayList<String> messageList;
    private DrawPanel drawPanel;

    public GameHandler(String ip, SlidePanel slidePanel,ChatPanel chatPanel, DrawPanel drawPanel) throws IOException, ClassNotFoundException {
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

    private void createPlayer(){
        player = new Player("Marcin");
    }

    private void sendJoiningPing(){
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
                    ArrayList<?> list = (ArrayList<?>) packet;
                    Object firstElement = list.get(0);
                    firstElement.getClass();
                    if (firstElement instanceof Player) {
                        ArrayList<Player> playerList = (ArrayList<Player>) list;
                        slidePanel.updatePlayerLabel(playerList);
                    } else if (firstElement instanceof String) {
                        messageList = (ArrayList<String>) list;
                        chatPanel.updateChat(messageList);
                    }
                } else if (packet instanceof Message) {
                    Message message = (Message) packet;
                    messageList.add(message.getSender()+" "+message.getContent());
                    chatPanel.updateChat(messageList);
                    System.out.println("Mozna rysowac");





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
        out.writeObject(new Message(player.getName(),content));
    }
    public ObjectOutputStream getOut(){
        return out;
    }
}
