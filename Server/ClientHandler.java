import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final Server server;
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
                if (obj instanceof Player) {
                    player = (Player) obj;
                    server.addPlayer(player);
                    System.out.println(player.getName());
                    server.broadcastData(server.playerList);
                    server.broadcastData(new Message("Server", player.getName() + " has joined the game!"));
                } else if (obj instanceof Message message) {
                    server.broadcastData(message);
                    System.out.println(message.getContent());
                    if (message.getContent().equals("/rdy") && !player.getReady()) {
                        server.broadcastData(new Message("Server", player.getName() + " is ready"));
                        player.setReady(true);
                        server.startGame();
                    } else if (!player.isDrawing) {
                        server.checkGuess(message.getContent(), player);
                    }
                } else if (obj instanceof DrawData drawData) {
                    server.broadcastImage(drawData);
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
