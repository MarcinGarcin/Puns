import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) {
        final int PORT = 8888;
        ArrayList<Player> players = new ArrayList<>();

        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                Object object = objectInputStream.readObject();

                if (object instanceof Player) {
                    Player player = (Player) object;
                    System.out.println("Player connected: " + player.getName());
                    players.add(player);
                } else {
                    System.out.println("Received object is not a Player object.");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
    }
}
