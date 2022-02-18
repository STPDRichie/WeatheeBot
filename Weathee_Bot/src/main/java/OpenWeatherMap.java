public class OpenWeatherMap {
//    public Coordinates coord;
//    public List<Weather> weather;
//    public String base;
    public MainInfo main;
//    public int visibility;
    public Wind wind;
//    public Clouds clouds;
//    public int dt;
//    public Sys sys;
//    public int timezone;
//    public int id;
    public String name;
//    public int cod;
}

class Coordinates {
    public double lon;
    public double lat;
}

class Weather {
    public int id;
    public String main;
    public String description;
    public String icon;
}

class MainInfo {
    public double temp;
    public double feels_like;
//    public double temp_min;
//    public double temp_max;
//    public double pressure;
    public double humidity;
}

class Wind {
    public double speed;
}

class Clouds {
    public int all;
}

class Sys {
    public int type;
    public int id;
    public double message;
    public String country;
    public int sunrise;
    public int sunset;
}
