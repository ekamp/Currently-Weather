package com.ekamp.morningcurrently.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekamp.morningcurrently.R;

import Model.DayWeather;
import butterknife.ButterKnife;

/**
 * Created by erikkamp on 8/31/14.
 */
public class ForecastFragment extends Fragment {

    DayWeather weather;

    private ForecastFragment(DayWeather weather)
    {
        this.weather = weather;
    }

    public static ForecastFragment create(DayWeather weather) {
        ForecastFragment fragment = new ForecastFragment(weather);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.current_weather, container, false);
        ButterKnife.inject(this, root);

        return root;
    }
}
