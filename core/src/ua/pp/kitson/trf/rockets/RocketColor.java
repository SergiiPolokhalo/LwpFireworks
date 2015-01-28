package ua.pp.kitson.trf.rockets;

import com.badlogic.gdx.math.MathUtils;

/**
 * Created by serhii on 1/20/15.
 */
public enum RocketColor {
    WHITE, YELLOW, BLUE, GREEN;

    public static RocketColor random() {
        switch (MathUtils.random(0, 3)) {
            case 0:
                return WHITE;
            case 1:
                return YELLOW;
            case 2:
                return BLUE;
            case 3:
                return GREEN;
            default:
                return WHITE;
        }
    }
}
