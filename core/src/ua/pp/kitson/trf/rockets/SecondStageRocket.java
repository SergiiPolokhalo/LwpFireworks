package ua.pp.kitson.trf.rockets;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by serhii on 1/20/15.
 */
public class SecondStageRocket extends FirstStageRocket {
    private float initialX;
    private float initialY;
    private int step = 0;
    private final int maxStep = 200;

    @Override
    public Rocket setParams(Vector2 position, float speedX, float speedY) {
        initialX = position.x;
        initialY = position.y;
        step = 0;
        return super.setParams(position, speedX, speedY);
    }

    @Override
    public boolean checkToFinish() {
        if (body != null) {
            if (step++>maxStep){
                return true;
            }
        }
        return false;
    }


}
