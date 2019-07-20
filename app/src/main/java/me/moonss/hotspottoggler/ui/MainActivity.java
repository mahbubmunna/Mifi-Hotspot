package me.moonss.hotspottoggler.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import me.moonss.hotspottoggler.R;
import me.moonss.hotspottoggler.utilities.TetheringUtils;
import me.moonss.hotspottoggler.utilities.WifiApManager;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private AdView mAdView;
    private LottieAnimationView tetheringAnim;
    private TextView tetheringText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting toolbar as action bar
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //Setting Image Views
        tetheringAnim = findViewById(R.id.tetheringAnim);
        tetheringText = findViewById(R.id.messageTextView);



        WifiApManager apManager = new WifiApManager(this);
        boolean isApEnabled = apManager.isWifiApEnabled();
        if (isApEnabled) {
            tetheringAnim.setAnimation("animation-hotspot-green.json");
            tetheringAnim.playAnimation();
            tetheringAnim.setRepeatCount(LottieDrawable.INFINITE);
            tetheringText.setText(getString(R.string.tethering_text_on));
        }

        //Click handle of Tethering Animation
        tetheringAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTethering(v);
            }
        });

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
           // tetheringAnim.setImageResource(R.drawable.ic_wifi_tethering_black_24dp);
            tetheringAnim.setAnimation("animation-hotspot-green.json");
            tetheringAnim.playAnimation();
            tetheringAnim.setRepeatCount(LottieDrawable.INFINITE);
            tetheringText.setText(getString(R.string.tethering_text_on));

        } else {
            TetheringUtils.stopTetheringService(this);
            //tetheringAnim.setImageResource(R.drawable.ic_portable_wifi_off_black_24dp);
            tetheringAnim.pauseAnimation();
            tetheringAnim.setAnimation("animation-hotspot-red.json");
            tetheringAnim.playAnimation();
            tetheringAnim.pauseAnimation();
            tetheringText.setText(getString(R.string.not_tethering_text));
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
            case R.id.action_privacy_policy:
                String url = "http://moonss.me/apps/wifi_hotspot/privacy_policy/";
                Uri webPage = Uri.parse(url);
                Intent startPrivacyPolicy =
                        new Intent(Intent.ACTION_VIEW, webPage);
                if (startPrivacyPolicy.
                        resolveActivity(getPackageManager()) != null)
                {
                    startActivity(startPrivacyPolicy);
                }
                return true;
            case R.id.action_exit:
                finish();
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
