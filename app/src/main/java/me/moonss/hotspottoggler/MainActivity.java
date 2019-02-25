package me.moonss.hotspottoggler;

import androidx.appcompat.app.AppCompatActivity;
import me.moonss.hotspottoggler.utilities.TetheringUtils;
import me.moonss.hotspottoggler.utilities.WifiApManager;

import android.os.Bundle;
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
}
