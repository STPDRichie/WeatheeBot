import java.util.HashMap;

public class UserStateRepo {
    private final HashMap<String, String> lastMessages = new HashMap<>();
    private final HashMap<String, String[]> favouriteCities = new HashMap<>();
    private final HashMap<String, UserState> userStates = new HashMap<>();

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

    String getLastMessage(String chatId) {
        return lastMessages.get(chatId);
    }

    void putLastMessage(String chatId, String message) {
        lastMessages.put(chatId, message);
    }

    UserState getUserState(String chatId) {
        return userStates.getOrDefault(chatId, new UserState());
    }
}
