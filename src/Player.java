import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 1;
    private String name;
    public Boolean isReady = false;
    private int score = 0;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public int getScore() {
        return score;
    }

    public void setIsReady(Boolean isReady) {
        this.isReady = isReady;
    }
    public boolean isReady() {
        return isReady;
    }
}

