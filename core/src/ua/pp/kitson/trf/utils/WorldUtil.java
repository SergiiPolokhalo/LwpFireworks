package ua.pp.kitson.trf.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import ua.pp.kitson.trf.ThrRealFireworks;
import ua.pp.kitson.trf.pool.RocketPool;
import ua.pp.kitson.trf.rockets.FirstStageRocket;
import ua.pp.kitson.trf.rockets.Rocket;
import ua.pp.kitson.trf.rockets.RocketColor;
import ua.pp.kitson.trf.rockets.RocketType;
import ua.pp.kitson.trf.rockets.SecondStageRocket;
import ua.pp.kitson.trf.rockets.ThirdStageRocket;

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

        int i = 0;
        do {
            i++;
        } while (world.isLocked());
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
        body.setUserData(new Object[]{rocketType, rocketColor});

        rocket.setData(body);
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
            case THIRD:
                rocket = new ThirdStageRocket();
                break;
            default:
                throw new RuntimeException("Wrong rocket type");
        }
        rocket.setColor(rocketColor);
        return makeObject(rocket, rocketType, rocketColor);
    }

//    public static void blow(Vector2 position, int numRays, float blastPower, RocketColor rocketColor) {
//        float angle = (1f / (float) numRays) * 360;
//        float currAngle = angle;
//        for (int i=numRays;i>0;i--){
//            Rocket rocket;
//            rocket = WorldUtil.makeRocket(RocketType.SECOND,
//                    rocketColor);
//            rocket.setParams(position, blastPower * MathUtils.cosDeg(currAngle), blastPower * MathUtils.sinDeg(currAngle));
//            currAngle+=angle;
//            RocketPool.getInstance().addToDrawList(rocket);
//        }
//
//    }

    public static class DelayedRocketLaunch implements Runnable {
        RocketType rocketType;
        RocketColor rocketColor;
        Vector2 position;
        float xSpeed, ySpeed;

        public DelayedRocketLaunch(RocketType rocketType, RocketColor rocketColor, Vector2 position, float xSpeed, float ySpeed) {
            this.rocketType = rocketType;
            this.rocketColor = rocketColor;
            this.position = position;
            this.xSpeed = xSpeed;
            this.ySpeed = ySpeed;
        }

        @Override
        public void run() {
            Rocket rocket;
            rocket = WorldUtil.makeRocket(rocketType, rocketColor);
            rocket.setParams(position, xSpeed, ySpeed);
            RocketPool.getInstance().addToDrawList(rocket);
        }
    }

    public static void blow(Vector2 position, int numRays, float blastPower, RocketColor rocketColor, RocketType rocketType) {
        float angle = (1f / (float) numRays) * 360;
        float currAngle = angle;
        switch (rocketType) {
            case THIRD:
                //do delay blow
                for (int i = numRays; i > 0; i--) {
                    DelayedRocketLaunch launcher = new DelayedRocketLaunch(rocketType, rocketColor, position, blastPower * MathUtils.cosDeg(currAngle), blastPower * MathUtils.sinDeg(currAngle));
                    ThrRealFireworks.timeUtils.timeShift(launcher, ThirdStageRocket.INITIAL_DELAY);
                    currAngle += angle;
                }
                break;
            default:
                for (int i = numRays; i > 0; i--) {
                    Rocket rocket;
                    rocket = WorldUtil.makeRocket(RocketType.SECOND,
                            rocketColor);
                    rocket.setParams(position, blastPower * MathUtils.cosDeg(currAngle), blastPower * MathUtils.sinDeg(currAngle));
                    currAngle += angle;
                    RocketPool.getInstance().addToDrawList(rocket);
                }
        }
    }

}
