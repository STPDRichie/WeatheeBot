import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WeatherGetter {
    private final String apiToken;

    public WeatherGetter(String token) {
        apiToken = token;
    }

    public HashMap<String, Object> jsonToMap(String weatherContent) {
        return new Gson().fromJson(weatherContent, new TypeToken<HashMap<String, Object>>(){}.getType());
    }

//    public OpenWeatherMap jsonToMap(String weatherContent) {
//        return new Gson().fromJson(weatherContent, new TypeToken<OpenWeatherMap>(){}.getType());
//    }

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

//        OpenWeatherMap openWeatherMap = jsonToMap(jsonContent.toString());
//        WeatherData weatherData = openWeatherMap.data.get(0);

        HashMap<String, Object> respMap = jsonToMap(jsonContent.toString());
        HashMap<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
        HashMap<String, Object> windMap = jsonToMap(respMap.get("wind").toString());

        model.setCityName((String) respMap.get("name"));
        model.setTemp((double) mainMap.get("temp"));
        model.setTempFeelsLike((double) mainMap.get("feels_like"));
        model.setHumidity((double) mainMap.get("humidity"));
        model.setWindSpeed((double) windMap.get("speed"));

        return model;
    }
}
