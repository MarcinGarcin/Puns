import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
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
}

