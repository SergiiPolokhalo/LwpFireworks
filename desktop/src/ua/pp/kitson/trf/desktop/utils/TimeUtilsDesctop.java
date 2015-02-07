package ua.pp.kitson.trf.desktop.utils;

import java.util.Timer;
import java.util.TimerTask;

import ua.pp.kitson.trf.utils.TimeUtils;

/**
 * Created by serhii on 2/7/15.
 */
public class TimeUtilsDesctop implements TimeUtils {
    Timer timer;

    {
        timer = new Timer();
    }

    @Override
    public void timeShift(final Runnable launcher, final int initialDelay) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                new Thread(launcher).start();
            }
        };
        timer.schedule(timerTask, initialDelay);
    }
}
