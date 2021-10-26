import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

    public static void main(String[] args) {

        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new TelegramBot());
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
    Weather -> ???
        String[] -> Класс
    Изменить десериализацию ( +1 балл )
    Кнопки в телеграме
    WeatherModel убрать
    Обработка исключений при получении из API

TODO Микро:
    Замена одного конкретного города в favouriteCities

TODO Глобально:
    Комментарии к погоде
        ( возьми зонтик; оденься потеплее )
    Inline система в чатах ( выпадающее меню )
        ( при написании @STPDWeatherBot что-то вылезает для быстрого набора )
    Погода не только на сегодня
        ( на завтра, на неделю вперёд, на конкретную дату )
*/