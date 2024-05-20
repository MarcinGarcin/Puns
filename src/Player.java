import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 1;
    private String name;
    public Boolean isReady = false;
    public Boolean isDrawing = false;
    private int score = 0;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setReady(Boolean isReady) {
        this.isReady = isReady;
    }
    public void setDrawing(Boolean isDrawing) {
        this.isDrawing = isDrawing;
    }
    public int getScore() {
        return this.score;
    }
    public boolean getReady(){
        return this.isReady;
    }
    public boolean getDrawing(){
        return this.isDrawing;
    }
}

