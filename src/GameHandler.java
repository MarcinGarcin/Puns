import java.io.*;
import java.util.ArrayList;

public class GameHandler implements Runnable {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private SlidePanel slidePanel;
    private Player player;

    public GameHandler(ObjectInputStream in, ObjectOutputStream out, SlidePanel slidePanel,Player player) {
        this.in = in;
        this.slidePanel = slidePanel;
        this.out = out;
        this.player = player;
    }

    @Override
    public void run() {
        while(true){
            try {
                out.writeObject(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
