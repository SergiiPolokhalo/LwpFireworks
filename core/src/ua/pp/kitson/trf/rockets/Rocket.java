package ua.pp.kitson.trf.rockets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by serhii on 1/16/15.
 */
public interface Rocket {

    boolean checkToFinish();

    Rocket drawEffect(SpriteBatch batch);

    Rocket setData(Body body);
    Body getData();

    Rocket setParams(Vector2 position, float speedX, float speedY);

    boolean checkParam(RocketType rocketType, RocketColor rocketColor);

    Rocket unblow();

    void dispose();

    void setColor(RocketColor rocketColor);
}
