package me.moonss.hotspottoggler.ui;

import androidx.appcompat.app.AppCompatActivity;
import me.moonss.hotspottoggler.R;
import me.moonss.hotspottoggler.utilities.TetheringUtils;
import me.moonss.hotspottoggler.utilities.WifiApManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startTethering(View view) {
        WifiApManager wifiApManager = new WifiApManager(this);
        ImageView tetheringImage = findViewById(R.id.tetheringImg);
        if (!wifiApManager.isWifiApEnabled()) {
            TetheringUtils.startTetheringService(this);
            tetheringImage.setImageResource(R.drawable.ic_wifi_tethering_black_24dp);
        } else {
            TetheringUtils.stopTetheringService(this);
            tetheringImage.setImageResource(R.drawable.ic_portable_wifi_off_black_24dp);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toggler_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent startSettingActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
