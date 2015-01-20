package ua.pp.kitson.trf.rockets;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

import ua.pp.kitson.trf.utils.Constants;

/**
 * Created by serhii on 1/20/15.
 */
public class FirstStageRocket implements Rocket {
    private Body body;
    private ParticleEffect particleEffect;

    @Override
    public boolean isBlowable() {
        return false;
    }

    @Override
    public boolean checkToFinish() {
        if (body != null) {
            float y = body.getPosition().y;
            float x = body.getPosition().x;
            if (x > Constants.WORLD_WIDTH || x < 0 && y < 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean readyToClean() {
        return checkToFinish();
    }

    @Override
    public void drawEffect(SpriteBatch batch) {
        for (ParticleEmitter emitter : this.particleEffect.getEmitters()){
            emitter.setPosition(body.getPosition().x, body.getPosition().y);
            emitter.draw(batch, 1/60f);
            if (emitter.isComplete()){
                emitter.start();
            }
        }
    }

    @Override
    public void setData(Body body, ParticleEffect particleEffect) {
        this.particleEffect = particleEffect;
        this.body = body;
    }
}
