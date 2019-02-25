package me.moonss.hotspottoggler.utilities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import me.moonss.hotspottoggler.service.HotSpotToggleWidgetService;

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

    public static void toggleTethering(Context context, boolean toggleEnabled, boolean dataEnabled) {
        setCarrierDataEnabled(context, dataEnabled);
        WifiApManager wifiApManager = new WifiApManager(context);
        wifiApManager.showWritePermissionSettings(false);
        wifiApManager.setWifiApEnabled(null, toggleEnabled);
    }

    private static void setMobileDataEnabled(Context context, boolean enabled)
            throws ClassNotFoundException,
            NoSuchFieldException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        final ConnectivityManager conman = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final Class conmanClass = Class.forName(conman.getClass().getName());
        final Field connectivityManagerField = conmanClass.getDeclaredField("mService");
        connectivityManagerField.setAccessible(true);
        final Object connectivityManager = connectivityManagerField.get(conman);
        final Class connectivityManagerClass =  Class.forName(connectivityManager.getClass().getName());
        final Method setMobileDataEnabledMethod =
                connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        setMobileDataEnabledMethod.setAccessible(true);

        setMobileDataEnabledMethod.invoke(connectivityManager, enabled);
    }
    private static void setCarrierDataEnabled(Context context, boolean action) {
        try {
            setMobileDataEnabled(context, action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
