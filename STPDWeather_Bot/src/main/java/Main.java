import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

    public static void main(String[] args) {

        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new TelegramBot(
                    new Bot(
                            new WeatherGetter(System.getenv("OPENWEATHER_API_TOKEN")),
                            new UserStateRepo()
                    ),
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
    + lastMessage → DialogState - enum
    + Userrepo.setFavCities → UserState
    + DialogState
    + Кнопки?
    + Gson.fromJson — без ручного парсинга json-а
    +     Изменить десериализацию ( +1 балл )

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
