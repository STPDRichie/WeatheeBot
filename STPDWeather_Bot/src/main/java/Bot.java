import org.apache.http.client.HttpResponseException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class Bot {

    private final WeatherGetter weatherGetter;
    private final UserStateRepo userStateRepo;

    private final HashMap<String, String> commands = new HashMap<>();

    private final DecimalFormat tempFormat = new DecimalFormat("0.00");

    public Bot(WeatherGetter wGetter, UserStateRepo repo) {
        weatherGetter = wGetter;
        userStateRepo = repo;

        commands.put("/start",
                "Введи название города, в котором хочешь узнать погоду" + "\n" +
                        "Для дополнительной информации вызови /help");
        commands.put("/help",
                "Привет! \u270B Я STPDWeatherBot \u2601" + "\n" +
                        "Напиши название города, и я покажу погоду в нём!" + "\n" +
                        "Также ты можешь сохранить четыре избранных города командой " + "/set_favourite_cities" + "\n" +
                        "Ещё можешь вывести список этих городов командой " + "/my_favourite_cities");
        commands.put("/my_favourite_cities",
                "'/my_favourite_cities' request");
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

    public BotReply getReplyToMessage(String text, String chatId) {
        UserState userState = userStateRepo.getUserState(chatId);
        String message;

        if (userState.dialogState == DialogState.SettingFavouriteCities) {
            if (userStateRepo.IsFavouriteCitiesSetted(
                    parseCitiesSettingText(userStateRepo.getFavouriteCities(chatId), text),
                    chatId)) {
                message = "Список успешно изменён \uD83D\uDC4D";
            } else {
                message = "Ошибка произошла... \uD83D\uDE22";
            }
            userState.dialogState = DialogState.Default;
            userStateRepo.setUserState(chatId, userState);

            return new BotReply(message, createKeyboard(chatId));
        }

        if (text.indexOf('/') == 0) {
            message = getReplyToCommand(text, chatId);

            if (userState.dialogState == DialogState.WaitFavouriteCities) {
                String[] cities = userStateRepo.getFavouriteCities(chatId);
                StringBuilder response = new StringBuilder("\uD83C\uDF07 Твои избранные города: ");
                for (int i = 0; i < 4; i++) {
                    response.append("\n").append(i + 1).append(". ").append(cities[i]);
                }
                message = response.toString();

                userState.dialogState = DialogState.Default;
            }
        } else {
            message = getWeather(text);
        }

        userStateRepo.setUserState(chatId, userState);
        return new BotReply(message, createKeyboard(chatId));
    }

    public String getReplyToCommand(String commandText, String chatId) {
        StringBuilder reply;

        if (commands.containsKey(commandText)) {
            if (commandText.equals("/set_favourite_cities")) {
                userStateRepo.setDialogState(chatId, DialogState.SettingFavouriteCities);
            } else if (commandText.equals("/my_favourite_cities")) {
                userStateRepo.setDialogState(chatId, DialogState.WaitFavouriteCities);
            }
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

    public static String[] parseCitiesSettingText(String[] currentCities, String newCitiesText) {
        String[] citiesWithNumbers = newCitiesText.split("\\s?\\n\\s?");
        if (citiesWithNumbers.length > 4) {
            return null;
        }

        for (String cityWithNumber : citiesWithNumbers) {
            if (cityWithNumber.split(". ").length == 1) {
                return null;
            }
            currentCities[Integer.parseInt(cityWithNumber.split(". ")[0]) - 1]
                    = cityWithNumber.split(". ")[1];
        }
        return currentCities;
    }

    public ArrayList<ArrayList<String>> createKeyboard(String... chatId) {

        ArrayList<ArrayList<String>> keyboard = new ArrayList<>();
        ArrayList<String> row1 = new ArrayList<>();
        ArrayList<String> row2 = new ArrayList<>();

        String[] cities = new UserState().defaultCities;
        if (chatId.length > 0) {
            cities = userStateRepo.getFavouriteCities(chatId[0]);
        }

        row1.add(cities[0]);
        row1.add(cities[1]);
        row2.add(cities[2]);
        row2.add(cities[3]);

        keyboard.add(row1);
        keyboard.add(row2);

        return keyboard;
    }
}
