package com.ekamp.morningcurrently;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Overwriting the default application in order to manage sharedPreferences
 *
 * @author Erik Kamp
 * @since v1.0
 */
public class WeatherApplication extends Application {

    private static WeatherApplication weatherApplicationInstance;

    @Override
    public void onCreate() {
        weatherApplicationInstance = this;
        super.onCreate();
    }

    public static WeatherApplication get() {
        return weatherApplicationInstance;
    }

    public Location getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            return location;
        }
        return null;
    }
}
