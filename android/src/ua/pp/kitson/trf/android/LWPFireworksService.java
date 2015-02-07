package ua.pp.kitson.trf.android;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidLiveWallpaperService;
import com.badlogic.gdx.backends.android.AndroidWallpaperListener;

import ua.pp.kitson.trf.ThrRealFireworks;
import ua.pp.kitson.trf.android.utils.TimeUtilsAndroid;

/**
 * Created by serhii on 1/29/15.
 */
public class LWPFireworksService extends AndroidLiveWallpaperService {
    @Override
    public void onCreateApplication() {
        super.onCreateApplication();
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//        config.useGL20 = false;
        config.useCompass = false;
        config.useWakelock = false;
        config.useAccelerometer = false;
        config.getTouchEventsForLiveWallpaper = true;

        ApplicationListener listener = new ThrRealFireworks(new TimeUtilsAndroid());
        initialize(listener, config);
    }
}
