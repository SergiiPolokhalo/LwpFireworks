package ua.pp.kitson.trf.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by serhii on 1/17/15.
 */
public interface Constants {
    int WORLD_WIDTH = 480;
    int WORLD_HEIGHT = 800;
    Vector2 GRAVITY = new Vector2(0, -10);
    float CANNON_X = 10f;
    float CANNON_Y = 10f;
    float WORLD_STEP = 1/60f;
    Vector2 SHOOT_VELOCITY = new Vector2(20, 100);
}
