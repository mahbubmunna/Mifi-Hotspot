package me.moonss.hotspottoggler.utilities;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.lang.reflect.Method;

import me.moonss.hotspottoggler.HotSpotToggleWidgetService;

public class TetheringUtils {
    public static final String HOTSPOT_ON_ACTION = "me.moonss.hotspottoggler.action.on_hotspot_widget";
    public static final String HOTSPOT_OFF_ACTION = "me.moonss.hotspottoggler.action.off_hotspot_widget";

    public static void startTetheringService(Context context) {
        Intent intent = new Intent(context, HotSpotToggleWidgetService.class);
        intent.setAction(HOTSPOT_ON_ACTION);
        context.startService(intent);
    }

    public static void stopTetheringService(Context context) {
        Intent intent = new Intent(context, HotSpotToggleWidgetService.class);
        intent.setAction(HOTSPOT_OFF_ACTION);
        context.startService(intent);
    }

    public static void toggleTethering(Context context, boolean action) {
        WifiApManager wifiApManager = new WifiApManager(context);
        wifiApManager.showWritePermissionSettings(false);
        wifiApManager.setWifiApEnabled(null, action);
    }
}
