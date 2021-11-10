import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public class BotReply {
    String message;
    ArrayList<KeyboardRow> keyboardRows;

    public ArrayList<KeyboardRow> createKeyboard(Bot telegramBot, Long chatId) {

        keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        String[] cities = telegramBot.userStateRepo.getFavouriteCities(chatId.toString());
        row1.add(cities[0]);
        row1.add(cities[1]);
        row2.add(cities[2]);
        row2.add(cities[3]);

        keyboardRows.add(row1);
        keyboardRows.add(row2);

        return keyboardRows;
    }
}
