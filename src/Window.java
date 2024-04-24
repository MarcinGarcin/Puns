import javax.swing.*;

public class Window extends JFrame {
    public Window() {
        setupWindow();
    }
    public void setupWindow(){
        setSize(1280,720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
