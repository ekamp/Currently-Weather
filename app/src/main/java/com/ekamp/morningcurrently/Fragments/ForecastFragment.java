package com.ekamp.morningcurrently.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekamp.morningcurrently.R;
import com.squareup.otto.Subscribe;

import Controller.Controller;
import Model.DayWeather;
import Model.ResponseParsing.ForecastEvent;
import butterknife.ButterKnife;

/**
 * Fragment used to display the current weather.
 */
public class ForecastFragment extends Fragment {


    private DayWeather weather;

    public static ForecastFragment create() {
        return new ForecastFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.current_weather, container, false);
        ButterKnife.inject(this, root);

        return root;
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

    @Subscribe
    public void receivedForecastEvent(ForecastEvent forecastEvent) {
        if (forecastEvent != null && forecastEvent.getWeather() != null) {
            weather = forecastEvent.getWeather();
        }
    }
}
