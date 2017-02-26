package ksayker.customgooglesearch.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import ksayker.customgooglesearch.utils.UtilHasher;
import ksayker.customgooglesearch.utils.UtilBitmapFileManager;

/**
 * @author ksayker
 * @version 0.0.1
 * @since 25.02.17
 */
public class ImageLoadingTask extends AsyncTask<String, Void, Bitmap> {
    private Context mContext;

    private ImageLoadingResult mImageLoadingResult;

    public ImageLoadingTask(Context context, ImageLoadingResult imageLoadingResult){
        mContext = context;
        mImageLoadingResult = imageLoadingResult;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        return UtilBitmapFileManager.downloadBitmap(mContext, params[0],
                UtilHasher.md5(params[0]));
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        mImageLoadingResult.onImageLoadingResult(bitmap);
    }

    public interface ImageLoadingResult{
        void onImageLoadingResult(Bitmap bitmap);
    }
}
