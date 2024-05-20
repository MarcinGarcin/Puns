import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private List<ClientHandler> clients = new ArrayList<>();
    private List<Player> playerList = new ArrayList<>();
    private List<String> messageList = new ArrayList<>();

    public static void main(String[] args) {
        new Server().start(12345);
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPlayer(Player player) {
        playerList.add(player);
        broadcastData(playerList);
    }

    public synchronized void broadcastData(Object data) {
        for (ClientHandler client : clients) {
            client.sendData(data);
        }
    }
}
