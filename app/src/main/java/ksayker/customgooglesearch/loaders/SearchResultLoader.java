package ksayker.customgooglesearch.loaders;

import android.content.Context;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.text.TextUtils;

import java.util.ArrayList;

import ksayker.customgooglesearch.models.ItemSearchData;
import ksayker.customgooglesearch.models.SearchRequestData;
import ksayker.customgooglesearch.network.SearchTask;

/**
 * @author ksayker
 * @version 0.0.1
 * @since 21.02.17
 */
public class SearchResultLoader extends Loader<ArrayList<ItemSearchData>>
        implements SearchTask.SearchResult {
    public static final int SEARCH_RESULT_LOADER_ID = 1;

    public static final String SEARCH_REQUEST_TEXT_KEY = "SEARCH_REQUEST_TEXT_KEY";
    public static final String SEARCH_REQUEST_START_INDEX = "SEARCH_REQUEST_START_INDEX";

    private Context mContext;

    private SearchTask mSearchTask;

    private String mSearchRequestText;
    private long mStartIndex;

    public SearchResultLoader(Context context, Bundle bundle){
        this(context);
        mContext = context;
        if (bundle != null){
            mSearchRequestText = bundle.getString(SEARCH_REQUEST_TEXT_KEY);
            mStartIndex = bundle.getLong(SEARCH_REQUEST_START_INDEX);
        }
    }

    public SearchResultLoader(Context context) {
        super(context);
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        if (!TextUtils.isEmpty(mSearchRequestText)){
            if (mSearchTask != null){
                mSearchTask.cancel(true);
            }

            mSearchTask = new SearchTask(mContext, this);
            mSearchTask.execute(new SearchRequestData(mSearchRequestText, mStartIndex));
        }
    }

    @Override
    public void onSearchResult(ArrayList<ItemSearchData> result) {
        deliverResult(result);
    }
}
