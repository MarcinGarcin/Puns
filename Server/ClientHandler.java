import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    public Player player;

    public ClientHandler(Socket socket) throws IOException, IOException {
        this.clientSocket = socket;
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    public void run() {
        try {
            Object object = in.readObject();
            if (object instanceof Player) {
                player = (Player) object;

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendUpdatedPlayerList(ArrayList<Player> players) throws IOException {
        out.writeObject(players);
        out.reset();

    }
    public Player getPlayer(){
        return player;
    }

}