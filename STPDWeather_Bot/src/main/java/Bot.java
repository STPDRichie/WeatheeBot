import java.util.HashMap;
import java.util.Map;

public class Bot {

    public Map<String, String> commands = new HashMap<>();

    public Bot() {
        commands.put("/start",
                "Введи название города, в котором хочешь узнать погоду.");
        commands.put("/help",
                "Я WeatherBot." + "\n" + "Введи название города, и я покажу погоду в нём.");
    }

    public String getReplyToMessage(String text) {

        if (text.indexOf('/') == 0) {
            return getReplyToCommand(text);
        } else {
            return getWeather(text);
        }

    }

    public String getReplyToCommand(String command) {
        StringBuilder reply;

        if (commands.containsKey(command)) {
            reply = new StringBuilder(commands.get(command));
        } else {
            reply = new StringBuilder("Неизвестная команда..." + "\n" +
                    "Список команд:" + "\n");
            for (String c : commands.keySet()) {
                reply.append("\n").append(c);
            }
        }

        return reply.toString();
    }

    public String getWeather(String city) {
        String reply;

        switch (city) {
            case "Екатеринбург":
                reply = "Город: " + city + "\n" +
                        "Температура: 5 C°" + "\n" +
                        "Влажность: 75%" + "\n" +
                        "Скорость ветра: 3.0 м/с" + "\n" +
                        "Но это не точно";
                break;

            case "Челябинск":
                reply = "Город: " + city + "\n" +
                        "Температура: 7 C°" + "\n" +
                        "Влажность: 80%" + "\n" +
                        "Скорость ветра: 6.0 м/с" + "\n" +
                        "Но это не точно";
                break;

            default:
                reply = "Не знаю такого города. Пока я могу показать погоду только в двух (((";
        }

        return reply;
    }
}
