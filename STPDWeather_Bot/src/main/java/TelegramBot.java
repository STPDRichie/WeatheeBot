import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class TelegramBot extends TelegramLongPollingBot {

    Bot telegramBot = new Bot();

    public String getBotToken() {
        return System.getenv("WEATHERBOT_TOKEN");
    }

    public String getBotUsername() {
        return System.getenv("WEATHERBOT_USERNAME");
    }

    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();

        if (message.hasText()) {

            String reply = telegramBot.getReplyToMessage(message.getText(), message.getChatId());

            sendMessage(message.getChatId(), reply);
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText(text);
        createKeyboard(sendMessage);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    
    private void createKeyboard(SendMessage sendMessage) {
        ReplyKeyboardMarkup citiesKeyboard = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(citiesKeyboard);

        citiesKeyboard.setSelective(true);
        citiesKeyboard.setResizeKeyboard(true);
        citiesKeyboard.setOneTimeKeyboard(false);

        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        String[] cities = telegramBot.userStateRepo.getCities(sendMessage.getChatId());
        row1.add(cities[0]);
        row1.add(cities[1]);
        row2.add(cities[2]);
        row2.add(cities[3]);

        keyboardRows.add(row1);
        keyboardRows.add(row2);

        citiesKeyboard.setKeyboard(keyboardRows);
    }
}
