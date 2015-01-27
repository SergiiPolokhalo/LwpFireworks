package ua.pp.kitson.trf.pool;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArraySet;
//import java.util.concurrent.

import ua.pp.kitson.trf.rockets.Rocket;
import ua.pp.kitson.trf.rockets.RocketColor;
import ua.pp.kitson.trf.rockets.RocketType;
import ua.pp.kitson.trf.utils.WorldUtil;

/**
 * Contain 100 Rocket object on pool
 * Have two collection -
 * FREE and ACTION  for objects
 * Created by serhii on 1/20/15.
 */
public class RocketPool {
    CopyOnWriteArraySet<Rocket> action = new CopyOnWriteArraySet<>();
    private static RocketPool INSTANCE = null;
    

    public synchronized static RocketPool getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RocketPool();
        }
        return INSTANCE;
    }

    public void addToDrawList(Rocket rocket) {
        action.add(rocket);
    }

    public void draw(SpriteBatch batch) {
        for (Rocket rocket : action) {
            rocket.drawEffect(batch);
            if (rocket.checkToFinish()) {
                action.remove(rocket);
                rocket.dispose();
            }
        }
    }

}
