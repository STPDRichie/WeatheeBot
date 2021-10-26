public class WeatherModel {

    private String formatInfo;
    private String cityName;
    private Double temperature;
    private Double humidity;
    private Double windSpeed;
    private Double rain;

    String getFormatInfo() { return formatInfo; }

    void setFormatInfo(String formatInfo) { this.formatInfo = formatInfo; }

    String getCityName() {
        return cityName;
    }

    void setCityName(String cityName) {
        this.cityName = cityName;
    }

    Double getTemperature() {
        return temperature;
    }

    void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    Double getHumidity() {
        return humidity;
    }

    void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    Double getWindSpeed() {
        return windSpeed;
    }

    void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    Double getRain() { return rain; }

    void setRain(Double rain) { this.rain = rain; }
}
