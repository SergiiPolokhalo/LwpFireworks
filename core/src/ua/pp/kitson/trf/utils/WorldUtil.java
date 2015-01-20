package ua.pp.kitson.trf.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import ua.pp.kitson.trf.rockets.FirstStageRocket;
import ua.pp.kitson.trf.rockets.Rocket;

/**
 * Created by serhii on 1/17/15.
 */
public class WorldUtil {

    private final static World world = new World(Constants.GRAVITY, true);

    public static World makeWorld() {
        return world;
    }

    public static FirstStageRocket makeFirstStageRocket() {
        FirstStageRocket rocket = new FirstStageRocket();
        return (FirstStageRocket) makeObject(rocket);
    }

    public static Body createBody(BodyDef bodyDef) {
        return world.createBody(bodyDef);
    }

    public static Rocket makeObject(Rocket rocket) {

        //make bodyDef, body and particles
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = true;
        bodyDef.bullet = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.active = true;
        bodyDef.allowSleep = true;
        bodyDef.linearDamping = 0.01f;
        bodyDef.position.set(Constants.CANNON_X, Constants.CANNON_Y);
        bodyDef.linearVelocity.set(Constants.SHOOT_VELOCITY);
        Body body = createBody(bodyDef);
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.01f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.friction = 0;
        fixtureDef.filter.groupIndex = -1;
        body.createFixture(fixtureDef);
        circleShape.dispose();

        ParticleEffect particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal("effects/little.p"), Gdx.files.internal("effects"));
        particleEffect.start();

        rocket.setData(body, particleEffect);
        return rocket;
    }
}
