import java.util.ArrayList;

public class BotReply {
    String message;
    ArrayList<ArrayList<String>> keyboard;

    public BotReply(String msg, ArrayList<ArrayList<String>> keyBoard) {
        message = msg;
        keyboard = keyBoard;
    }
}
