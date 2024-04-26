import java.awt.image.BufferedImage;

public class Player {
    private String name;
    private BufferedImage avatarImage;
    private Boolean isDrawing = false;
    private int score = 0;


    public Player(String name){
        this.name = name;
    }
    public void changeDrawer(){
        isDrawing = !isDrawing;
    }
    public BufferedImage getAvatar(){
        return avatarImage;
    }
    public String getName(){
        return name;
    }


}
