import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Player player;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    ArrayList<Player> players;

    public ClientHandler(Socket clientSocket, Player player, ArrayList<Player> players) {
        this.clientSocket = clientSocket;
        this.player = player;
        this.players = players;
        try {
            this.out = new ObjectOutputStream(clientSocket.getOutputStream());
            this.in = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
    }
}

