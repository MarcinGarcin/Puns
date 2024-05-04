import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class GameHandler{
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private Player player;
    private String ip;
    private Object object;
    private SlidePanel slidePanel;

    public GameHandler(String ip, SlidePanel slidePanel) throws IOException, ClassNotFoundException {
        this.ip = ip;
        this.slidePanel = slidePanel;
        createPlayer();
        setupServerConnection();
        sendJoiningPing();
        new Thread(this::listenForArrayList).start();
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
    private void listenForArrayList() {
        try {
            while (true) {
                Object object = in.readObject();
                if (object instanceof ArrayList<?>) {
                    ArrayList<Player> list = (ArrayList<Player>) object;
                    for (Player p : list) {
                    }
                    slidePanel.updatePlayerLabel(list);
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


}
