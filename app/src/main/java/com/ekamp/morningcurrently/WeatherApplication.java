package com.ekamp.morningcurrently;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;

import Model.Utils.FormatUtils;

/**
 * Weather application override to manage sharedPrefs and User location
 *
 * @author Erik Kamp
 * @since v1.0
 */
public class WeatherApplication extends Application {

    private static WeatherApplication weatherApplicationInstance;
    private static final String sharedPreferencesKey = "SHARED_PREFS_KEY";
    private static final String sharedPreferencesCommuteLocationKey = "COMMUTE_LOCATION_KEY";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    public void onCreate() {
        weatherApplicationInstance = this;
        super.onCreate();
    }

    public static WeatherApplication get() {
        return weatherApplicationInstance;
    }

    public String getCurrentCommuteLocation() {
        if ((sharedPreferences = getSharedPreferences(sharedPreferencesKey, 0)) != null) {
            return sharedPreferences.getString(sharedPreferencesCommuteLocationKey, "");
        }
        return "";
    }

    public boolean setCurrentCommuteLocation(String location) {
        if ((sharedPreferences = getSharedPreferences(sharedPreferencesKey, 0)) != null) {
            sharedPreferencesEditor = sharedPreferences.edit();
            sharedPreferencesEditor.putString(sharedPreferencesCommuteLocationKey, FormatUtils.formatUserLocation(location));
            sharedPreferencesEditor.commit();
            return true;
        }
        return false;
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
