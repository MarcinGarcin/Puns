import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

class ChatPanel extends JPanel {
    private int width;
    private int height;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;
    private Color grey = new Color(51, 51, 51);
    private Color darkerGrey = new Color(40, 40, 40);
    private Color borderColor = new Color(252,222,6);
    private GameHandler gh;


    public ChatPanel(int width, int height, GameHandler gh) {
        this.width = width;
        this.height = height;
        this.gh = gh;
        setupChatPanel();


    }
    private void setupChatPanel(){
        setBounds(15,5,width,height);
        setBorder(new LineBorder(borderColor));
        chatArea = new JTextArea();
        chatArea.setBounds(5,5,width-10,height-50);
        chatArea.setEditable(false);
        chatArea.setBackground(darkerGrey);
        chatArea.setBorder(new LineBorder(borderColor));


        messageField = new JTextField();
        messageField.setBackground(darkerGrey);
        messageField.setBounds(5,height-40,width-70,35);
        messageField.setForeground(Color.red);
        messageField.setBorder(new LineBorder(borderColor));

        sendButton = new JButton("Send");
        sendButton.setBackground(darkerGrey);
        sendButton.setBorder(new LineBorder(borderColor));
        sendButton.setBounds(width-60,height-40,55,35);
        sendButton.setForeground(borderColor);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    gh.sendMessage(messageField.getText());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        add(chatArea);
        add(messageField);
        add(sendButton);


    }




}
