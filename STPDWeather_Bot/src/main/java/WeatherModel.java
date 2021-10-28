public class WeatherModel {

    private String formatInfo;
    private String cityName;
    private Double temp;
    private Double tempFeelsLike;
    private Double humidity;
    private Double windSpeed;

    String getFormatInfo() { return formatInfo; }

    void setFormatInfo(String formatInfo) { this.formatInfo = formatInfo; }

    String getCityName() { return cityName; }

    void setCityName(String cityName) { this.cityName = cityName; }

    Double getTemp() { return temp; }

    void setTemp(Double temp) { this.temp = temp; }

    Double getTempFeelsLike() { return tempFeelsLike; }

    void setTempFeelsLike(Double tempFeelsLike) { this.tempFeelsLike = tempFeelsLike; }

    Double getHumidity() { return humidity; }

    void setHumidity(Double humidity) { this.humidity = humidity; }

    Double getWindSpeed() { return windSpeed; }

    void setWindSpeed(Double windSpeed) { this.windSpeed = windSpeed; }
}
