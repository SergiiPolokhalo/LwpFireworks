package ua.pp.kitson.trf;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ua.pp.kitson.trf.pool.RocketPool;
import ua.pp.kitson.trf.utils.Constants;
import ua.pp.kitson.trf.utils.WorldUtil;


/**
 * Create World with size from constants interface
 * Make it independent to physical screen density
 */
public class ThrRealFireworks extends ApplicationAdapter {
    World world;
    Viewport viewport;
    OrthographicCamera camera;
    SpriteBatch batch;


    @Override
    public void create() {
        world = WorldUtil.makeWorld();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        batch = new SpriteBatch();
        RocketPool.getInstance().activateRocket();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        RocketPool.getInstance().draw(batch);
        batch.end();
        world.step(Constants.WORLD_STEP, 6, 2);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        RocketPool.getInstance().activateRocket();
    }
}
