import org.apache.http.client.HttpResponseException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;

public class Bot {

    private final WeatherGetter weatherGetter;
    public UserStateRepo userStateRepo;
    private final BotReply botReply;

    private final HashMap<String, String> commands = new HashMap<>();

    private final DecimalFormat tempFormat = new DecimalFormat("0.00");

    public Bot(WeatherGetter wGetter, UserStateRepo repo, BotReply reply) {
        weatherGetter = wGetter;
        userStateRepo = repo;
        botReply = reply;
        botReply.keyboardRows = botReply.createKeyboard(userStateRepo);

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

    public BotReply getReplyToMessage(String text, Long chatId) {
//         возвращать каждый раз новый botReply
        UserState userState = userStateRepo.getUserState(chatId.toString());
        if (userState.dialogState == DialogState.WaitFavouriteCities) {
//            if (Character.isDigit(text.charAt(0)) &&
//                userStateRepo.lastMessage.get(chatId.toString()).equals("/set_favourite_cities")) {
            if (userStateRepo.setFavouriteCities(text, chatId.toString())) {
                botReply.message = "Список успешно изменён \uD83D\uDC4D";
            } else {
                botReply.message = "Ошибка произошла... \uD83D\uDE22";
            }
            botReply.keyboardRows = botReply.createKeyboard(userStateRepo, chatId);
            return botReply;
        }

        userStateRepo.putLastMessage(chatId.toString(), text);
        if (userStateRepo.getLastMessage(chatId.toString()).equals("/my_favourite_cities")) {
            String[] cities = userStateRepo.getFavouriteCities(chatId.toString());
            StringBuilder response = new StringBuilder("\uD83C\uDF07 Твои избранные города: ");
            for (int i = 0; i < 4; i++) {
                response.append("\n").append(i + 1).append(". ").append(cities[i]);
            }
            botReply.message = response.toString();
            return botReply;
        }

        if (text.indexOf('/') == 0) {
            botReply.message = getReplyToCommand(text);
        } else {
            botReply.message = getWeather(text);
        }
        return botReply;
    }

    public static String[] parseCitiesSettingText(String[] cities, String text) {
        String[] pairs = text.split("\\s?\\n\\s?");
        if (pairs.length > 4) {
            return null;
        }

        for (String pair : pairs) {
            if (pair.split(". ").length == 1) {
                return null;
            }
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
        } catch (HttpResponseException e) {
            switch (e.getStatusCode()) {
                case 401:
                    return "Это мой косяк.. извиняюсь \uD83D\uDE1E";
                case 404:
                    return "Сервер сейчас недоступен \uD83D\uDE1E";
                case 429:
                    return "Лимит запросов превышен, подожди минуту \uD83D\uDE43";
            }
            return "Какая-то непонятная ошибка \uD83D\uDE1E, попробуй позже \uD83D\uDE43";
        } catch (IOException e) {
            return "Не знаю такого города \uD83D\uDE1E";
        }

        return "\uD83C\uDF07 Город: " + model.getCityName() + "\n" +
                "\uD83D\uDD06 Температура: " +
                "по ощущениям " + tempFormat.format(model.getTempFeelsLike()) + " C°, " +
                "по факту " + tempFormat.format(model.getTemp()) + " C°\n" +
                "\uD83D\uDCA7 Влажность: " + model.getHumidity() + "%\n" +
                "\uD83C\uDF43 Скорость ветра: " + model.getWindSpeed() + " м/с\n";
    }
}
