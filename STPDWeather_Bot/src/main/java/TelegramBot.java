import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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

            String reply = telegramBot.getReplyToMessage(message);

            sendMessage(message.getChatId(), reply);
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText(text);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
