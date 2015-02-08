package ua.pp.kitson.trf.android;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class TheRealFireworksActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_real_fireworks);
        Toast toast = Toast.makeText(this, "Choose 'The Real Fireworks' from the list to start the Live Wallpaper.", Toast.LENGTH_LONG);
        toast.show();

        Intent intent = new Intent();
        intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_the_real_fireworks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
