package ua.pp.kitson.trf.rockets;

import com.badlogic.gdx.math.Vector2;

import ua.pp.kitson.trf.utils.Constants;

/**
 * Created by serhii on 1/20/15.
 */
public class SecondStageRocket extends FirstStageRocket {
    float initialX, initialY;
    int step = 0;
    int fadeFrom = 80;
    int maxStep = 100;

    @Override
    public Rocket setParams(Vector2 position, Vector2 speed) {
        initialX = position.x;
        initialY = position.y;
        step = 0;
        return super.setParams(position, speed);
    }

    @Override
    public boolean checkToFinish() {
        if (body != null) {
            float y = body.getPosition().y;
            float x = body.getPosition().x;
//            if (x > Constants.WORLD_WIDTH || x < 0 || y < 0 || y > Constants.WORLD_HEIGHT) {
//                return true;
//            }
            if (step > fadeFrom) {
                fadeOff();
            }
            if (step++>maxStep){
                return true;
            }

        }
        return false;
    }


}
