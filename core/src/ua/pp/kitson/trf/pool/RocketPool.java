package ua.pp.kitson.trf.pool;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashSet;

import ua.pp.kitson.trf.rockets.Rocket;
import ua.pp.kitson.trf.utils.WorldUtil;

/**
 * Contain 100 Rocket object on pool
 * Have two collection -
 * FREE and ACTION  for objects
 * Created by serhii on 1/20/15.
 */
public class RocketPool {
    private static HashSet<Rocket> free = new HashSet<>();
    private static HashSet<Rocket> action = new HashSet<>();
    private static RocketPool INSTANCE = null;

    private RocketPool() {

    }

    public synchronized static RocketPool getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RocketPool();
        }
        return INSTANCE;
    }

    public synchronized Rocket activateRocket() {
        Rocket rocket;
        if (!free.isEmpty()) {
            rocket = free.iterator().next();
            free.remove(rocket);
        } else {
            rocket = WorldUtil.makeFirstStageRocket();
        }
        action.add(rocket);
        return rocket;

    }

    public void deactivateRocket(Rocket rocket) {
        try {
            action.remove(rocket);
            free.add(rocket);
        } catch (Exception e) {
            System.out.println("ERROR deactivateRocket");
        }
    }

    public void draw(SpriteBatch batch) {
        for (Rocket rocket : action) {
            rocket.drawEffect(batch);
        }
    }
}
