/*import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            while (true) {
                Object receivedObject = inputStream.readObject();

                for (ObjectOutputStream outputStream : outputStreams) {
                    outputStream.writeObject(receivedObject);
                    outputStream.flush();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                outputStreams.remove(clientSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
*/