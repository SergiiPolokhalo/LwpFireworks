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
    CopyOnWriteArraySet<Rocket> free = new CopyOnWriteArraySet<>();
    CopyOnWriteArraySet<Rocket> action = new CopyOnWriteArraySet<>();
    CopyOnWriteArraySet<Rocket> finished = new CopyOnWriteArraySet<>();
//    private static HashSet<Rocket> free = new HashSet<>();
//    private static HashSet<Rocket> action = new HashSet<>();
    private static RocketPool INSTANCE = null;
//    private HashSet<Rocket> finished = new HashSet<>();

    private RocketPool() {
        //pre heat
        Vector2 pos = new Vector2(-1,-1);
        Vector2 spd = new Vector2(-2,-2);

        free.add(WorldUtil.makeRocket(RocketType.FIRST,RocketColor.random()).setParams(pos,spd));
        free.add(WorldUtil.makeRocket(RocketType.FIRST,RocketColor.random()).setParams(pos,spd));
        for (int i = 100;i>0;i--){
            free.add(WorldUtil.makeRocket(RocketType.SECOND,RocketColor.random()).setParams(pos,spd));
        }
    }

    public synchronized static RocketPool getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RocketPool();
        }
        return INSTANCE;
    }

    public synchronized Rocket activateRocket(Vector2       position,
                                              Vector2       speed,
                                              RocketType    rocketType,
                                              RocketColor   rocketColor) {
        Rocket rocket = null;
        for(Rocket r : free) {
            if (r.checkParam(rocketType,rocketColor)){
                rocket = r;
                break;
            }
        }
        if (rocket == null) {
            System.out.println("Cannot found "+rocketType + " " + rocketColor);
            rocket = WorldUtil.makeRocket(rocketType,rocketColor);
        } else {
            free.remove(rocket);
        }
        action.add(rocket.setParams(position,speed));
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
            if (rocket.checkToFinish()) {
                finished.add(rocket);
            }
        }
        if (!finished.isEmpty()) {
            action.removeAll(finished);
            free.addAll(finished);
            finished.clear();
        }
    }

}
