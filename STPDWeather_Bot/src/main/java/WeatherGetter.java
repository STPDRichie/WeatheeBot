import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WeatherGetter {

    public HashMap<String, Object> jsonToMap(String weatherContent) {
        return new Gson().fromJson(weatherContent, new TypeToken<HashMap<String, Object>>(){}.getType());
    }

    private final DecimalFormat tempFormat = new DecimalFormat("0.00");

    public WeatherModel getWeather(String city) throws IOException {
        WeatherModel model = new WeatherModel();

        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city +
                "&appid=" + System.getenv("OPENWEATHER_API_TOKEN"));

        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (scanner.hasNext()) {
            result.append(scanner.nextLine());
        }

        HashMap<String, Object> respMap = jsonToMap(result.toString());
        HashMap<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
        HashMap<String, Object> windMap = jsonToMap(respMap.get("wind").toString());

        String cityName = new JSONObject(result.toString()).getString("name");
        Double temp = (double)(mainMap.get("temp")) - 273.15;
        Double humidity = (double)(mainMap.get("humidity"));
        Double windSpeed = (double)(windMap.get("speed"));

        String formatInfo = "\uD83C\uDF07 Город: " + cityName + "\n" +
                "\uD83D\uDD06 Температура: " + tempFormat.format(temp) + " C°\n" +
                "\uD83D\uDCA7 Влажность: " + humidity + "%\n" +
                "\uD83C\uDF43 Скорость ветра: " + windSpeed + " м/с\n";

        model.setFormatInfo(formatInfo);
        model.setCityName(cityName);
        model.setTemperature(temp);
        model.setHumidity(humidity);
        model.setWindSpeed(windSpeed);

        return model;
    }
}
