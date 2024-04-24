import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private Color bg = new Color(51,51,51);
    public Window() {
        setupWindow();
    }
    public void setupWindow(){
        setSize(1280,720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setBackground(bg);
    }
}
