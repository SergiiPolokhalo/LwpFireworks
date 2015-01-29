package ua.pp.kitson.trf.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import ua.pp.kitson.trf.pool.RocketPool;
import ua.pp.kitson.trf.rockets.Rocket;
import ua.pp.kitson.trf.rockets.RocketColor;
import ua.pp.kitson.trf.rockets.RocketType;
import ua.pp.kitson.trf.utils.Constants;
import ua.pp.kitson.trf.utils.WorldUtil;

import static ua.pp.kitson.trf.utils.Constants.WORLD_WIDTH;
import static ua.pp.kitson.trf.utils.Constants.WORLD_HEIGHT;
import static ua.pp.kitson.trf.utils.Constants.CANNON_X;
import static ua.pp.kitson.trf.utils.Constants.CANNON_Y;
import static ua.pp.kitson.trf.utils.Constants.CANNON_X_MAX;
import static ua.pp.kitson.trf.utils.Constants.SHOOT_VELOCITY;


/**
 * Created by serhii on 1/23/15.
 */
public class FireworksDesctop extends FireworkBaseScreen implements InputProcessor {

    final World world;
    final OrthographicCamera camera;
    final SpriteBatch batch;

    public FireworksDesctop(Game game) {
        super(game);
        Gdx.input.setInputProcessor(this);
        world = WorldUtil.makeWorld();
        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        batch = new SpriteBatch();
        Rocket rocket;
        rocket = WorldUtil.makeRocket(RocketType.FIRST,
                RocketColor.random());
        rocket.setParams(new Vector2(Constants.CANNON_X, CANNON_Y), SHOOT_VELOCITY.x, SHOOT_VELOCITY.y);
        RocketPool.getInstance().addToDrawList(rocket);
        Runnable runner = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(Constants.TIME_SLEEP);
                        if (!world.isLocked()) {
                            Rocket rocket;
                            rocket = WorldUtil.makeRocket(RocketType.FIRST,
                                    RocketColor.random());
                            float xPos = MathUtils.random(Constants.CANNON_X, CANNON_X_MAX);
                            float sSpeed = SHOOT_VELOCITY.y * (((WORLD_WIDTH - 2 * CANNON_X) / 2 - xPos) / WORLD_WIDTH);
                            rocket.setParams(new Vector2(xPos, CANNON_Y), sSpeed, MathUtils.random((int) (SHOOT_VELOCITY.y * 0.75), (int) (SHOOT_VELOCITY.y * 1.25)));
                            RocketPool.getInstance().addToDrawList(rocket);
                        }
                    } catch (InterruptedException e) {

                    }
                }
            }
        };
        new Thread(runner).start();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        RocketPool.getInstance().draw(batch);
        batch.end();
        synchronized (this) {
            world.step(Constants.WORLD_STEP, 1, 1);
        }

    }

    @Override
    public void dispose() {
        super.dispose();
        world.dispose();
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        /*
        Screen zero in top left
         */
        float h = Gdx.graphics.getHeight();
        float w = Gdx.graphics.getWidth();
        float deltaH = (Constants.WORLD_HEIGHT / h);
        float y = WORLD_HEIGHT - deltaH * screenY;
        float deltaW = (WORLD_WIDTH / w);
        float x = /*Constants.WORLD_WIDTH - */deltaW * screenX;
        Rocket rocket;
        rocket = WorldUtil.makeRocket(RocketType.FIRST,
                RocketColor.random());
        rocket.setParams(new Vector2(x, y), SHOOT_VELOCITY.x, SHOOT_VELOCITY.y);
        RocketPool.getInstance().addToDrawList(rocket);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


}
