package ua.pp.kitson.trf.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import ua.pp.kitson.trf.pool.RocketPool;
import ua.pp.kitson.trf.rockets.FirstStageRocket;
import ua.pp.kitson.trf.rockets.Rocket;
import ua.pp.kitson.trf.rockets.RocketColor;
import ua.pp.kitson.trf.rockets.RocketType;
import ua.pp.kitson.trf.rockets.SecondStageRocket;

/**
 * Created by serhii on 1/17/15.
 */
public class WorldUtil {

    private final static World world = new World(Constants.GRAVITY, true);

    public static World makeWorld() {
        return world;
    }

    public static Body createBody(BodyDef bodyDef) {
        return world.createBody(bodyDef);
    }

    public static Rocket makeObject(Rocket rocket, RocketType rocketType, RocketColor rocketColor) {

        //make bodyDef, body and particles
        BodyDef bodyDef = new BodyDef();
        bodyDef.fixedRotation = true;
        bodyDef.bullet = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.active = true;
        bodyDef.allowSleep = true;
        bodyDef.linearDamping = 0.01f;
        //bodyDef.position.set(Constants.CANNON_X, Constants.CANNON_Y);
        //bodyDef.linearVelocity.set(Constants.SHOOT_VELOCITY);
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
        String asset;
        switch (rocketColor) {
            case WHITE:
                asset = "effects/little.p";break;
            case YELLOW:
                asset = "effects/little1.p";break;
            case BLUE:
                asset = "effects/little2.p";break;
            default:
                throw new RuntimeException("Wrong color type");
        }
        particleEffect.load(Gdx.files.internal(asset), Gdx.files.internal("effects"));
        particleEffect.start();

        body.setUserData(new Object[]{rocketType, rocketType});

        rocket.setData(body, particleEffect);
        return rocket;
    }


    public static Rocket makeRocket(RocketType rocketType, RocketColor rocketColor)  {
        Rocket rocket;
        switch (rocketType){
            case FIRST:
                rocket = new FirstStageRocket();
            break;
            case SECOND:
                rocket = new SecondStageRocket();
            break;
            default:
                throw new RuntimeException("Wrong rocket type");
        }
        return makeObject(rocket, rocketType, rocketColor);
    }

    public static void blow(Vector2 position, int numRays, float blastPower) {
        for (int i=numRays;i>0;i--){
            float angle = (i / (float) numRays) * 360;
            Vector2 rayPower = new Vector2(MathUtils.sinDeg(angle) * blastPower, MathUtils.cosDeg(angle) * blastPower);
            Rocket rocket = RocketPool.getInstance().activateRocket(
                    position,
                    getSpeed(blastPower, angle),
                    RocketType.SECOND,
                    RocketColor.YELLOW
            );
        }

    }

    private static Vector2 getSpeed(float blastPower, float angle) {
        Vector2 speed = new Vector2();
        return speed.set(blastPower*MathUtils.cosDeg(angle),blastPower*MathUtils.sinDeg(angle));
    }
}
