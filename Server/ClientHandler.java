import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Server server;
    public Player player;

    public ClientHandler(Socket socket, Server server) throws IOException {
        this.clientSocket = socket;
        this.server = server;
        this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        this.in = new ObjectInputStream(clientSocket.getInputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {
                Object obj = in.readObject();
                if (obj instanceof Player ) {
                    player = (Player) obj;
                    server.addPlayer(player);
                    System.out.println(player.getName());
                    server.broadcastData(server.playerList);
                    server.broadcastData(new Message("Server: ", player.getName() + " has joined the game!"));
                } else if (obj instanceof Message message) {
                    server.broadcastData(message);
                    System.out.println(message.getContent());
                    if (message.getContent().equals("/rdy")&&!(player.getReady())) {
                        server.broadcastData(new Message("Server: ", player.getName() + " is ready"));
                        player.setReady(true);
                        server.startGame();
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isPlayerReady() {
        return player.getReady();
    }

    public void sendData(Object data) {
        try {
            out.reset();
            out.writeObject(data);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
