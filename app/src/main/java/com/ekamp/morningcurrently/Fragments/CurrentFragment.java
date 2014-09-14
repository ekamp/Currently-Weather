package com.ekamp.morningcurrently.Fragments;

import android.app.Fragment;
import android.os.Bundle;
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
 * Created by erikkamp on 8/31/14.
 */
public class CurrentFragment extends Fragment{

    @InjectView(R.id.weatherIcon)
    ImageView weatherIcon;

    @InjectView(R.id.temperature)
    TextView temperature;

    @InjectView(R.id.weather)
    TextView weatherText;

    private String minTemp,maxTemp,currentTemp,icon,weather;

    public static CurrentFragment create(DayWeather weather) {
        CurrentFragment fragment = new CurrentFragment();
        Bundle args = new Bundle();
        args.putString("minTemp",weather.getTempMin());
        args.putString("maxTemp",weather.getTempMax());
        args.putString("currentTemp",weather.getCurrentTemp());
        args.putString("icon",weather.getWeatherIcon());
        args.putString("weather",weather.getWeather());
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.current_weather, container, false);
        ButterKnife.inject(this, root);

        /*VIEW SETUP START */
        temperature.setText(currentTemp);
        weatherText.setText(weather);

        Log.e("weather information ", currentTemp + " " + weather + " " + Controller.getControllerInstance().getWeatherIconURL(icon));

        Picasso.with(getActivity()).load(Controller.getControllerInstance().getWeatherIconURL(icon)).placeholder(R.drawable.grey_placeholder).error(R.drawable.grey_placeholder).fit().into(weatherIcon, new Callback() {
            @Override
            public void onSuccess() {
                Log.e("Weather Request for image", "Successful");
            }

            @Override
            public void onError() {

                Log.e("Weather Request for image", "Not completed");
            }
        });

        /*VIEW SETUP END */

        return root;
    }
}
