package com.ekamp.morningcurrently.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ekamp.morningcurrently.R;
import Model.CommuteData;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Fragment to be placed at the bottom of the view that will tell the estimated time of arrival
 * for the user in terms of min.
 *
 * @author Erik Kamp
 * @since v1.0
 */
public class ETAFragment extends Fragment {

    @InjectView(R.id.etaTime)
    TextView estimatedTime;

    @InjectView(R.id.distance)
    TextView distanceDisplay;

    @InjectView(R.id.recommendedRoute)
    TextView recommendedRoute;

    private String summary, timeTaken, distance;

    public static ETAFragment create(CommuteData commuteData){
        ETAFragment etaFragment = new ETAFragment();
        Bundle args = new Bundle();
        args.putString("summary",commuteData.getSummary());
        args.putString("timeTaken",commuteData.getTimeTaken());
        args.putString("distance",commuteData.getDistance());
        etaFragment.setArguments(args);
        return etaFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        summary = getArguments().getString("summary");
        timeTaken = getArguments().getString("timeTaken");
        distance = getArguments().getString("distance");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.eta, container, false);
        ButterKnife.inject(this, root);

        /*VIEW SETUP START */

        recommendedRoute.setText(this.getString(R.string.RecommendationText) + " "+ summary);
        estimatedTime.setText(this.getString(R.string.ETA) +" "+ timeTaken);
        distanceDisplay.setText(this.getString(R.string.Distance) + " "+ distance);

        /*VIEW SETUP END */

        return root;
    }
}
