import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private ArrayList<Player> players;
    private ArrayList<Socket> sockets;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            executorService = Executors.newFixedThreadPool(10);
            players = new ArrayList<>();
            sockets = new ArrayList<>();
            System.out.println("Server started. Waiting for clients...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.execute(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            Object object = in.readObject();

            if (object instanceof Player) {
                Player player = (Player) object;
                System.out.println(player.getName() + " has joined the game.");

                synchronized (players) {
                    players.add(player);
                }
                synchronized (sockets){
                    sockets.add(clientSocket);
                }
                for(Player p : players) {
                    System.out.println(p.getName());
                }
            } else {
                System.out.println("Received object is not a Player object.");
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
