package jp.gr.java_conf.daisy.photo_taken_observer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;

public class NewPictureReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Cursor cursor = context.getContentResolver().query(intent.getData(),
                new String[] {MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.MIME_TYPE},
                null, null, null);
        cursor.moveToFirst();
        String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
        String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE));
        cursor.close();
        PhotoTakenLogger.logPhotoTaken("Broadcast Receiver", filePath, mimeType);
    }
}
