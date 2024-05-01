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
    private ArrayList<ClientHandler> clientHandlers;

    public ClientHandler(Socket socket, ArrayList<Player> players, ArrayList<ClientHandler> clientHandlers) throws IOException, IOException {
        this.clientSocket = socket;
        this.players = players;
        this.clientHandlers = clientHandlers;
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
                }
                broadcastPlayerList();
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

    private void broadcastPlayerList() {
        synchronized (clientHandlers) {
            for (ClientHandler clientHandler : clientHandlers) {
                try {
                    clientHandler.sendUpdatedPlayerList();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void sendData(Object data) {
        try {
            out.writeObject(data);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}