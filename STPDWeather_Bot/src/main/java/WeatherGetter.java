import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
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

    private final DecimalFormat tempFormat = new DecimalFormat("0.00");

    public WeatherModel getWeather(String city) throws IOException {
        WeatherModel model = new WeatherModel();

        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?" +
                "q=" + city +
                "&units=" + "metric" +
                "&lang=" + "ru" +
                "&appid=" + apiToken);

        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (scanner.hasNext()) {
            result.append(scanner.nextLine());
        }

//        OpenWeatherMap openWeatherMap = jsonToMap(result.toString());
//        WeatherData weatherData = openWeatherMap.data.get(0);

        HashMap<String, Object> respMap = jsonToMap(result.toString());
        HashMap<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
        HashMap<String, Object> windMap = jsonToMap(respMap.get("wind").toString());

//        String cityName = (String) respMap.get("name");
//        Double temp = (double) mainMap.get("temp");
//        Double tempFeelsLike = (double) mainMap.get("feels_like");
//        Double humidity = (double) mainMap.get("humidity");
//        Double windSpeed = (double) windMap.get("speed");

        String formatInfo = "\uD83C\uDF07 Город: " + respMap.get("name") + "\n" +
                "\uD83D\uDD06 Температура: " +
                    "по ощущениям " + tempFormat.format(mainMap.get("feels_like")) + " C°, " +
                    "по факту " + tempFormat.format(mainMap.get("temp")) + " C°\n" +
                "\uD83D\uDCA7 Влажность: " + mainMap.get("humidity") + "%\n" +
                "\uD83C\uDF43 Скорость ветра: " + windMap.get("speed") + " м/с\n";

        model.setFormatInfo(formatInfo);
        model.setCityName((String) respMap.get("name"));
        model.setTemp((double) mainMap.get("temp"));
        model.setTempFeelsLike((double) mainMap.get("feels_like"));
        model.setHumidity((double) mainMap.get("humidity"));
        model.setWindSpeed((double) windMap.get("speed"));

        return model;
    }
}
