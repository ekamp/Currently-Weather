package com.ekamp.morningcurrently;

import android.app.Application;
import android.content.SharedPreferences;

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

    public String getLocation(){
        //TODO check that the strings are not null
        return getSharedPreferences().getString(getString(R.string.locationKey),defaultLocation);
    }

    public void setLocation(String location){
        sharedPreferencesEditor = getSharedPreferences().edit();
        if(sharedPreferencesEditor != null){
            sharedPreferencesEditor.putString(getString(R.string.locationKey),location);
        }
    }
}
