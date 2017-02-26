package ksayker.customgooglesearch.models;

import android.graphics.Bitmap;

/**
 * @author ksayker
 * @version 0.0.1
 * @since 19.02.17
 */
public class ItemSearchData {
    private Bitmap mBitmap;
    private String mName;
    private String mLink;
    private boolean isFavorite;

    public ItemSearchData(Bitmap bitmap, String name, String link, boolean isFavorite) {
        mBitmap = bitmap;
        mName = name;
        mLink = link;
        this.isFavorite = isFavorite;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public String getName() {
        return mName;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getLink() {
        return mLink;
    }
}
