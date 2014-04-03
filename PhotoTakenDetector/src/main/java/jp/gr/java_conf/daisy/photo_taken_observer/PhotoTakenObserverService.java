package jp.gr.java_conf.daisy.photo_taken_observer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.MediaStore;

public class PhotoTakenObserverService extends Service {
    private PhotoTakenObserver photoTakenObserver;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
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
        PhotoTakenLogger.debugLog("Service created / register observer");
    }

    @Override
    public void onDestroy() {
        getApplicationContext().getContentResolver()
                .unregisterContentObserver(photoTakenObserver);
        PhotoTakenLogger.debugLog("Service destroy / Unregister observer");
        super.onDestroy();
    }

    /**
     * @return NULL
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
