package ua.pp.kitson.trf.rockets;

import ua.pp.kitson.trf.utils.Constants;

/**
 * Created by serhii on 1/20/15.
 */
public class SecondStageRocket extends FirstStageRocket {
    @Override
    public boolean checkToFinish() {
        if (body != null) {
            float y = body.getPosition().y;
            float x = body.getPosition().x;
            if (x > Constants.WORLD_WIDTH || x < 0 && y < 0) {
                return true;
            }
        }
        return false;
    }
}
