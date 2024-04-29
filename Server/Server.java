import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started. Waiting for clients...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        ArrayList<Player> players = new ArrayList<>();

        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                Object object = in.readObject();

                if (object instanceof Player) {
                    Player player = (Player) object;
                    System.out.println(player.getName() + " has joined the game.");

                    synchronized (players) {
                        players.add(player);
                    }
                    Thread thread = new Thread(new ClientHandler(clientSocket, player, players));
                    thread.start();
                } else {
                    System.out.println("Received object is not a Player object.");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Server server = new Server(8888);
        server.startServer();
    }
}




