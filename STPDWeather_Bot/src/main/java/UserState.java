import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;

public class UserState {

    private final HashMap<String, String[]> favouriteCities = new HashMap<>();
    private final String[] defaultCities = new String[] {
            "Екатеринбург",
            "Челябинск",
            "Тагил",
            "Москва",
    };

    String[] getCities(String chatId) {
        if (favouriteCities.containsKey(chatId)) {
            return favouriteCities.get(chatId);
        }

        return defaultCities;
    }

    void setCities(Message message) {
        String[] splitText = message.getText().split("[0-9]\\.");
        String[] cities = new String[4];
        System.arraycopy(splitText, 1, cities, 0, 4);

        favouriteCities.put(message.getChatId().toString(), cities);
    }
}
