package ksayker.customgooglesearch.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import ksayker.customgooglesearch.R;
import ksayker.customgooglesearch.adapters.ImageSearchFragmentPageAdapter;
import ksayker.customgooglesearch.adapters.SearchResultAdapter;

public class MainActivity extends AppCompatActivity
        implements SearchResultAdapter.ImageDisplayer{
    private static int IMAGE_PAGE_INDEX = 1;
    private static int SEARCH_PAGE_INDEX = 0;
    private ViewPager mViewPager;
    private ImageSearchFragmentPageAdapter mPagerAdapter;

    //TODO resolve permission.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI(){
        mViewPager = (ViewPager) findViewById(R.id.activity_main_view_pager);

        mPagerAdapter = new ImageSearchFragmentPageAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mPagerAdapter);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && mViewPager.getCurrentItem() == IMAGE_PAGE_INDEX){
            mViewPager.setCurrentItem(SEARCH_PAGE_INDEX);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onImageDisplay(String url) {
        mViewPager.setCurrentItem(IMAGE_PAGE_INDEX);
        mPagerAdapter.getImagePageFragment().forceImageLoad(url);
    }
}
