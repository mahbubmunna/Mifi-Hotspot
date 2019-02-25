package me.moonss.hotspottoggler.ui;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import me.moonss.hotspottoggler.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_toggle);
    }
}
