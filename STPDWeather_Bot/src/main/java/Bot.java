import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    private static final String TOKEN = "2033333636:AAFARfll7qSN0QdFopeIRE5hRX740o_76n4";
    private static final String USERNAME = "STPDWeather_Bot";

    public String getBotToken() {
        return TOKEN;
    }

    public String getBotUsername() {
        return USERNAME;
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (message != null && message.hasText()) {

            switch (message.getText()) {
                case "/help":
                    sendMessage(message, "Я WeatherBot. \n" + "Введи название города, и я покажу погоду в нём.");
                    break;

                case "/start":
                    sendMessage(message, "Введи название города, в котором хочешь узнать погоду.");
                    break;

                case "Екатеринбург":
                    sendMessage(message,
                            "Город: " + message.getText() + "\n" +
                                    "Температура: 5 C°" + "\n" +
                                    "Влажность: 75%" + "\n" +
                                    "Скорость ветра: 3.0 м/с" + "\n" +
                                    "Но это не точно");
                    break;

                default:
                    if (message.getText().indexOf('/') == 0) {
                        sendMessage(message, "Неизвестная команда");
                        break;
                    } else {
                        sendMessage(message, "Не знаю такого города. Пока я могу показать погоду только в одном (((");
                    }

            }
        }
    }

    public void sendMessage(Message message, String text) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(message.getChatId())
                .setText(text);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
