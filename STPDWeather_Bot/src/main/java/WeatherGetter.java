import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.Gson;

public class WeatherGetter {
    private final String apiToken;

    public WeatherGetter(String token) {
        apiToken = token;
    }

    public WeatherModel getWeather(String city) throws IOException {
        WeatherModel model = new WeatherModel();

        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?" +
                "q=" + city +
                "&units=" + "metric" +
                "&lang=" + "ru" +
                "&appid=" + apiToken);

        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder jsonContent = new StringBuilder();
        while (scanner.hasNext()) {
            jsonContent.append(scanner.nextLine());
        }
        String content = jsonContent.toString();

        OpenWeatherMap weatherMap = new Gson().fromJson(content, OpenWeatherMap.class);

        model.setCityName(weatherMap.name);
        model.setTemp(weatherMap.main.temp);
        model.setTempFeelsLike(weatherMap.main.feels_like);
        model.setHumidity(weatherMap.main.humidity);
        model.setWindSpeed(weatherMap.wind.speed);

        return model;
    }
}
