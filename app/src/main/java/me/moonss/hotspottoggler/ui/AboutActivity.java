package me.moonss.hotspottoggler.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import me.moonss.hotspottoggler.BuildConfig;
import me.moonss.hotspottoggler.R;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Element versionElement = new Element();
        versionElement.setTitle("Version: " + BuildConfig.VERSION_NAME);
        Element appTitle = new Element();
        appTitle.setTitle(getString(R.string.app_name));
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .addItem(appTitle)
                .setImage(R.drawable.ic_launcher)
                .addItem(versionElement)
                .addFacebook("munnacs")
                .addEmail("moons.dev@gmail.com")
                .addWebsite("http://moonss.me")
                .setDescription(getString(R.string.description))
                .create();


        setContentView(aboutPage);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
