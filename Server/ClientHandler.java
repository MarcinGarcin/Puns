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

    public ClientHandler(Socket socket, ArrayList<Player> players) throws IOException, ClassNotFoundException {
        this.clientSocket = socket;
        this.players = players;

    }

    public void run() {
        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            Object object = in.readObject();
            if(object instanceof Player) {
                Player player = (Player) object;
                System.out.println("Client"+ player.getName()+" connected");
                players.add(player);
            }
            if(players.size()>0){
                out.writeObject(players);
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
