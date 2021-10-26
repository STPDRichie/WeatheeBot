import java.util.HashMap;

public class UserStateRepo {

    public final HashMap<Long, String> lastMessages = new HashMap<>();
    private final HashMap<String, String[]> favouriteCities = new HashMap<>();

    private final String[] defaultCities = new String[] {
            "Екатеринбург",
            "Челябинск",
            "Нижний Тагил",
            "Пермь",
    };

    String[] getCities(String chatId) {
        if (favouriteCities.containsKey(chatId)) {
            return favouriteCities.get(chatId);
        }

        return defaultCities;
    }

    void setCities(String text, String chatId) {
        String[] splitText = text.split("[0-9]\\.");
        String[] cities = new String[4];
        System.arraycopy(splitText, 1, cities, 0, 4);

        favouriteCities.put(chatId, cities);
    }
}
