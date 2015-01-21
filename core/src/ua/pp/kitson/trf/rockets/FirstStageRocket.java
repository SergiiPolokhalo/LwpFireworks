package ua.pp.kitson.trf.rockets;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import ua.pp.kitson.trf.utils.Constants;

/**
 * Created by serhii on 1/20/15.
 */
public class FirstStageRocket implements Rocket {
    protected Body body;
    private ParticleEffect particleEffect;

    protected float lastY = -1f;

    @Override
    public boolean checkToFinish() {
        if (body != null) {
            float y = body.getPosition().y;
            if (y<lastY) {
                blowMe();
                return true;
            }
            lastY=y;
        }
        return false;
    }

    private void blowMe() {
        //TODO make blow with SecondStageRockets
        System.out.println("Blow me");
    }

    @Override
    public void drawEffect(SpriteBatch batch) {
        float x = body.getPosition().x;
        float y = body.getPosition().y;
        for (ParticleEmitter emitter : this.particleEffect.getEmitters()){
            emitter.setPosition(x, y);
            emitter.draw(batch,Constants.WORLD_STEP);
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

    @Override
    public void setParams(Vector2 position, Vector2 speed) {
        this.body.setTransform(position, 0f);//ignore angle
        this.body.setLinearVelocity(speed.x,speed.y);
        for (ParticleEmitter emitter:this.particleEffect.getEmitters()){
            emitter.setPosition(position.x,position.y);
        }
    }

    @Override
    public boolean checkParam(RocketType rocketType, RocketColor rocketColor) {
        Object[] arr = (Object[]) this.body.getUserData();
        if (arr[0]==rocketType && arr[1]==rocketColor) {
            return  true;
        }
        return false;
    }
}
