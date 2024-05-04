import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
    private ArrayList<Player> players = new ArrayList<Player>();
    private ClientHandler handler;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            while (!serverSocket.isClosed()) {
                try {
                    handler = new ClientHandler(serverSocket.accept());
                    handler.run();

                    clients.add(handler);
                    players.add(handler.getPlayer());
                    for(ClientHandler c : clients) {
                        c.sendUpdatedPlayerList(players);
                    }


                } catch (IOException e) {
                    System.out.println("Error accepting client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
        } finally {
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing server: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start(32768);
    }
}
