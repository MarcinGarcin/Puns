import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private ServerSocket server;
    private ArrayList<ClientHandler> clients = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();
    private ExecutorService pool;
    private int port = 32768;

    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            pool = Executors.newCachedThreadPool();
            System.out.println("Server started on port " + port);
            while (true) {
                Socket client = server.accept();
                ClientHandler handler = new ClientHandler(client);
                clients.add(handler);
                pool.execute(handler);
                players.add(handler.getPlayer());

                broadcastPlayerList();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void broadcastPlayerList() {
        for (ClientHandler client : clients) {
            System.out.println("wyslano");
            try {
                client.sendPlayerList(players);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}