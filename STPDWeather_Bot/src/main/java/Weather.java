import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Weather {
    private final DecimalFormat tempFormat = new DecimalFormat("0.00");

    public String[] getWeather(String city, WeatherModel model) throws IOException {
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city +
                "&appid=" + System.getenv("OPENWEATHER_API_TOKEN"));

        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (scanner.hasNext()) {
            result.append(scanner.nextLine());
        }

        JSONObject object = new JSONObject(result.toString());
        model.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");
        model.setTemperature(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));

        JSONObject wind = object.getJSONObject("wind");
        model.setWindSpeed(wind.getDouble("speed"));

        JSONArray getArray = object.getJSONArray("weather");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            model.setId(obj.getInt("id"));
        }

        String temperature = tempFormat.format(model.getTemperature() - 273.15);

        String info = "\uD83C\uDF07 Город: " + model.getName() + "\n" +
                "\uD83D\uDD06 Температура: " + temperature + " C°\n" +
                "\uD83D\uDCA7 Влажность: " + model.getHumidity() + "%\n" +
                "\uD83C\uDF43 Скорость ветра: " + model.getWindSpeed() + " м/с\n";

        return (new String[] {info, temperature});
    }
}
