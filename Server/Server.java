import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
    private ArrayList<Player> players = new ArrayList<Player>();

    public void start(int port) {

        while (true) {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("client connected");
                System.out.println("Server started on port " + port);
                clients.add(new ClientHandler(serverSocket.accept(), players));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start(8888);
    }
}
