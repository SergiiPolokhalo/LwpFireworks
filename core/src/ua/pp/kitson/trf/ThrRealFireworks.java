package ua.pp.kitson.trf;

import com.badlogic.gdx.Game;

import ua.pp.kitson.trf.screens.FireworksDesctop;


public class ThrRealFireworks extends Game {
    @Override
    public void create() {
        setScreen(new FireworksDesctop(this));
    }
}
