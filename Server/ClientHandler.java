import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ArrayList<Player> players;

    public ClientHandler(Socket socket, ArrayList<Player> players) throws IOException, IOException {
        this.clientSocket = socket;
        this.players = players;
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
        sendUpdatedPlayerList();
    }

    public void run() {
        try {
            Object object = in.readObject();
            System.out.println(object.getClass());
            if (object instanceof Player) {
                Player player = (Player) object;
                System.out.println("Client " + player.getName() + " connected");
                synchronized (players) {
                    players.add(player);
                    for(Player p : players) {
                        System.out.println(p.getName());
                    }
                }
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

    private void sendUpdatedPlayerList() throws IOException {
        synchronized (players) {
            out.writeObject(players);
            out.reset();
        }
    }

}