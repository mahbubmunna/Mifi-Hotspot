package me.moonss.hotspottoggler.service;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Nullable;
import me.moonss.hotspottoggler.utilities.PrefUtils;
import me.moonss.hotspottoggler.widget.HotSpotToggleWidgetProvider;
import me.moonss.hotspottoggler.R;
import me.moonss.hotspottoggler.utilities.TetheringUtils;


public class HotSpotToggleWidgetService extends IntentService {
    public static final String TAG = HotSpotToggleWidgetService.class.getSimpleName();
    Boolean dataEnabled;

    public HotSpotToggleWidgetService() {
        super("HotSpotToggleWidgetService");
    }



    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Context context = getApplicationContext();
        if (intent != null) {
            final String action = intent.getAction();
            if (TetheringUtils.HOTSPOT_ON_ACTION.equals(action)) {
                Log.d(TAG, "Hello, calling form background");
                handleStartTethering(context);

            } else if (TetheringUtils.HOTSPOT_OFF_ACTION.equals(action)) {
                handleStopTethering(context);
            }
        }

    }
    //Starts the Tethering
    private void handleStartTethering(Context context) {
        dataEnabled = PrefUtils.getIsDataEnabled(context);
        TetheringUtils.toggleTethering(this, true, dataEnabled);
        int imgRes = R.drawable.ic_wifi_tethering_black_24dp;
        updateWidgetIcon(imgRes);
    }

    //Stops Tethering
    private void handleStopTethering(Context context) {
        TetheringUtils.toggleTethering(this,false, false);
        int imgRes = R.drawable.ic_portable_wifi_off_black_24dp;
        updateWidgetIcon(imgRes);
    }


    private void updateWidgetIcon(int imageResource) {
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
        int[] appIds = widgetManager.getAppWidgetIds(
                new ComponentName(this, HotSpotToggleWidgetProvider.class));
        HotSpotToggleWidgetProvider.updateTetherWidget(
                this, widgetManager, imageResource, appIds);
    }



}
