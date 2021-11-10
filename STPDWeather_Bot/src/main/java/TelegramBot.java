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
    private final BotReply botReply;

    public TelegramBot(Bot bot, BotReply reply, String token, String username) {
        telegramBot = bot;
        botToken = token;
        botUsername = username;
        botReply = reply;
    }

    public String getBotToken() { return botToken; }

    public String getBotUsername() { return botUsername; }

    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();

        if (message.hasText()) {
            botReply.message = telegramBot.getReplyToMessage(message.getText(), message.getChatId());
            botReply.keyboardRows = botReply.createKeyboard(telegramBot, message.getChatId());

            sendMessage(message.getChatId(), botReply);
        }
    }

    private void sendMessage(Long chatId, BotReply reply) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText(reply.message);
        createKeyboard(sendMessage, reply.keyboardRows);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void createKeyboard(SendMessage sendMessage, ArrayList<KeyboardRow> keyboardRows) {
        ReplyKeyboardMarkup citiesKeyboard = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(citiesKeyboard);

        citiesKeyboard.setSelective(true);
        citiesKeyboard.setResizeKeyboard(true);
        citiesKeyboard.setOneTimeKeyboard(false);

        citiesKeyboard.setKeyboard(keyboardRows);
    }
}
