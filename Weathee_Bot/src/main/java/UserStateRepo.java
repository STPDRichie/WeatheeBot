import java.util.HashMap;

public class UserStateRepo {
    private final HashMap<String, UserState> userStates = new HashMap<>();

    String[] getFavouriteCities(String chatId) {
        return userStates.getOrDefault(chatId, new UserState()).favouriteCities;
    }

    void setFavouriteCities(String chatId, String[] newCities) {
        userStates.put(chatId, new UserState(newCities));
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
