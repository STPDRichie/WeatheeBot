import java.io.IOException;
import java.util.HashMap;

public class Bot {

    private final WeatherGetter weatherGetter;
    public UserStateRepo userStateRepo;

    private final HashMap<String, String> commands = new HashMap<>();

    public Bot(WeatherGetter wGetter, UserStateRepo uStateRepo) {
        weatherGetter = wGetter;
        userStateRepo = uStateRepo;

        commands.put("/start",
                "Введи название города, в котором хочешь узнать погоду" + "\n" +
                        "Для дополнительной информации вызови /help");
        commands.put("/help",
                "Привет! \u270B Я STPDWeatherBot \u2601" + "\n" +
                        "Напиши название города, и я покажу погоду в нём!" + "\n" +
                        "Также ты можешь сохранить четыре избранных города командой " + "/set_favourite_cities" + "\n" +
                        "Ещё можешь вывести список этих городов командой " + "/my_favourite_cities");
        commands.put("/my_favourite_cities",
                "Секундочку...");
        commands.put("/set_favourite_cities",
                "Напиши названия четырёх избранных городов" + "\n" +
                        "\u270D Формат такой:" + "\n" +
                        "1. Город 1 \n" +
                        "2. Город 2 \n" +
                        "3. Город 3 \n" +
                        "4. Город 4 \n" + "\n" +
                        "Можешь писать в любом порядке и количестве, главное не дальше четвёртого города" + "\n" +
                        "Пиши правильно \u261D \uD83D\uDE43");
    }

    public String getReplyToMessage(String text, Long chatId) {

        if (Character.isDigit(text.charAt(0)) &&
                userStateRepo.lastMessage.get(chatId.toString()).equals("/set_favourite_cities")) {
            if (userStateRepo.setFavouriteCities(text, chatId.toString())) {
                return "Список успешно изменён \uD83D\uDC4D";
            }
            return "Ошибка произошла... \uD83D\uDE22";
        }

        userStateRepo.lastMessage.put(chatId.toString(), text);

        if (userStateRepo.lastMessage.get(chatId.toString()).equals("/my_favourite_cities")) {
            String[] cities = userStateRepo.getFavouriteCities(chatId.toString());
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

    public static String[] parseCitiesSettingText(String[] cities, String text) {
        String[] pairs = text.split("\\s?\\n\\s?");
        if (pairs.length > 4) {
            return null;
        }

        for (String pair : pairs) {
            cities[Integer.parseInt(pair.split(". ")[0]) - 1] = pair.split(". ")[1];
        }
        return cities;
    }

    public String getReplyToCommand(String commandText) {
        StringBuilder reply;

        if (commands.containsKey(commandText)) {
            reply = new StringBuilder(commands.get(commandText));
        } else {
            reply = new StringBuilder("Не знаю такой команды... \uD83D\uDE22" + "\n\n" +
                    "Список команд:");
            for (String command : commands.keySet()) {
                reply.append("\n").append(command);
            }
        }

        return reply.toString();
    }

    public String getWeather(String cityName) {
        WeatherModel model;

        try {
            model = weatherGetter.getWeather(cityName);
        } catch (IOException e) {
            return "Не знаю такого города \uD83D\uDE1E";
        }

        return model.getFormatInfo();
    }
}
