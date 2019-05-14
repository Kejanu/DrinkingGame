package de.kejanu.drinkinggame.Fragments;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import de.kejanu.drinkinggame.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
    }
}
