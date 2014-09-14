package Model;

/**
 * Created by erikkamp on 8/31/14.
 *
 * Class to be used to indicate the weather of a day, its icon and the temperature of that day
 */
public class DayWeather {

    private String weatherIcon, weather, tempMin, tempMax, currentTemp;

    public DayWeather(String weatherIcon, String weather, String tempMin, String tempMax, String currentTemp) {
        this.weatherIcon = weatherIcon;
        this.weather = weather;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.currentTemp = currentTemp;
    }


    @Override
    public String toString() {
        return "DayWeather{" +
                "weatherIcon='" + weatherIcon + '\'' +
                ", weather='" + weather + '\'' +
                ", tempMin='" + tempMin + '\'' +
                ", tempMax='" + tempMax + '\'' +
                ", currentTemp='" + currentTemp + '\'' +
                '}';
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public String getWeather() {
        return weather;
    }

    public String getTempMin() {
        return tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public String getCurrentTemp() {
        return currentTemp;
    }
}
