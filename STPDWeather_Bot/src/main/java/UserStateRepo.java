import java.util.HashMap;

public class UserState {
    String chatId;
    String[] favCities;
    DialogState dialogState;
}

public class UserStateRepo {


    public final HashMap<String, String> lastMessage = new HashMap<>();
    private final HashMap<String, String[]> favouriteCities = new HashMap<>();

    public final String[] defaultCities = new String[] {
            "Екатеринбург",
            "Челябинск",
            "Нижний Тагил",
            "Пермь",
    };

    String[] getFavouriteCities(String chatId) {
        if (favouriteCities.containsKey(chatId)) {
            return favouriteCities.get(chatId);
        }

        return defaultCities;
    }

    Boolean setFavouriteCities(String text, String chatId) {
        String[] cities = favouriteCities.getOrDefault(chatId, defaultCities);
        cities = Bot.parseCitiesSettingText(cities, text);

        if (cities == null)
            return false;

        favouriteCities.put(chatId, cities);

        return true;
    }
}
