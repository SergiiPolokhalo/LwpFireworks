package ua.pp.kitson.trf.rockets;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.Stack;

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

        public void draw(final SpriteBatch batch, final Sprite sprite, float x, float y) {
            for (FadingPathElement fadingPathElement : path) {
                    sprite.setX(fadingPathElement.getX());
                    sprite.setY(fadingPathElement.getY());
                    sprite.draw(batch,0.7f*((MAX_LIFE_LENGTH - fadingPathElement.age)/((float)MAX_LIFE_LENGTH)));
                    fadingPathElement.aging();
                    if (fadingPathElement.age < MAX_LIFE_LENGTH){
                        tmp.push(fadingPathElement);
                    }
                }
            path.clear();
            path.addAll(tmp);
            tmp.clear();
            batch.draw(sprite, x, y);
            path.push(new FadingPathElement(x,y));

        }

    }
    FadingPathController controller = new FadingPathController();
    protected RocketColor color;
    protected Body body;

    protected float lastY = -1f;
    private boolean disableBlow = false;
    private Sprite texture;
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
        this.texture = RocketPool.getInstance().getSprite(this.color);
        return this;
    }

    @Override
    public Body getData() {
        return this.body;
    }

    @Override
    public Rocket setParams(Vector2 position, float speedX, float speedY) {
        this.body.setTransform(position, 0f);//ignore angle
        this.body.setLinearVelocity(speedX, speedY);
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
    }

    @Override
    public void setColor(RocketColor rocketColor) {
        this.color = rocketColor;
    }

}
