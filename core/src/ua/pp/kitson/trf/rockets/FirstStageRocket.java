package ua.pp.kitson.trf.rockets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.Stack;
import java.util.function.Consumer;

import ua.pp.kitson.trf.pool.RocketPool;
import ua.pp.kitson.trf.utils.WorldUtil;

/**
 * Created by serhii on 1/20/15.
 */
public class FirstStageRocket implements Rocket {
    //collection of previous coodinates
    public class FadingPathElement {
        int age = 0;
        float x,y;

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public FadingPathElement(float x, float y) {
            this.x = x;
            this.y = y;
        }
        public void aging(){
            age++;
        }
    }
    public class FadingPathController {
        public static final int MAX_LIFE_LENGTH = 30;
        Stack<FadingPathElement> path = new Stack<>();
        Stack<FadingPathElement> tmp = new Stack<>();

        public void draw(final SpriteBatch batch, final Texture texture, float x, float y){
            path.forEach(new Consumer<FadingPathElement>() {
                @Override
                public void accept(FadingPathElement fadingPathElement) {
                    Sprite sprite = new Sprite(texture);
                    sprite.setX(fadingPathElement.getX());
                    sprite.setY(fadingPathElement.getY());
                    sprite.draw(batch,0.7f*((MAX_LIFE_LENGTH - fadingPathElement.age)/((float)MAX_LIFE_LENGTH)));
                    fadingPathElement.aging();
                    if (fadingPathElement.age < MAX_LIFE_LENGTH){
                        tmp.push(fadingPathElement);
                    }
                }
            });
            path.clear();
            path.addAll(tmp);
            tmp.clear();
            batch.draw(texture,x,y);
            path.push(new FadingPathElement(x,y));

        }

    }
    FadingPathController controller = new FadingPathController();
    protected RocketColor color;
    protected Body body;

    protected float lastY = -1f;
    private boolean disableBlow = false;
    private Texture texture;
    //private ParticleEffectPool.PooledEffect effect;

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
            WorldUtil.blow(pos, 32, 100, this.color);
        }
    }

    @Override
    public Rocket drawEffect(SpriteBatch batch) {
        float x = body.getPosition().x;
        float y = body.getPosition().y;
        controller.draw(batch,texture,x,y);
        return this;
    }

    @Override
    public Rocket setData(Body body) {
        this.body = body;
        this.texture = new Texture(Gdx.files.internal("effects/particle.png"));
        return this;
    }

    @Override
    public Body getData() {
        return this.body;
    }

    @Override
    public Rocket setParams(Vector2 position, Vector2 speed) {
        this.body.setTransform(position, 0f);//ignore angle
        this.body.setLinearVelocity(speed.x,speed.y);
//        this.effect.setPosition(position.x,position.y);
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
        texture.dispose();
    }

    @Override
    public void setColor(RocketColor rocketColor) {
        this.color = rocketColor;
    }

}
