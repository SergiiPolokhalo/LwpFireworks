package ua.pp.kitson.trf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import ua.pp.kitson.trf.screens.FireworksDesctop;
import ua.pp.kitson.trf.utils.TimeUtils;


public class ThrRealFireworks extends Game {
    public static TimeUtils timeUtils;

    public ThrRealFireworks(TimeUtils timeUtils) {
        this.timeUtils = timeUtils;
    }

    @Override
    public void create() {
        FireworksDesctop desktop = new FireworksDesctop(this);
        desktop.setTimeUtils(this.timeUtils);
        setScreen(desktop);
    }
}
