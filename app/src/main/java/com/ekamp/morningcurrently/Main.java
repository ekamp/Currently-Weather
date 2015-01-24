package com.ekamp.morningcurrently;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.ekamp.morningcurrently.Fragments.ETAFragment;
import com.ekamp.morningcurrently.Fragments.WeatherFragment;
import com.google.common.base.Strings;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import Controller.Controller;
import Model.CommuteData;
import Model.DayWeather;
import Model.ResponseParsing.GetAddressEvent;

/**
 *  Main activity that controls the lifecycle of the fragments and asks the controller for
 *  weather information.
 *
 * @author Erik Kamp
 * @since v1.0
 */

public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestUserAddress();
    }

    @Override
    public void onResume() {
        super.onResume();
        Controller.getBus().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Controller.getBus().unregister(this);
    }

    private void requestUserAddress(){
        Controller.getControllerInstance().getCurrentAddress(this,WeatherApplication.get().getLocation());
    }

    private void requestForecastAndCommuteInformation(String address){
        Controller.getControllerInstance().collectForecastInformation(address);
        Controller.getControllerInstance().getCurrentCommute();
    }

    private void setupCurrentWeatherInformation(DayWeather weather) {
        WeatherFragment current = WeatherFragment.create(weather);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.weatherForecastContainer, current, null).commit();
    }

    private void setupForecastWeatherInformation(ArrayList<DayWeather> weatherForecast) {
        if (weatherForecast != null) {
            for (DayWeather weather : weatherForecast) {
                WeatherFragment current = WeatherFragment.create(weather);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.weatherForecastContainer, current, null).commit();
            }
        }
    }

    private void setupCommuteData(CommuteData commuteData) {
        ETAFragment etaFragment = ETAFragment.create(commuteData);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.commuteContainer, etaFragment, null).commit();
    }

    @Subscribe
    public void recievedCurrentWeatherInformation(DayWeather weather) {
        setupCurrentWeatherInformation(weather);
    }

    @Subscribe
    public void recievedForecastWeatherInformation(ArrayList<DayWeather> weatherForecast) {
        setupForecastWeatherInformation(weatherForecast);
    }

    @Subscribe
    public void recievedGoogleMapsCommuteInformation(CommuteData commuteData) {
        setupCommuteData(commuteData);
    }

    @Subscribe
    public void receivedCurrentUserAddress(GetAddressEvent addressEvent){
        if(addressEvent != null && !Strings.isNullOrEmpty(addressEvent.getAddress())){
            requestForecastAndCommuteInformation(addressEvent.getAddress());
        }
    }
}
