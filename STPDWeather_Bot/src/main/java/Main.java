import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new TelegramBot(
                    new Bot(
                            new WeatherGetter(System.getenv("OPENWEATHER_API_TOKEN")),
                            new UserStateRepo(new HashMap<>(), new HashMap<>())
                    ),
                    new BotReply(),
                    System.getenv("WEATHERBOT_TOKEN"),
                    System.getenv("WEATHERBOT_USERNAME"))
            );
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

/*
TODO Замечания:
    + UserState -> UserStateRepo
    + Убрать из Bot и UserStateRepo завязку на Telegram
    + Свой класс для всего состояния пользователя: favouriteCities и lastMessages
    + Weather -> ???
    +     String[] -> Класс
    + Все зависимости собирать в main. В том числе все параметры из Env собирать в main и передавать в конструкторы.
	+     Токен передавать в конструктор WeatherGetter
    + UserStateRepo chatId string или long? Определитесь :)
    + getCities → favourite
    + Убрать парсинг текста из UserStateRepo
    + Сообщения об ошибках могут обманывать
    + Обработка исключений при получении из API
    + formatInfo → Bot. Не надо это делать в коде десериализации.
    Gson.fromJson — без ручного парсинга json-а
        Изменить десериализацию ( +1 балл )
    + Кнопки телеграмма
    BotReply telegramBot.getReplyToMessage(...)
    + BotReply {
    +     String message;
    +     KeyboardButton[] buttons;
    +     // ...
    +     // ...
    + }
    + sendMessage(Long chatId, String text, KeyboardButton[] buttons) {}

TODO Микро:
    + Замена конкретных городов в favouriteCities

TODO Глобально:
    Комментарии к погоде
        ( возьми зонтик; оденься потеплее )
    Inline система в чатах ( выпадающее меню )
        ( при написании @STPDWeatherBot что-то вылезает для быстрого набора )
    Погода не только на сегодня
        ( на завтра, на неделю вперёд, на конкретную дату )
*/
