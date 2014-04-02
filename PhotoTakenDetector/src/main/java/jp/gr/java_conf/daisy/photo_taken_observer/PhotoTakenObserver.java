package jp.gr.java_conf.daisy.photo_taken_observer;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;


public class PhotoTakenObserver extends ContentObserver {
    private final Context mContext;

    public PhotoTakenObserver(Context context) {
        super(null);
        mContext = context;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange);
        Cursor cursor = mContext.getContentResolver().query(uri,
                new String[] {MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.MIME_TYPE},
                null,
                null,
                MediaStore.MediaColumns.DATE_ADDED + " DESC");
        cursor.moveToFirst();
        String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
        String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE));
        cursor.close();
        PhotoTakenLogger.logPhotoTaken("Observer", filePath, mimeType);
    }
}
