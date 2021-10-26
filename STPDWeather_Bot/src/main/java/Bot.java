import java.io.IOException;
import java.util.HashMap;

public class Bot {

    private final WeatherModel model = new WeatherModel();
    private final Weather weather = new Weather();
    public final UserStateRepo userStateRepo = new UserStateRepo();

    private final HashMap<String, String> commands = new HashMap<>();

    Bot() {
        commands.put("/start",
                "Введи название города, в котором хочешь узнать погоду" + "\n" +
                "Для дополнительной информации вызови /help");
        commands.put("/help",
                "Привет! \u270B Я STPDWeatherBot \u2601" + "\n" +
                "Напиши название города, и я покажу погоду в нём!" + "\n" +
                "Также ты можешь сохранить четыре избранных города командой /set_favourite_cities" + "\n" +
                "Ещё можешь вывести список этих городов командой /my_cities");
        commands.put("/my_cities",
                "Секундочку...");
        commands.put("/set_favourite_cities",
                "Напиши названия четырёх избранных городов" + "\n" +
                "\u270D Формат: \"1. Город 2. Город 3. Город 4. Город\"" + "\n" +
                "Пиши правильно \u261D \uD83D\uDE43");
    }

    public String getReplyToMessage(String text, Long chatId) {

        if (Character.isDigit(text.charAt(0)) &&
                userStateRepo.lastMessages.get(chatId).equals("/set_favourite_cities")) {
            userStateRepo.setCities(text, chatId.toString());
            return "Список успешно изменён \uD83D\uDC4D";
        }

        userStateRepo.lastMessages.put(chatId, text);

        if (userStateRepo.lastMessages.get(chatId).equals("/my_cities")) {
            String[] cities = userStateRepo.getCities(chatId.toString());
            return "\uD83C\uDF07 Твои избранные города: " + "\n" +
                    "1. " + cities[0] + "\n" + "2. " + cities[1] + "\n" +
                    "3. " + cities[2] + "\n" + "4. " + cities[3];
        }


        if (text.indexOf('/') == 0) {
            return getReplyToCommand(text);
        } else {
            return getWeather(text);
        }

    }

    public String getReplyToCommand(String text) {
        StringBuilder reply;

        if (commands.containsKey(text)) {
            reply = new StringBuilder(commands.get(text));
        } else {
            reply = new StringBuilder("Не знаю такой команды... \uD83D\uDE22" + "\n\n" +
                    "Список команд:");
            for (String command : commands.keySet()) {
                reply.append("\n").append(command);
            }
        }

        return reply.toString();
    }

    public String getWeather(String text) {
        String[] resultWeather;

        try {
            resultWeather = weather.getWeather(text, model);
        } catch (IOException e) {
            return "Город не найден \uD83D\uDE1E";
        }

        return resultWeather[0];
    }
}
