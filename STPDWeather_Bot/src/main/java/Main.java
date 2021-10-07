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

    // TODO К этой:
    //    + команда для дефолтного города
    //    + класс состояния бота для конктреного chatId
    //    + API
    //    кнопки для списка дефолтных городов
    //
    // TODO К следующей:
    //    замена одного конкретного города в favouriteCities
    //
    // TODO Глобально:
    //    комментарии к погоде
    //    ( возьми зонтик; оденься потеплее )
    //    Inline система в чатах ( выпадающее меню )
    //    ( при написании @STPDWeatherBot что-то вылезает для быстрого набора )
    //    Погода не только на сегодня
    //    ( на завтра, на неделю вперёд, на конкретную дату )