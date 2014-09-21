package com.ekamp.morningcurrently;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.ekamp.morningcurrently.Fragments.WeatherFragment;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import Controller.Controller;
import Model.CommuteData;
import Model.DayWeather;

public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Controller.getControllerInstance().collectForecastInformation("Shrewsbury,NJ");
//        Controller.getControllerInstance().collectCurrentWeatherInformation("Shrewsbury,NJ");
        Controller.getControllerInstance().getCurrentCommute("148+Spruce+Drive+Shrewsbury+NJ+07702","15+Corporate+Place+Piscataway+NJ");
    }
    @Override
    public void onResume()
    {
        super.onResume();
        Controller.getBus().register(this);
    }
    @Override
    public void onPause()
    {
        super.onPause();
        Controller.getBus().unregister(this);
    }

    @Subscribe
    public void recievedMapsInformation(CommuteData commuteData)
    {
        Log.e("Recieved Commute Data ", commuteData.toString());
    }

    @Subscribe
    public void recievedCurrentWeatherInformation(DayWeather weather)
    {
        Log.e("Recieved Day Data ", weather.toString());
//        setupCurrentWeatherInformation(weather);
        setupCurrentWeatherInformation(weather);
    }

    @Subscribe
    public void recievedForecastWeatherInformation(ArrayList<DayWeather> weatherForecast)
    {
        Log.e("Recieved Forecast Data ", weatherForecast.toString());
        setupForecastWeatherInformation(weatherForecast);
    }

    private void setupCurrentWeatherInformation(DayWeather weather)
    {
        WeatherFragment current = WeatherFragment.create(weather);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.weatherForecastContainer, current, null).commit();
    }

    private void setupForecastWeatherInformation(ArrayList<DayWeather> weatherForecast)
    {
        if(weatherForecast != null)
        {
            for(DayWeather weather : weatherForecast)
            {
                WeatherFragment current = WeatherFragment.create(weather);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.weatherForecastContainer, current, null).commit();
            }
        }
    }
}
