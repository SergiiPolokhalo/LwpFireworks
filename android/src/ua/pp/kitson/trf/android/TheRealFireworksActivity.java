package ua.pp.kitson.trf.android;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
//import Leadbolt classes
import com.appfireworks.android.listener.AppModuleListener;
import com.appfireworks.android.track.AppTracker;
import com.ddghmuedoolmvssforl.AdController;

public class TheRealFireworksActivity extends Activity {

    private AdController interstitial;
    private AdController audioad;
    private Activity act = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_real_fireworks);
        if(savedInstanceState == null) {
            initializeLeadBolt();
        }
        Toast toast = Toast.makeText(this, "Choose 'The Real Fireworks' from the list to start the Live Wallpaper.", Toast.LENGTH_LONG);
        toast.show();

        Intent intent = new Intent();
        intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
        startActivity(intent);
    }

    private void initializeLeadBolt() {
        audioad = new AdController(act, "707642171");
        audioad.loadAudioAd();
        AppTracker.startSession(act, "QsRtIFlA5PX2MdP8pMTuIqqTT3XFDiFc", new AppModuleListener() {
            @Override
            public void onModuleLoaded() {}
            @Override
            public void onModuleFailed() {
                loadDisplayAd();
            }
            @Override
            public void onModuleClosed() {}
            @Override
            public void onModuleCached() {}
        });
    }

    private void loadDisplayAd() {
        // use this else where in your app to load a Leadbolt Interstitial Ad
        interstitial = new AdController(act, "180520850");
        interstitial.loadAd();
    }

    public void onPause() {
        super.onPause();
        if (!isFinishing()) {
            AppTracker.pause(getApplicationContext());
        }
    }

    public void onResume() {
        super.onResume();
        AppTracker.resume(getApplicationContext());
    }

    public void onDestroy() {
        super.onDestroy();
        if(isFinishing()) {
            AppTracker.closeSession(getApplicationContext(), true);
        }
        if(audioad != null) {
            audioad.destroyAd();
        }
        if(interstitial != null) {
            interstitial.destroyAd();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_the_real_fireworks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
