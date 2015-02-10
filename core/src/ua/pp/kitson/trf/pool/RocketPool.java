package ua.pp.kitson.trf.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;

import ua.pp.kitson.trf.rockets.Rocket;
import ua.pp.kitson.trf.rockets.RocketColor;
import ua.pp.kitson.trf.rockets.RocketType;
import ua.pp.kitson.trf.utils.WorldUtil;

//import java.util.concurrent.

/**
 * Contain rockets and textures
 * Created by serhii on 1/20/15.
 */
public class RocketPool {
    private static final long MS_STEP = 600;
    private static int MAX_COUNT = 256;
    private long lastDrawTime = System.currentTimeMillis();

    public void setLastDrawTime(long lastDrawTime) {
        this.lastDrawTime = lastDrawTime;
    }

    public long getLastDrawTime() {
        return lastDrawTime;
    }

    public boolean isShown() {
        return (getLastDrawTime() + MS_STEP) > System.currentTimeMillis();
    }
    public static class ReadyData {
        RocketType rocketType;
        RocketColor rocketColor;
        Vector2 position;
        float xSpeed;
        float ySpeed;

        public ReadyData(RocketType rocketType, RocketColor rocketColor, Vector2 position, float xSpeed, float ySpeed) {
            this.rocketType = rocketType;
            this.rocketColor = rocketColor;
            this.position = position;
            this.xSpeed = xSpeed;
            this.ySpeed = ySpeed;
        }
    }

    //make concurrent list/stack
    ConcurrentLinkedQueue<ReadyData> readyDatas = new ConcurrentLinkedQueue<>();
    private final CopyOnWriteArraySet<Rocket> action = new CopyOnWriteArraySet<>();
    private final HashMap<RocketColor, Sprite> textures = new HashMap<>();
    private static RocketPool INSTANCE = null;

    private RocketPool() {
        textures.put(RocketColor.BLUE, new Sprite(new Texture(Gdx.files.internal("effects/BL.png"))));
        textures.put(RocketColor.WHITE, new Sprite(new Texture(Gdx.files.internal("effects/WH.png"))));
        textures.put(RocketColor.YELLOW, new Sprite(new Texture(Gdx.files.internal("effects/YL.png"))));
        textures.put(RocketColor.GREEN, new Sprite(new Texture(Gdx.files.internal("effects/GR.png"))));
    }

    public static boolean acceptable() {
        boolean res = false;
        if (INSTANCE.action.size() < MAX_COUNT) {
            res = true;
        }
        return res;
    }

    public Sprite getSprite(RocketColor rocketColor) {
        return textures.get(rocketColor);
    }

    public synchronized static RocketPool getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RocketPool();
        }
        return INSTANCE;
    }

    public void addToDrawList(Rocket rocket) {
        action.add(rocket);
    }

    public void addToReadyList(RocketType rocketType, RocketColor rocketColor, Vector2 position, float xSpeed, float ySpeed) {
        readyDatas.add(new ReadyData(rocketType, rocketColor, position, xSpeed, ySpeed));
    }

    public void moveReadyToDraw() {
        //LAST TIME DRAW
        setLastDrawTime(System.currentTimeMillis());
        while (!readyDatas.isEmpty() && acceptable()) {
            ReadyData data = readyDatas.poll();
            Rocket rocket;
            rocket = WorldUtil.makeRocket(data.rocketType, data.rocketColor);
            rocket.setParams(data.position, data.xSpeed, data.ySpeed);
            addToDrawList(rocket);
        }
    }

    public void draw(SpriteBatch batch) {
        for (Rocket rocket : action) {
            rocket.drawEffect(batch);
            if (rocket.checkToFinish()) {
                action.remove(rocket);
                rocket.dispose();
            }
        }
    }

}
