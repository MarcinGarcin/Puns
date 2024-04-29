import java.io.IOException;
import java.net.ServerSocket;


public class Server {
    private ServerSocket serverSocket;


    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true){
            new ClientHandler(serverSocket.accept()).start();
            System.out.println("new client connected");
        }

    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start(8888);
    }
}

