import java.util.HashMap;

public class UserStateRepo {
    private final HashMap<String, UserState> userStates = new HashMap<>();

    String[] getFavouriteCities(String chatId) {
        return userStates.getOrDefault(chatId, new UserState()).favouriteCities;
    }

    Boolean IsFavouriteCitiesSetted(String[] newCities, String chatId) {
        if (newCities == null)
            return false;

        userStates.put(chatId, new UserState(newCities));

        return true;
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
