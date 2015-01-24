package Controller;

import android.content.Context;
import android.location.Location;

import com.google.common.base.Strings;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;

/**
 * Used to control all activities within the application. Everything must go through the controller!
 * Once fired off by the controller the event bus (otto) will notify the view that the query is complete
 *
 * @author Erik Kamp
 * @since v1.0
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

    public void collectForecastInformation(String address) {
        QueryOpenWeatherForecast openWeatherQuery = new QueryOpenWeatherForecast();
        if (!Strings.isNullOrEmpty(address)) {
            openWeatherQuery.execute(Constants.gatherForecastData(address));
        } else {
            openWeatherQuery.execute(Constants.gatherForecastData(Constants.defaultCity));
        }
    }

    public void collectForecastInformation() {
        QueryOpenWeatherForecast openWeatherQuery = new QueryOpenWeatherForecast();
        openWeatherQuery.execute(Constants.gatherForecastData(Constants.defaultCity));
    }

    public void collectCurrentWeatherInformation(String city) {
        QueryOpenWeather openWeatherQuery = new QueryOpenWeather();
        if (!Strings.isNullOrEmpty(city)) {
            openWeatherQuery.execute(Constants.gatherCurrentWeatherData(city));
        } else {
            openWeatherQuery.execute(Constants.gatherCurrentWeatherData(Constants.defaultCity));
        }
    }

    public void getCurrentCommute(String startAddress, String endAddress) {
        QueryGoogleMaps mapsQuery = new QueryGoogleMaps();
        if (!Strings.isNullOrEmpty(endAddress) && !Strings.isNullOrEmpty(startAddress)) {
            mapsQuery.execute(Constants.gatherETAData(startAddress, endAddress));
        } else {
            mapsQuery.execute(Constants.gatherETAData(Constants.defaultStartAddress, Constants.defaultEndAddress));
        }
    }

    public void getCurrentCommute() {
        QueryGoogleMaps mapsQuery = new QueryGoogleMaps();
        mapsQuery.execute(Constants.gatherETAData(Constants.defaultStartAddress, Constants.defaultEndAddress));
    }

    public String getWeatherIconURL(String iconURL) {
        return Constants.getWeatherImage(iconURL);
    }

    public void getCurrentAddress(Context context,Location location) {
        new GetAddressTask(context).execute(location);
    }
}
