package Controller;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;

/**
 * Created by erikkamp on 8/31/14.
 * Used to control all activities within the application. Everything must go thru the controller!
 * Once fired off by the controller the event bus (otto) will notify the view that the query is completen
 */
public class Controller {

    private static Controller controllerInstance;
    private static OkHttpClient okHttpClient;
    private static Bus ottoBus;

    private Controller() {
    }

    public static Controller getControllerInstance() {
        if (controllerInstance == null) {
            controllerInstance = new Controller();
            okHttpClient = new OkHttpClient();
        }
        return controllerInstance;
    }

    public static OkHttpClient getOkClientInstance() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
        return okHttpClient;
    }

    public static Bus getBus() {
        if (ottoBus == null) {
            ottoBus = new Bus();
        }
        return ottoBus;
    }

    public void collectForecastInformation(String city) {
        QueryOpenWeatherForecast openWeatherQuery = new QueryOpenWeatherForecast();
        openWeatherQuery.execute(Constants.gatherForecastData(city));
    }

    public void collectCurrentWeatherInformation(String city) {
        QueryOpenWeather openWeatherQuery = new QueryOpenWeather();
        openWeatherQuery.execute(Constants.gatherCurrentWeatherData(city));
    }

    public void getCurrentCommute(String startAddress, String endAddress) {
        QueryGoogleMaps mapsQuery = new QueryGoogleMaps();
        mapsQuery.execute(Constants.gatherETAData(startAddress, endAddress));
    }

    public String getWeatherIconURL(String iconURL)
    {
        return Constants.getWeatherImage(iconURL);
    }
}
