package jp.gr.java_conf.daisy.photo_taken_observer;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;


public class PhotoTakenObserver extends ContentObserver {
    private final long LAST_TAKEN_PHOTO_THRESHOLD_MILLIS = 1000 * 60;
    private final Context mContext;
    private final Uri mObservedUri;

    public PhotoTakenObserver(Context context, Uri observedUri) {
        super(null);
        mContext = context;
        mObservedUri = observedUri;
    }

    @Override
    public void onChange(boolean selfChange) {
        onChange(selfChange, mObservedUri);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange);

        Cursor cursor = mContext.getContentResolver().query(uri,
                new String[]{MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DATE_ADDED,
                        MediaStore.MediaColumns.MIME_TYPE, MediaStore.Images.ImageColumns.DATE_TAKEN},
                null,
                null,
                MediaStore.MediaColumns.DATE_ADDED + " DESC");

        cursor.moveToFirst();
        int dateTakenIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN);
        Long dateTaken = cursor.getLong(dateTakenIndex);
        long dateAdded = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.DATE_ADDED)) * 1000;
        if (System.currentTimeMillis() - dateTaken > LAST_TAKEN_PHOTO_THRESHOLD_MILLIS) {
            PhotoTakenLogger.debugLog("old photo, ignored");
            cursor.close();
            PhotoTakenLogger.debugLog("taken:" + dateTaken + " added:" + dateAdded);
            return;
        }
        String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
        String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE));
        cursor.close();
        PhotoTakenLogger.logPhotoTaken("Observer", filePath, mimeType);
        PhotoTakenLogger.debugLog("taken:" + dateTaken + " added:" + dateAdded);
    }
}
