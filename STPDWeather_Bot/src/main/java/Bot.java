import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.util.HashMap;

public class Bot {

    private final WeatherModel model = new WeatherModel();
    private final Weather weather = new Weather();
    private final UserState userState = new UserState();

    private final HashMap<Long, String> lastMessages = new HashMap<>();
    private final HashMap<String, String> commands = new HashMap<>();

    Bot() {
        commands.put("/start",
                "Введи название города, в котором хочешь узнать погоду");
        commands.put("/help",
                "Привет! Я STPDWeatherBot" + "\n" + "Напиши название города, и я покажу погоду в нём" + "\n" +
                "Также ты можешь сохранить четыре избранных города и получать погоду, просто нажав на кнопку");
        commands.put("/myCities",
                "Секундочку...");
        commands.put("/setFavouriteCities",
                "Напиши названия четырёх избранных городов" + "\n" +
                "Формат: \"1. Город 2. Город 3. Город 4. Город\"");
    }

    public String getReplyToMessage(Message message) {

        if (Character.isDigit(message.getText().charAt(0)) &&
                lastMessages.get(message.getChatId()).equals("/setFavouriteCities")) {
            userState.setCities(message);
            return "Список изменён";
        }

        lastMessages.put(message.getChatId(), message.getText());

        if (lastMessages.get(message.getChatId()).equals("/myCities")) {
            String[] cities = userState.getCities(message.getChatId().toString());
            return "Твои избранные города: " + "\n" +
                    cities[0] + "\n" + cities[1] + "\n" +
                    cities[2] + "\n" + cities[3];
        }


        if (message.getText().indexOf('/') == 0) {
            return getReplyToCommand(message);
        } else {
            return getWeather(message);
        }

    }

    public String getReplyToCommand(Message message) {
        StringBuilder reply;

        if (commands.containsKey(message.getText())) {
            reply = new StringBuilder(commands.get(message.getText()));
        } else {
            reply = new StringBuilder("Неизвестная команда..." + "\n\n" +
                    "Список команд:");
            for (String command : commands.keySet()) {
                reply.append("\n").append(command);
            }
        }

        return reply.toString();
    }

    public String getWeather(Message message) {
        String[] resultWeather;

        try {
            resultWeather = weather.getWeather(message.getText(), model);
        } catch (IOException e) {
            return "Город не найден(((";
        }

        return resultWeather[0];
    }
}
