package ua.pp.kitson.trf;

import com.badlogic.gdx.Game;

import ua.pp.kitson.trf.screens.FireworksDesctop;
import ua.pp.kitson.trf.utils.TimeUtils;


public class ThrRealFireworks extends Game {
    public static TimeUtils timeUtils;

    public ThrRealFireworks(TimeUtils timeUtils) {
        this.timeUtils = timeUtils;
    }

    @Override
    public void create() {
        setScreen(new FireworksDesctop(this));
    }
}
