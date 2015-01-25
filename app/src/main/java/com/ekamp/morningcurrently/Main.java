package com.ekamp.morningcurrently;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.ekamp.morningcurrently.Fragments.DialogFragments.EditCommuteLocationDialogFragment;
import com.ekamp.morningcurrently.Fragments.ETAFragment;
import com.ekamp.morningcurrently.Fragments.DialogFragments.WeatherDialogFragment;
import com.ekamp.morningcurrently.Fragments.WeatherFragment;
import com.google.common.base.Strings;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import Controller.Controller;
import Model.CommuteData;
import Model.DayWeather;
import Model.ResponseParsing.GetAddressEvent;

/**
 * Main activity that controls the lifecycle of the fragments and asks the controller for
 * weather information.
 *
 * @author Erik Kamp
 * @since v1.0
 */

public class Main extends FragmentActivity {

    private ProgressBar progressBar;
    private View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.loading_dialog);
        contentView = findViewById(R.id.horizontalScrollContainer);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_commute, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.edit_commute:
                editCommuteData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void editCommuteData() {
        EditCommuteLocationDialogFragment editCommuteLocationDialogFragment = EditCommuteLocationDialogFragment.newInstance();
        editCommuteLocationDialogFragment.show(getSupportFragmentManager());
    }

    private void requestUserAddress() {
        Controller.getControllerInstance().getCurrentAddress(this, WeatherApplication.get().getLocation());
    }

    private void requestForecastAndCommuteInformation(String address) {
        Controller.getControllerInstance().collectForecastInformation(address);
        Controller.getControllerInstance().getCurrentCommute(address);
    }

    private void setupCurrentWeatherInformation(DayWeather weather) {
        WeatherFragment current = WeatherFragment.create(weather);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.weatherForecastContainer, current, WeatherFragment.TAG).commit();
    }

    private void setupForecastWeatherInformation(ArrayList<DayWeather> weatherForecast) {
        if (weatherForecast != null) {
            for (DayWeather weather : weatherForecast) {
                WeatherFragment current = WeatherFragment.create(weather);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.weatherForecastContainer, current, WeatherFragment.TAG).commit();
            }
        }
        hideLoadingDialog();
    }

    private void setupCommuteData(CommuteData commuteData) {
        ETAFragment etaFragment = ETAFragment.create(commuteData);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.commuteContainer, etaFragment, ETAFragment.TAG).commit();
    }

    private void showLoadingDialog() {
        if (contentView != null && progressBar != null) {
            showContentOrLoadingIndicator(progressBar, contentView);
        }
    }

    private void hideLoadingDialog() {
        if (contentView != null && progressBar != null) {
            showContentOrLoadingIndicator(contentView, progressBar);
        }
    }

    private void showContentOrLoadingIndicator(View showView, final View hideView) {
        if (showView == null || hideView == null)
            return;

        // Set the "show" view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        showView.setAlpha(0f);
        showView.setVisibility(View.VISIBLE);

        // Animate the "show" view to 100% opacity, and clear any animation listener set on
        // the view. Remember that listeners are not limited to the specific animation
        // describes in the chained method calls. Listeners are set on the
        // ViewPropertyAnimator object for the view, which persists across several
        // animations.
        showView.animate()
                .alpha(1f)
                .setDuration(500)
                .setListener(null);

        // Animate the "hide" view to 0% opacity. After the animation ends, set its visibility
        // to GONE as an optimization step (it won't participate in layout passes, etc.)
        hideView.animate()
                .alpha(0f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        hideView.setVisibility(View.GONE);
                    }
                });
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
    public void receivedCurrentUserAddress(GetAddressEvent addressEvent) {
        if (addressEvent != null && !Strings.isNullOrEmpty(addressEvent.getAddress())) {
            requestForecastAndCommuteInformation(addressEvent.getAddress());
        } else {
            final WeatherDialogFragment weatherDialogFragment = WeatherDialogFragment.newInstance(getResources().getString(R.string.no_gps_dialog_title),
                    getResources().getString(R.string.no_gps_dialog_message),
                    getResources().getString(R.string.continue_dialog_message));
            weatherDialogFragment.setWeatherDialogOnClickListener(new WeatherDialogFragment.WeatherDialogOnClickListener() {
                @Override
                public void onPositiveClick() {
                    weatherDialogFragment.dismiss();
                    onBackPressed();
                }

                @Override
                public void onNegativeClick() {
                }
            });
            weatherDialogFragment.show(getSupportFragmentManager());
        }
    }
}
