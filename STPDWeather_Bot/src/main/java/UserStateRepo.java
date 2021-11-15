import java.util.HashMap;

public class UserStateRepo {
    private final HashMap<String, String> lastMessages = new HashMap<>();
    private final HashMap<String, UserState> userStates = new HashMap<>();

    String[] getFavouriteCities(String chatId) {
        return userStates.getOrDefault(chatId, new UserState()).favouriteCities;
    }

    Boolean IsFavouriteCitiesSetted(String text, String chatId) {
        String[] cities = userStates.getOrDefault(chatId, new UserState()).favouriteCities;
        cities = Bot.parseCitiesSettingText(cities, text);

        if (cities == null)
            return false;

        userStates.put(chatId, new UserState(cities));

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

    void setUserState(String chatId, UserState userState) {
        userStates.put(chatId, userState);
    }

    void setDialogState(String chatId, DialogState dialogState) {
        userStates.get(chatId).dialogState = dialogState;
    }
}
