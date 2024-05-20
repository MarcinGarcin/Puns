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
    private Color borderColor = new Color(252, 222, 6);
    private JScrollPane scrollPane;
    private ArrayList<String> chatMessages = new ArrayList<>();
    private GameHandler gh;

    public ChatPanel(int width, int height) {
        this.width = width;
        this.height = height;
        setupChatPanel();
    }

    private void setupChatPanel() {
        setBounds(15, 5, width, height);
        setBorder(new LineBorder(borderColor));
        setBackground(grey);
        setLayout(null);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setBackground(darkerGrey);
        chatArea.setBorder(new LineBorder(borderColor));

        scrollPane = new JScrollPane(chatArea);
        scrollPane.setBounds(5, 5, width - 10, height - 50);
        scrollPane.setBorder(new LineBorder(borderColor));
        scrollPane.getVerticalScrollBar().setBackground(darkerGrey);
        scrollPane.getHorizontalScrollBar().setBackground(darkerGrey);

        messageField = new JTextField();
        messageField.setBackground(darkerGrey);
        messageField.setBounds(5, height - 40, width - 70, 35);
        messageField.setForeground(Color.WHITE);
        messageField.setBorder(new LineBorder(borderColor));

        sendButton = new JButton("Send");
        sendButton.setBackground(darkerGrey);
        sendButton.setBorder(new LineBorder(borderColor));
        sendButton.setBounds(width - 60, height - 40, 55, 35);
        sendButton.setForeground(borderColor);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        add(scrollPane);
        add(messageField);
        add(sendButton);
    }

    private void displayChat() {
        StringBuilder chatContent = new StringBuilder();
        for (String message : this.chatMessages) {
            chatContent.append(message).append("\n");
        }
        chatArea.setText(chatContent.toString());
        chatArea.setForeground(borderColor);
    }

    public void updateChat(ArrayList<String> newMessages) {
        this.chatMessages = newMessages;
        displayChat();
    }

    public void setGameHandler(GameHandler gh) {
        this.gh = gh;
    }

    private void sendMessage() {
        try {
            gh.sendMessage(messageField.getText());
            messageField.setText("");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
