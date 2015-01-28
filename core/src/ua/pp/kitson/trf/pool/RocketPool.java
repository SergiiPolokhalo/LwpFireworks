package ua.pp.kitson.trf.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import ua.pp.kitson.trf.rockets.Rocket;
import ua.pp.kitson.trf.rockets.RocketColor;

//import java.util.concurrent.

/**
 * Contain rockets and textures
 * Created by serhii on 1/20/15.
 */
public class RocketPool {
    CopyOnWriteArraySet<Rocket> action = new CopyOnWriteArraySet<>();
    private HashMap<RocketColor, Sprite> textures = new HashMap<>();
    private static RocketPool INSTANCE = null;

    private RocketPool() {
        textures.put(RocketColor.BLUE, new Sprite(new Texture(Gdx.files.internal("effects/BL.png"))));
        textures.put(RocketColor.WHITE, new Sprite(new Texture(Gdx.files.internal("effects/WH.png"))));
        textures.put(RocketColor.YELLOW, new Sprite(new Texture(Gdx.files.internal("effects/YL.png"))));
        textures.put(RocketColor.GREEN, new Sprite(new Texture(Gdx.files.internal("effects/GR.png"))));
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
