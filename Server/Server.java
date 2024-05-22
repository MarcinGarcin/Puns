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
    private String currentWord;
    private int currentDrawerIndex = 0; // Dodanie indeksu rysującego gracza

    public void start(int port){
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
            currentWord = pickRandomWord();

            for (int i = 0; i < clients.size(); i++) {
                Player player = playerList.get(i);
                player.setDrawing(i == currentDrawerIndex);
                clients.get(i).sendData(player);



                if (i == currentDrawerIndex) {
                    clients.get(i).sendData(new Message("Server", "You have to draw: " + currentWord));
                } else {
                    clients.get(i).sendData(new Message("Server: ",playerList.get(currentDrawerIndex).getName()+" is drawing,Guess the word"));
                }
            }
            broadcastData(new Message("Server: ","New round"));


            currentDrawerIndex = (currentDrawerIndex + 1) % playerList.size();

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

    public synchronized void broadcastImage(DrawData drawData) {
        for (ClientHandler client : clients) {
            if (!client.player.isDrawing) {
                client.sendData(drawData);
            }
        }
    }

    public synchronized void checkGuess(String guess, Player player) {
        if (guess.equalsIgnoreCase(currentWord)) {
            broadcastData(new Message("Server", player.getName() + " guessed the word! The word was: " + currentWord));
            startGame();
        }
    }

    public static void main(String[] args) {
        new Server().start(12345);
    }
}
