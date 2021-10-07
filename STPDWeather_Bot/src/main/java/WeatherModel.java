public class WeatherModel {

    private String name;
    private Double temperature;
    private Double humidity;
    private Double windSpeed;
    private Integer id;

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
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

    Integer getId() {
        return id;
    }

    void setId(Integer id) {
        this.id = id;
    }
}
