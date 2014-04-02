package jp.gr.java_conf.daisy.photo_taken_observer;

import android.util.Log;

public class PhotoTakenLogger {
    private static final String PHOTO_TAKEN_DETECTOR = "PhotoTakenDetector";

    static public void debugLog(Object obj) {
        Log.d(PHOTO_TAKEN_DETECTOR, obj == null ? "null" : obj.toString());
    }

    static public void logPhotoTaken(String via, String path, String mimeType) {
        Log.d(PHOTO_TAKEN_DETECTOR, String.format("Detect new photo with %s at %s (%s)", via, path, mimeType));
    }
}
