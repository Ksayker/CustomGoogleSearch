package ksayker.customgooglesearch.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ksayker.customgooglesearch.activities.MainActivity;
import ksayker.customgooglesearch.fragments.ImagePageFragment;
import ksayker.customgooglesearch.fragments.SearchPageFragment;

/**
 * @author ksayker
 * @version 0.0.1
 * @since 18.02.17
 */
public class ImageSearchFragmentPageAdapter extends FragmentPagerAdapter {
    private static final int COUNT_PAGES = 2;
    private ImagePageFragment mImagePageFragment;

    public ImageSearchFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment result;
        if (position == 0){
            result = new SearchPageFragment();
        } else {
            mImagePageFragment = new ImagePageFragment();
            result = mImagePageFragment;
        }

        return result;
    }

    @Override
    public int getCount() {
        return COUNT_PAGES;
    }

    public ImagePageFragment getImagePageFragment() {
        return mImagePageFragment;
    }
}
