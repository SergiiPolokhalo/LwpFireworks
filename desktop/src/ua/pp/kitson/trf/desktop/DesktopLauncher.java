package ua.pp.kitson.trf.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ua.pp.kitson.trf.ThrRealFireworks;
import ua.pp.kitson.trf.utils.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = Constants.WORLD_WIDTH;
        config.height = Constants.WORLD_HEIGHT;
        new LwjglApplication(new ThrRealFireworks(new TimeUtilsDesctop()), config);
    }
}
