package me.moonss.hotspottoggler.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import me.moonss.hotspottoggler.R;

public class PrefUtils {
    synchronized public static boolean getIsDataEnabled(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(
                context.getString(R.string.pref_data_enable_key),
                context.getResources().getBoolean(R.bool.pref_data_enabled_default)
        );
    }
}
