package jp.gr.java_conf.daisy.photo_taken_observer;

import android.app.Service;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;

public class PhotoTakenObserverService extends Service {
    private PhotoTakenObserver[] photoTakenObservers;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Uri[] observeUri = new Uri[] {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.INTERNAL_CONTENT_URI
        };
        photoTakenObservers = new PhotoTakenObserver[observeUri.length];
        for (int i = 0; i < observeUri.length; i++) {
            photoTakenObservers[i] = new PhotoTakenObserver(this, observeUri[i]);
            getApplicationContext()
                    .getContentResolver()
                    .registerContentObserver(
                            observeUri[i],
                            false,
                            photoTakenObservers[i]);
        }
        PhotoTakenLogger.debugLog("Service created / register observer");
    }

    @Override
    public void onDestroy() {
        for (ContentObserver observer: photoTakenObservers) {
            getApplicationContext().getContentResolver().unregisterContentObserver(observer);
        }
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
