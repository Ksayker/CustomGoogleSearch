package ksayker.customgooglesearch.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.io.IOException;
import java.net.URL;

/**
 * @author ksayker
 * @version 0.0.1
 * @since 23.02.17
 */
public class UtilBitmapDecoder {
    public static Bitmap decodeSampledBitmapFromResource(URL url, Rect rect,
                                                         int reqWidth, int reqHeight){
        Bitmap result = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(
                    url.openConnection().getInputStream(),
                    rect,
                    options);

            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            options.inJustDecodeBounds = false;
            result = BitmapFactory.decodeStream(
                    url.openConnection().getInputStream(),
                    rect,
                    options);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
