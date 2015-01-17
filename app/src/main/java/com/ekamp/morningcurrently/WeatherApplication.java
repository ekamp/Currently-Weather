package com.ekamp.morningcurrently;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;

/**
 * Overwriting the default application in order to manage sharedPreferences
 *
 * @author Erik Kamp
 * @since v1.0
 */
public class WeatherApplication extends Application {

    private static final String defaultLocation = "New York";
    private SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public SharedPreferences getSharedPreferences() {
        return getSharedPreferences(getString(R.string.preferencesKey),0);
    }

    public Location getLocation(){
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(locationManager != null){
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location == null){
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            return location;
        }
        return null;
    }
}
