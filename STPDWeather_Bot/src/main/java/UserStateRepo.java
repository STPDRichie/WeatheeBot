import java.util.HashMap;

public class UserStateRepo {

    public final HashMap<String, String> lastMessage;
    private final HashMap<String, String[]> favouriteCities;

    public UserStateRepo(HashMap<String, String> lastMsg, HashMap<String, String[]> favCities) {
        lastMessage = lastMsg;
        favouriteCities = favCities;
    }

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

    Boolean setCities(String text, String chatId) {
        String[] cities = favouriteCities.getOrDefault(chatId, defaultCities);

        String[] pairs = text.split("\\s?\\n\\s?");
        if (pairs.length > 4) {
            return false;
        }

        for (String pair : pairs) {
            cities[Integer.parseInt(pair.split(". ")[0]) - 1] = pair.split(". ")[1];
        }
        favouriteCities.put(chatId, cities);

        return true;
    }
}
