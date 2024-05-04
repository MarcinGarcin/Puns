import java.io.*;
import java.net.Socket;

public class GameHandler implements Runnable {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    private Player player;
    private String ip;

    public GameHandler(String ip) {
        this.ip = ip;
    }



    @Override
    public void run() {
        createPlayer();
        setupServerConnection();
        sendJoiningPing();
    }
    private void setupServerConnection() {
        try {
            socket = new Socket(ip, 32768);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.err.println("Failed to connect to the server: " + e.getMessage());
        } finally {
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Failed to close the socket: " + e.getMessage());
                }
            }
            // Close the input/output streams as well
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
}
