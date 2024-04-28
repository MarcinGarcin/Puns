import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Player player;
    private ObjectOutputStream out;

    public ClientHandler(Socket clientSocket, Player player, ObjectInputStream in) {
        this.clientSocket = clientSocket;
        this.player = player;
        try {
            this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

