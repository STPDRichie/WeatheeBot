public class UserState {
    public String[] favouriteCities;
    public DialogState dialogState;

    public final String[] defaultCities = new String[] {
            "Екатеринбург",
            "Челябинск",
            "Нижний Тагил",
            "Пермь",
    };

    public UserState() {
        favouriteCities = defaultCities;
        dialogState = DialogState.Default;
    }

    public UserState(String[] cities) {
        favouriteCities = cities;
        dialogState = DialogState.Default;
    }
}
