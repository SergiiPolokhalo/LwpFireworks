package ua.pp.kitson.trf.rockets;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import ua.pp.kitson.trf.pool.RocketPool;
import ua.pp.kitson.trf.utils.Constants;
import ua.pp.kitson.trf.utils.ParticleStorage;
import ua.pp.kitson.trf.utils.WorldUtil;

/**
 * Created by serhii on 1/20/15.
 */
public class FirstStageRocket implements Rocket {
    protected RocketColor color;
    protected Body body;

    protected float lastY = -1f;
    private boolean disableBlow = false;
    private ParticleEffectPool.PooledEffect effect;

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

    protected void blowMe() {
        if (!disableBlow) {
            Vector2 pos = new Vector2(body.getPosition());
            //RocketPool.getInstance().deactivateRocket(this);
            WorldUtil.blow(pos, 32, 100, this.color);
        }
    }

    @Override
    public Rocket drawEffect(SpriteBatch batch) {
        float x = body.getPosition().x;
        float y = body.getPosition().y;
        this.effect.setPosition(x,y);
        this.effect.draw(batch, Constants.WORLD_STEP);
        if (this.effect.isComplete()) {
            this.effect.reset();
        }
        return this;
    }

    @Override
    public Rocket setData(Body body) {
        this.body = body;
        return this;
    }

    @Override
    public Rocket setParams(Vector2 position, Vector2 speed) {
        this.body.setTransform(position, 0f);//ignore angle
        this.body.setLinearVelocity(speed.x,speed.y);
        this.effect.setPosition(position.x,position.y);
        return this;
    }

    @Override
    public boolean checkParam(RocketType rocketType, RocketColor rocketColor) {
        Object[] arr = (Object[]) this.body.getUserData();
        if (arr[0]==rocketType && arr[1]==rocketColor) {
            return  true;
        }
        return false;
    }

    @Override
    public Rocket unblow() {
        this.disableBlow = true;
        return this;
    }

    @Override
    public void dispose() {
        disableBlow = true;
        WorldUtil.makeWorld().destroyBody(this.body);
        this.effect.free();
    }

    @Override
    public void setColor(RocketColor rocketColor) {
        this.color = rocketColor;
        this.effect = ParticleStorage.getInstance().getEffect(rocketColor,new Vector2(0,0));
    }

}
