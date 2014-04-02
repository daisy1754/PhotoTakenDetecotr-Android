package jp.gr.java_conf.daisy.photo_taken_observer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {
    private PhotoTakenObserver photoTakenObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photoTakenObserver = new PhotoTakenObserver(this);
        getApplicationContext()
                .getContentResolver()
                .registerContentObserver(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        false,
                        photoTakenObserver);
        getApplicationContext()
                .getContentResolver()
                .registerContentObserver(
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                        false,
                        photoTakenObserver);
        PhotoTakenLogger.debugLog("Register observer");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.getApplicationContext().getContentResolver()
                .unregisterContentObserver(photoTakenObserver);
        PhotoTakenLogger.debugLog("Unregister observer");
    }
}
