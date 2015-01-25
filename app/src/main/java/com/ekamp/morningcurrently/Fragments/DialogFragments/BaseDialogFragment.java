package com.ekamp.morningcurrently.Fragments.DialogFragments;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

/**
 * Used as a template for create DialogFragments.
 */
public class BaseDialogFragment extends DialogFragment {
    public void show(FragmentManager fragmentManager) {
        if (fragmentManager == null)
            return;
        super.show(fragmentManager, getTag());
    }
}
