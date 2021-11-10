import org.apache.http.client.HttpResponseException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class TelegramBot extends TelegramLongPollingBot {

    private final Bot telegramBot;
    private final String botToken;
    private final String botUsername;

    public TelegramBot(Bot bot, String token, String username) {
        telegramBot = bot;
        botToken = token;
        botUsername = username;
    }

    public String getBotToken() { return botToken; }

    public String getBotUsername() { return botUsername; }

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

        String[] cities = telegramBot.userStateRepo.getFavouriteCities(sendMessage.getChatId());
        row1.add(cities[0]);
        row1.add(cities[1]);
        row2.add(cities[2]);
        row2.add(cities[3]);

        keyboardRows.add(row1);
        keyboardRows.add(row2);

        citiesKeyboard.setKeyboard(keyboardRows);
    }
}
