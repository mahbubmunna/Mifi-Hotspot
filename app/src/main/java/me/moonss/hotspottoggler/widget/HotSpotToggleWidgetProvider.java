package me.moonss.hotspottoggler.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import me.moonss.hotspottoggler.R;
import me.moonss.hotspottoggler.service.HotSpotToggleWidgetService;
import me.moonss.hotspottoggler.utilities.TetheringUtils;

/**
 * Implementation of App Widget functionality.
 */
public class HotSpotToggleWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, int imgResource) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.hot_spot_toggle);

        Intent intent = new Intent(context, HotSpotToggleWidgetService.class);
        if (imgResource == R.drawable.ic_wifi_tethering_black_24dp) {
            views.setImageViewResource(R.id.toggleImageView, imgResource);
            intent.setAction(TetheringUtils.HOTSPOT_OFF_ACTION);
        } else if (imgResource == R.drawable.ic_portable_wifi_off_black_24dp) {
            views.setImageViewResource(R.id.toggleImageView, imgResource);
            intent.setAction(TetheringUtils.HOTSPOT_ON_ACTION);
        }

        PendingIntent pendingIntent = PendingIntent.getService(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.toggleImageView, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateTetherWidget(
            Context context,
            AppWidgetManager widgetManager,
            int imageResource,
            int[] appIds) {
        for (int appId : appIds) {
            updateAppWidget(context, widgetManager, appId, imageResource);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

