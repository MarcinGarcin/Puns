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
                if(clientSocket.getInputStream()!=null) {
                    ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                    Player player = (Player) objectInputStream.readObject();

                    System.out.println("Player connected: " + player.getName());

                    players.add(player);
                }

                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
