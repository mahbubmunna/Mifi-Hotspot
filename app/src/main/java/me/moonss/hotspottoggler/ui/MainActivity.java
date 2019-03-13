package me.moonss.hotspottoggler.ui;

import androidx.appcompat.app.AppCompatActivity;
import me.moonss.hotspottoggler.R;
import me.moonss.hotspottoggler.utilities.TetheringUtils;
import me.moonss.hotspottoggler.utilities.WifiApManager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    private ImageView tetheringImage;
    private TextView tetheringText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tetheringImage = findViewById(R.id.tetheringImg);
        tetheringText = findViewById(R.id.messageTextView);



        WifiApManager apManager = new WifiApManager(this);
        boolean isApEnabled = apManager.isWifiApEnabled();
        if (isApEnabled) {
            tetheringImage.setImageResource(R.drawable.ic_wifi_tethering_black_24dp);
            tetheringText.setText(getString(R.string.tethering_text));
            tetheringText.setTypeface(null, Typeface.BOLD);
        }

        initializeGoogleAdd();
    }

    //Google Add initialization
    private void initializeGoogleAdd() {
        MobileAds.initialize(this, getString(R.string.ADD_APP_ID));
        mAdView = findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void startTethering(View view) {
        WifiApManager wifiApManager = new WifiApManager(this);
        if (!wifiApManager.isWifiApEnabled()) {
            TetheringUtils.startTetheringService(this);
            tetheringImage.setImageResource(R.drawable.ic_wifi_tethering_black_24dp);
            tetheringText.setText(getString(R.string.tethering_text));
            tetheringText.setTypeface(null, Typeface.BOLD);

        } else {
            TetheringUtils.stopTetheringService(this);
            tetheringImage.setImageResource(R.drawable.ic_portable_wifi_off_black_24dp);
            tetheringText.setText(getString(R.string.not_tethering_text));
            tetheringText.setTypeface(null, Typeface.NORMAL);
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
        switch (id) {
            case R.id.action_settings:
                Intent startSettingActivity = new Intent(this, SettingsActivity.class);
                startActivity(startSettingActivity);
                return true;
            case R.id.action_about:
                Intent startAboutActivity = new Intent(this, AboutActivity.class);
                startActivity(startAboutActivity);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Called when leaving the activity */
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /** Called when returning to the activity */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /** Called before the activity is destroyed */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
