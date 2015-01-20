package ua.pp.kitson.trf.rockets;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by serhii on 1/16/15.
 */
public interface Rocket {
    boolean isBlowable();

    boolean checkToFinish();

    boolean readyToClean();

    void drawEffect(SpriteBatch batch);

    void setData(Body body, ParticleEffect particleEffect);

    void setParams(Vector2 position, Vector2 speed);

    boolean checkParam(RocketType rocketType, RocketColor rocketColor);
}
