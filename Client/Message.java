import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 2L;
    private String sender;
    private String content;
    private String guess;
    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }
    public Message(String sender, String content,String guess) {
        this.sender = sender;
        this.content = content;
        this.guess = guess;
    }
    public String getSender() {
        return sender;
    }
    public String getContent() {
        return content;
    }
}
