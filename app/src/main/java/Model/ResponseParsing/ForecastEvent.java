package Model.ResponseParsing;

import Model.DayWeather;

/**
 * Created by erikkamp on 1/24/15.
 */
public class ForecastEvent {

    private DayWeather weather;

    public ForecastEvent(DayWeather weather) {
        this.weather = weather;
    }

    public DayWeather getWeather() {
        return weather;
    }
}
