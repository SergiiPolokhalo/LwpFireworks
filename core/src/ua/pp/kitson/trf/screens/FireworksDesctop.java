package ua.pp.kitson.trf.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import ua.pp.kitson.trf.pool.RocketPool;
import ua.pp.kitson.trf.rockets.RocketColor;
import ua.pp.kitson.trf.rockets.RocketType;
import ua.pp.kitson.trf.utils.Constants;
import ua.pp.kitson.trf.utils.WorldUtil;

/**
 * Created by serhii on 1/23/15.
 */
public class FireworksDesctop extends FireworkBaseScreen implements InputProcessor {

    World world;
    OrthographicCamera camera;
    SpriteBatch batch;

    public FireworksDesctop(Game game) {
        super(game);
        Gdx.input.setInputProcessor(this);
        world = WorldUtil.makeWorld();
        camera = new OrthographicCamera(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        camera.position.set(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2, 0);
        batch = new SpriteBatch();
        RocketPool.getInstance().activateRocket(
                new Vector2(Constants.CANNON_X, Constants.CANNON_Y),
                Constants.SHOOT_VELOCITY,
                RocketType.FIRST,
                RocketColor.random());

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
        world.step(Constants.WORLD_STEP, 6, 2);

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
        float y = Constants.WORLD_HEIGHT - deltaH * screenY;
        float deltaW = (Constants.WORLD_WIDTH / w);
        float x = /*Constants.WORLD_WIDTH - */deltaW * screenX;

        RocketPool.getInstance().activateRocket(
                new Vector2(x, y),
                Constants.SHOOT_VELOCITY,
                RocketType.FIRST,
                RocketColor.random());
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


   /*




    @Override
    public void render() {
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        RocketPool.getInstance().activateRocket(
                new Vector2(Constants.CANNON_X + MathUtils.random(Constants.WORLD_WIDTH / 2), Constants.CANNON_Y),
                Constants.SHOOT_VELOCITY,
                RocketType.FIRST,
                RocketColor.random());
    }
    */
/*
    @Override
    public void render(float deltaTime) {

        if(worldController.isCollisionWithEnemy()) {

            game.setScreen(game.battleScreen);

        } else {

            if(!paused) {
                worldController.update(deltaTime);
            }

            Gdx.gl.glClearColor(57.0f / 255.0f, 181.0f / 225.0f, 115.0f / 255.0f, 1.0f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            worldRenderer.render();
        }

    }
*/
}
