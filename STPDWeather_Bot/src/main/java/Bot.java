import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.util.HashMap;

public class Bot {

    private final WeatherModel model = new WeatherModel();
    private final Weather weather = new Weather();
    public final UserState userState = new UserState();

    private final HashMap<Long, String> lastMessages = new HashMap<>();
    private final HashMap<String, String> commands = new HashMap<>();

    Bot() {
        commands.put("/start",
                "Введи название города, в котором хочешь узнать погоду" + "\n" +
                "Для дополнительной информации вызови /help");
        commands.put("/help",
                "Привет! \u270B Я STPDWeatherBot \u2601" + "\n" +
                "Напиши название города, и я покажу погоду в нём!" + "\n" +
                "Также ты можешь сохранить четыре избранных города командой /set_favourite_cities " +
                "Ещё можешь вывести список этих городов командой /my_cities");
        commands.put("/my_cities",
                "Секундочку...");
        commands.put("/set_favourite_cities",
                "Напиши названия четырёх избранных городов" + "\n" +
                "\u270D Формат: \"1. Город 2. Город 3. Город 4. Город\"" + "\n" +
                "Пиши правильно \u261D \uD83D\uDE43");
    }

    public String getReplyToMessage(Message message) {

        if (Character.isDigit(message.getText().charAt(0)) &&
                lastMessages.get(message.getChatId()).equals("/set_favourite_cities")) {
            userState.setCities(message);
            return "Список успешно изменён \uD83D\uDC4D";
        }

        lastMessages.put(message.getChatId(), message.getText());

        if (lastMessages.get(message.getChatId()).equals("/my_cities")) {
            String[] cities = userState.getCities(message.getChatId().toString());
            return "\uD83C\uDF07 Твои избранные города: " + "\n" +
                    "1. " + cities[0] + "\n" + "2. " + cities[1] + "\n" +
                    "3. " + cities[2] + "\n" + "4. " + cities[3];
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
            reply = new StringBuilder("Не знаю такой команды... \uD83D\uDE22" + "\n\n" +
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
            return "Город не найден \uD83D\uDE1E";
        }

        return resultWeather[0];
    }
}
