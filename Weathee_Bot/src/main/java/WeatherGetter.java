import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

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
                "&appid=" + apiToken
        );

        JSONObject jsonContent = new JSONObject(IOUtils.toString(url, StandardCharsets.UTF_8));

        OpenWeatherMap weatherMap = new Gson().fromJson(jsonContent.toString(), OpenWeatherMap.class);

        model.setCityName(weatherMap.name);
        model.setTemp(weatherMap.main.temp);
        model.setTempFeelsLike(weatherMap.main.feels_like);
        model.setHumidity(weatherMap.main.humidity);
        model.setWindSpeed(weatherMap.wind.speed);

        return model;
    }
}
