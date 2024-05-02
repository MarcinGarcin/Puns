
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private List<Socket> clients = Collections.synchronizedList(new ArrayList<>());
    private List<Player> players = Collections.synchronizedList(new ArrayList<>());

    public void start(int port) {
        try {

            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            while (true){
                Socket client = serverSocket.accept();
                clients.add(client);
                System.out.println("client connected");
                try (ObjectInputStream in = new ObjectInputStream(client.getInputStream())) {
                    Object object = in.readObject();
                    if(object instanceof Player){
                        players.add((Player) object);
                        for(Socket socket : clients){
                            resendPlayerList(socket);
                            System.out.println("wyslano");
                        }
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("Error reading object from client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error in server: " + e.getMessage());
        }
    }

    private void resendPlayerList(Socket client) {
        try (ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream())) {
            out.writeObject(players);
        } catch (IOException e) {
            System.out.println("Error sending player list to client: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start(8888);
    }
}
