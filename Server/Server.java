import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();
    private ClientHandler clientHandler;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);

        while (true) {
            try {
                clientHandler= new ClientHandler(serverSocket.accept(), players, clientHandlers);
                clientHandler.start();
                clientHandlers.add(clientHandler);
                broadcast(players);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void broadcast(Object data) {
        for (ClientHandler handler : clientHandlers) {
            handler.sendData(data);
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start(8888);
    }
}