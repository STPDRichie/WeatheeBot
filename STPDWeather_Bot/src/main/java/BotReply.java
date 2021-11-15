import java.util.ArrayList;

public class BotReply {
    String message;
    ArrayList<ArrayList<String>> keyboard;

    public BotReply(String msg, ArrayList<ArrayList<String>> keys) {
        message = msg;
        keyboard = keys;
    }

    // отвязать от телеграмма, сделав свой KeynboardRow
    // перенести это в Bot
    public ArrayList<ArrayList<String>> createKeyboard(UserStateRepo userStateRepo, Long... chatId) {

        keyboard = new ArrayList<>();
        ArrayList<String> row1 = new ArrayList<>();
        ArrayList<String> row2 = new ArrayList<>();

        String[] cities = new UserState().defaultCities;
        if (chatId.length > 0) {
            cities = userStateRepo.getFavouriteCities(chatId[0].toString());
        }
        row1.add(cities[0]);
        row1.add(cities[1]);
        row2.add(cities[2]);
        row2.add(cities[3]);

        keyboard.add(row1);
        keyboard.add(row2);

        return keyboard;
    }

//    public
}
