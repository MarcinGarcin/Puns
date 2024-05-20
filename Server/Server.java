import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server {
    private List<ClientHandler> clients = new ArrayList<>();
    public List<Player> playerList = new ArrayList<>();
    private List<String> words = List.of("Cat", "Dog", "House", "Car", "Tree", "Sun", "Star", "Moon", "Computer", "Phone");



    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame() {
        if (playerList.size() >= 2) {
            for (int i = 0; i < clients.size(); i++) {
                Player player = playerList.get(i);
                String word = pickRandomWord();
                player.setDrawing(true);
                clients.get(i).sendData(clients.get(i).player);
                clients.get(i).sendData(new Message("Server: ", "You have to draw "+ word));
                for (int j = 0; j < clients.size(); j++) {
                    if (j != i) {
                        Player otherPlayer = playerList.get(j);
                        otherPlayer.setDrawing(false);
                        clients.get(j).sendData(clients.get(j).player);
                        clients.get(j).sendData(new Message("Server", "Guess the word!"));
                    }
                }
                while (true){

                }
            }
        } else {
            broadcastData(new Message("Server", "Not enough players to start the game"));
        }
    }

    private String pickRandomWord() {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    public synchronized void addPlayer(Player player) {
        playerList.add(player);
        broadcastData(playerList);
    }

    public synchronized void broadcastData(Object data) {
        for (ClientHandler client : clients) {
            client.sendData(data);
        }
    }

    public static void main(String[] args) {
        new Server().start(12345);
    }
}
