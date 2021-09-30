public class Bot {

    public String getReplyToMessage(String text) {

        if (text.indexOf('/') == 0) {
            return getReplyToCommand(text);
        } else {
            return getWeather(text);
        }

    }

    public String getReplyToCommand(String command) {
        String reply;

        switch (command) {
            case "/help":
                reply = "Я WeatherBot. \n" + "Введи название города, и я покажу погоду в нём.";
                break;

            case "/start":
                reply = "Введи название города, в котором хочешь узнать погоду.";
                break;

            default:
                reply = "Неизвестная команда";
        }

        return reply;
    }

    public String getWeather(String city) {
        String reply;

        switch (city) {
            case "Екатеринбург":
                reply = "Город: " + city + "\n" +
                        "Температура: 5 C°" + "\n" +
                        "Влажность: 75%" + "\n" +
                        "Скорость ветра: 3.0 м/с" + "\n" +
                        "Но это не точно";
                break;

            case "Челябинск":
                reply = "Город: " + city + "\n" +
                        "Температура: 7 C°" + "\n" +
                        "Влажность: 80%" + "\n" +
                        "Скорость ветра: 6.0 м/с" + "\n" +
                        "Но это не точно";
                break;

            default:
                reply = "Не знаю такого города. Пока я могу показать погоду только в двух (((";
        }

        return reply;
    }
}
