import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private List<Player> playerList;
    private Server server;
    private int currentPlayerIndex = 0;
    private Player player;


    public ClientHandler(Socket socket, Server server, List<Player> playerList) throws IOException {
        this.clientSocket = socket;
        this.playerList = playerList;
        this.server = server;
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object obj = in.readObject();
                if (obj instanceof Player) {
                    player = (Player) obj;
                    server.addPlayer(player);
                    server.chatHandler(new Message("Server: ",player.getName() + " joined"));
                }
                else if (obj instanceof Message){
                    Message message = (Message) obj;
                    server.chatHandler(message);
                    if(message.getContent().contains("/rdy")){
                        server.chatHandler(new Message("Server: ",player.getName() + " is ready"));
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public <T> void sendPacket(T data) throws IOException {
        out.reset();
        out.writeObject(data);
        out.flush();
    }
    public void chooseNextDrawer() {
        if (!playerList.isEmpty()) {
            Player currentPlayer = playerList.get(currentPlayerIndex);


            broadcastPacket("Player " + currentPlayer.getName() + " is now drawing.");

            currentPlayerIndex = (currentPlayerIndex + 1) % playerList.size();
        }
    }
}