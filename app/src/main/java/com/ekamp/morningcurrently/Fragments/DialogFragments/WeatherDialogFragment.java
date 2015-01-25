package com.ekamp.morningcurrently.Fragments.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Dialog to be used throughout the application to notify the user about application events
 */
public class WeatherDialogFragment extends BaseDialogFragment {


    private static final String ARG_MESSAGE_KEY = "argMessageKey",
            ARG_TITLE_KEY = "argTitleKey", ARG_POSITIVE_BUTTON_KEY = "argPositiveButtonKey",
            ARG_NEGATIVE_BUTTON_KEY = "argNegativeButtonKey";

    private String message, title;

    private WeatherDialogOnClickListener weatherDialogOnClickListener;

    public static WeatherDialogFragment newInstance(String title, String message, String positiveButtonText, String negativeButtonText) {
        WeatherDialogFragment frag = new WeatherDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE_KEY, title);
        args.putString(ARG_MESSAGE_KEY, message);
        args.putString(ARG_POSITIVE_BUTTON_KEY, positiveButtonText);
        args.putString(ARG_NEGATIVE_BUTTON_KEY, negativeButtonText);

        frag.setArguments(args);
        return frag;
    }

    public static WeatherDialogFragment newInstance(String title, String message, String positiveButtonText) {
        WeatherDialogFragment frag = new WeatherDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE_KEY, title);
        args.putString(ARG_MESSAGE_KEY, message);
        args.putString(ARG_POSITIVE_BUTTON_KEY, positiveButtonText);

        frag.setArguments(args);
        return frag;
    }


    public static WeatherDialogFragment newInstance(String title, String message) {
        WeatherDialogFragment frag = new WeatherDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE_KEY, title);
        args.putString(ARG_MESSAGE_KEY, message);

        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            if (getArguments().containsKey(ARG_TITLE_KEY)) {
                title = getArguments().getString(ARG_TITLE_KEY);
                builder.setTitle(title);
            }
            if (getArguments().containsKey(ARG_MESSAGE_KEY)) {
                message = getArguments().getString(ARG_MESSAGE_KEY);
                builder.setMessage(message);
            }
            if (getArguments().containsKey(ARG_POSITIVE_BUTTON_KEY)) {
                builder.setPositiveButton(getArguments().getString(ARG_POSITIVE_BUTTON_KEY), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        weatherDialogOnClickListener.onPositiveClick();
                    }
                });
            }
            if (getArguments().containsKey(ARG_NEGATIVE_BUTTON_KEY)) {
                builder.setNegativeButton(getArguments().getString(ARG_NEGATIVE_BUTTON_KEY), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        weatherDialogOnClickListener.onNegativeClick();
                    }
                });
            }
            return builder.create();
        }
        return null;
    }

    public void setWeatherDialogOnClickListener(WeatherDialogOnClickListener weatherDialogOnClickListener) {
        this.weatherDialogOnClickListener = weatherDialogOnClickListener;
    }

    public interface WeatherDialogOnClickListener {
        public void onPositiveClick();

        public void onNegativeClick();
    }
}
