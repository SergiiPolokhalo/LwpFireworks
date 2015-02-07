package ua.pp.kitson.trf.android.utils;

import android.os.Handler;

import ua.pp.kitson.trf.utils.TimeUtils;

/**
 * Created by serhii on 2/7/15.
 */
public class TimeUtilsAndroid implements TimeUtils {
    Handler h = new Handler();

    @Override
    public void timeShift(Runnable launcher, int initialDelay) {
        h.postAtTime(launcher, initialDelay);
    }
}
