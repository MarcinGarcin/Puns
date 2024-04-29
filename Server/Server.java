import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;


public class Server {
    private ServerSocket serverSocket;
    private ArrayList<Player> players = new ArrayList<Player>();



    public void start(int port) throws IOException, ClassNotFoundException {
        serverSocket = new ServerSocket(port);
        while (true){
            new ClientHandler(serverSocket.accept(),players).start();
        }

    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Server server = new Server();
        server.start(8888);
    }
}

