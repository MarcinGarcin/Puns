import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server {
    private List<ClientHandler> clients = new ArrayList<>();
    private List<Player> playerList = new ArrayList<>();
    private List<String> messageList = new ArrayList<>();
    private Random random = new Random();
    private int currentDrawerIndex = 0;

    public static void main(String[] args) {
        new Server().start(12345);
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            messageList.add("If you are ready write /rdy");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);
                ClientHandler clientHandler = new ClientHandler(clientSocket, this, playerList);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized <T> void broadcastPacket(T object) {
        for (ClientHandler client : clients) {
            try {
                client.sendPacket(object);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addPlayer(Player player) {
        playerList.add(player);
        broadcastPacket(playerList);
    }

    public void chatHandler(Message message) {
        messageList.add(message.getSender() + ": " + message.getContent());
        broadcastPacket(messageList);
    }

    public void startNewRound() {
        if (playerList.size() < 1) {
            //TODO poprawic to
            chatHandler(new Message("Server: ", "Not enough players to start a new round."));
            return;
        }

        Player drawer = playerList.get(0);
        String wordToDraw = chooseWord();

        for (ClientHandler client : clients) {
            if (client.getPlayer().equals(drawer)) {
                try {
                    client.sendPacket(new Message("Server: ", wordToDraw));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    client.sendPacket("NEW_ROUND: " + drawer.getName() + " is drawing.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        currentDrawerIndex = (currentDrawerIndex + 1) % playerList.size();
    }

    private String chooseWord() {
        List<String> words = List.of("apple", "house", "car", "dog");
        return words.get(random.nextInt(words.size()));
    }
}
