package ksayker.customgooglesearch.loaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.Loader;

import ksayker.customgooglesearch.network.ImageLoadingTask;

/**
 * @author ksayker
 * @version 0.0.1
 * @since 25.02.17
 */
public class ImageLoader extends Loader<Bitmap>
        implements ImageLoadingTask.ImageLoadingResult{
    public static final String IMAGE_URL_KEY = "IMAGE_URL_KEY";
    public static final int IMAGE_LOADER_ID = 2;

    private Context mContext;

    private String mImageUrl;

    public ImageLoader(Context context, Bundle bundle) {
        this(context);
        mContext = context;
        if (bundle != null){
            mImageUrl = bundle.getString(IMAGE_URL_KEY);
        }
    }

    public ImageLoader(Context context) {
        super(context);
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();

        ImageLoadingTask imageLoadingTask = new ImageLoadingTask(mContext, this);
        imageLoadingTask.execute(mImageUrl);
    }

    @Override
    public void onImageLoadingResult(Bitmap bitmap) {
        deliverResult(bitmap);
    }
}
