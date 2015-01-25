package com.ekamp.morningcurrently.Fragments.DialogFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ekamp.morningcurrently.R;
import com.ekamp.morningcurrently.WeatherApplication;
import com.google.common.base.Strings;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Dialog to be used to change a users current commute.
 */
public class EditCommuteLocationDialogFragment extends BaseDialogFragment {

    @InjectView(R.id.edit_commute_edittext)
    private EditText commuteEditText;

    @InjectView(R.id.edit_commute_submit)
    private Button commuteSubmit;

    public static EditCommuteLocationDialogFragment newInstance() {
        return new EditCommuteLocationDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View returnedView = inflater.inflate(R.layout.edit_commute_dialog, container);
        ButterKnife.inject(this, returnedView);
        getDialog().setTitle(getResources().getString(R.string.edit_commute_title));
        setListeners();

        return returnedView;
    }

    private void setListeners() {
        if (commuteSubmit != null && commuteEditText != null) {
            commuteSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String newCommuteLocation = checkAndGatherNewCommuteLocation();
                    submitCommuteLocation(newCommuteLocation);
                    getDialog().dismiss();
                }
            });
            listenForSoftKeyboardDone();
        }
    }

    private void listenForSoftKeyboardDone() {
        if (commuteEditText != null) {
            commuteEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE || EditorInfo.IME_ACTION_NEXT == actionId) {
                        submitCommuteLocation(checkAndGatherNewCommuteLocation());
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    private String checkAndGatherNewCommuteLocation() {
        if (commuteEditText.getText() != null && !Strings.isNullOrEmpty(commuteEditText.getText().toString())) {
            return commuteEditText.getText().toString();
        }
        return "";
    }

    private void submitCommuteLocation(String location) {
        if (!Strings.isNullOrEmpty(location)) {
            WeatherApplication.get().setCurrentCommuteLocation(location);
        }
    }
}
