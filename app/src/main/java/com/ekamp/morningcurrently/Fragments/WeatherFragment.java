package com.ekamp.morningcurrently.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekamp.morningcurrently.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import Controller.Controller;
import Model.DayWeather;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Erik Kamp on 8/31/14.
 */
public class WeatherFragment extends Fragment {

    public static final String TAG = "WeatherFragment";

    @InjectView(R.id.weatherIcon)
    ImageView weatherIcon;

    @InjectView(R.id.Lowtemperature)
    TextView lowTemperature;

    @InjectView(R.id.Highttemperature)
    TextView highTemperature;

    @InjectView(R.id.weather)
    TextView weatherText;

    @InjectView(R.id.dayOfTheWeek)
    TextView dayOfTheWeekText;

    private String minTemp,maxTemp,currentTemp,icon,weather,dayOfTheWeek;

    public static WeatherFragment create(DayWeather weather) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString("minTemp",weather.getTempMin());
        args.putString("maxTemp",weather.getTempMax());
        args.putString("currentTemp",weather.getCurrentTemp());
        args.putString("icon",weather.getWeatherIcon());
        args.putString("weather",weather.getWeather());
        args.putString("day",weather.getDayOfWeek());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        minTemp = getArguments().getString("minTemp");
        maxTemp = getArguments().getString("maxTemp");
        currentTemp = getArguments().getString("currentTemp");
        icon = getArguments().getString("icon");
        weather = getArguments().getString("weather");
        dayOfTheWeek = getArguments().getString("day");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.current_weather, container, false);
        ButterKnife.inject(this, root);

        /*VIEW SETUP START */

        lowTemperature.setText(minTemp);
        highTemperature.setText(maxTemp);
        weatherText.setText(weather);
        dayOfTheWeekText.setText(dayOfTheWeek);

        Picasso.with(getActivity()).load(Controller.getControllerInstance().getWeatherIconURL(icon)).placeholder(R.drawable.weather_placeholder).error(R.drawable.weather_placeholder).fit().into(weatherIcon, new Callback() {
            @Override
            public void onSuccess() {
//                Log.e(getClass().getName(), "Picasso Successful");
            }

            @Override
            public void onError() {
                Log.e(getClass().getName(), "Picasso ERROR : Not completed");
            }
        });

        /*VIEW SETUP END */

        return root;
    }
}
