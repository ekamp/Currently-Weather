package com.ekamp.morningcurrently;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.ekamp.morningcurrently.Fragments.ETAFragment;
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
    public void recievedCurrentWeatherInformation(DayWeather weather)
    {
        setupCurrentWeatherInformation(weather);
    }

    @Subscribe
    public void recievedForecastWeatherInformation(ArrayList<DayWeather> weatherForecast)
    {
        setupForecastWeatherInformation(weatherForecast);
    }

    @Subscribe
    public void recievedGoogleMapsCommuteInformation(CommuteData commuteData)
    {
        Log.e("Received Commute Data ", commuteData.toString());
        setupCommuteData(commuteData);
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

    private void setupCommuteData(CommuteData commuteData)
    {
        ETAFragment etaFragment = ETAFragment.create(commuteData);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.commuteContainer, etaFragment, null).commit();
    }
}
