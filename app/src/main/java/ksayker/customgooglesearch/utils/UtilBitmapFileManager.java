package ksayker.customgooglesearch.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author ksayker
 * @version 0.0.1
 * @since 25.02.17
 */
public class UtilBitmapFileManager {

    public static Bitmap downloadBitmap(Context context, String url, String hashKey) {
        Bitmap result = null;
        File file = new File(context.getExternalCacheDir(), hashKey + ".cache");
        try {
            if (!file.exists()) {
                file.createNewFile();
                fileSave(new URL(url).openStream(), new FileOutputStream(file));
            }
            result = BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void fileSave(InputStream inputStream,
                                FileOutputStream fileOutputStream){
        int i;
        try {
            while ((i = inputStream.read()) != -1){
                fileOutputStream.write(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
