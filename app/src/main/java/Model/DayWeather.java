package Model;

/**
 * Created by erikkamp on 8/31/14.
 *
 * Class to be used to indicate the weather of a day, its icon and the temperature of that day
 */

public class DayWeather {

    private String weatherIcon, weather, tempMin, tempMax, currentTemp, dayOfWeek;


    public DayWeather(String weatherIcon, String weather, String tempMin, String tempMax, String currentTemp, String dayOfWeek) {
        this.weatherIcon = weatherIcon;
        this.weather = weather;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.currentTemp = currentTemp;
        this.dayOfWeek = dayOfWeek;
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

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    @Override
    public String toString() {
        return "DayWeather{" +
                "weatherIcon='" + weatherIcon + '\'' +
                ", weather='" + weather + '\'' +
                ", tempMin='" + tempMin + '\'' +
                ", tempMax='" + tempMax + '\'' +
                ", currentTemp='" + currentTemp + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                '}';
    }
}
