package ksayker.customgooglesearch.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import ksayker.customgooglesearch.R;
import ksayker.customgooglesearch.loaders.ImageLoader;

/**
 * @author ksayker
 * @version 0.0.1
 * @since 18.02.17
 */
public class ImagePageFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Bitmap>{
    private ImageView mImageView;
    private RelativeLayout mRlProgressHolder;

    public ImagePageFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(
                ImageLoader.IMAGE_LOADER_ID, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page_image, null);

        mImageView = (ImageView) view.findViewById(R.id.fragment_page_image_image);
        mRlProgressHolder = (RelativeLayout) view.findViewById(
                R.id.fragment_page_image_progress_holder);
        return view;
    }

    @Override
    public Loader<Bitmap> onCreateLoader(int id, Bundle args) {
        Loader<Bitmap> loader = null;
        if(id == ImageLoader.IMAGE_LOADER_ID){
            loader = new ImageLoader(getContext(), args);
        }

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Bitmap> loader, Bitmap data) {
        mRlProgressHolder.setVisibility(View.GONE);
        mImageView.setVisibility(View.VISIBLE);

        mImageView.setImageBitmap(data);
    }

    @Override
    public void onLoaderReset(Loader<Bitmap> loader) {
    }

    public void forceImageLoad(String url){
        mRlProgressHolder.setVisibility(View.VISIBLE);
        mImageView.setVisibility(View.GONE);

        Bundle bundle = new Bundle();
        bundle.putString(ImageLoader.IMAGE_URL_KEY, url);
        getLoaderManager().restartLoader(ImageLoader.IMAGE_LOADER_ID, bundle, this)
            .forceLoad();
    }
}
