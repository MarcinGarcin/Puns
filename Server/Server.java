import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private List<ClientHandler> clients = new ArrayList<>();
    private  List<Player> playerList = new ArrayList<>(); // Corrected declaration

    public static void main(String[] args) {
        new Server().start(12345);
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);
                ClientHandler clientHandler = new ClientHandler(clientSocket, this, playerList);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void broadcastPlayerList() {
        for (ClientHandler client : clients) {
            try {
                client.sendPlayerList(playerList);
                System.out.println(playerList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void addPlayer(Player player) {
        playerList.add(player);
        broadcastPlayerList();
    }
}
