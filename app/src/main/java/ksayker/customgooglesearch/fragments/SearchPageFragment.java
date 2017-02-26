package ksayker.customgooglesearch.fragments;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import java.util.ArrayList;

import ksayker.customgooglesearch.R;
import ksayker.customgooglesearch.database.DataBaseHelper;
import ksayker.customgooglesearch.loaders.SearchResultLoader;
import ksayker.customgooglesearch.adapters.SearchResultAdapter;
import ksayker.customgooglesearch.models.ItemSearchData;

/**
 * @author ksayker
 * @version 0.0.1
 * @since 18.02.17
 */
public class SearchPageFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<ArrayList<ItemSearchData>>,
        SearchView.OnQueryTextListener, SearchResultAdapter.DataBaseQueryExecutor{
    private View mRootView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    private SearchResultAdapter mRecyclerAdapter;

    private DataBaseHelper mDataBaseHelper;

    private ArrayList<ItemSearchData> mSearchResults;

    public SearchPageFragment(){
        mSearchResults = new ArrayList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(
                SearchResultLoader.SEARCH_RESULT_LOADER_ID, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mDataBaseHelper = new DataBaseHelper(getContext());

        mRootView = inflater.inflate(R.layout.fragment_page_search, null);

        mRecyclerView = (RecyclerView) mRootView.findViewById(
                R.id.fragment_page_search_recycler_view_search_result);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerAdapter = new SearchResultAdapter(
                (SearchResultAdapter.ImageDisplayer) getActivity(), this, mSearchResults);
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.addOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if ((mLayoutManager.findFirstVisibleItemPosition() + mLayoutManager.getChildCount()
                                >= mLayoutManager.getItemCount() - 1)){
                            startSearch(mLayoutManager.getItemCount());
                        }
                    }
                }
        );

        SearchView searchView = (SearchView)mRootView.findViewById(
                R.id.fragment_page_search_sv_search);
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(false);
        showKeyboard(searchView);

        return mRootView;
    }

    private void startSearch(long startSearchIndex){
        Bundle bundle = new Bundle();
        bundle.putString(SearchResultLoader.SEARCH_REQUEST_TEXT_KEY,
                ((SearchView)mRootView.findViewById(R.id.fragment_page_search_sv_search))
                        .getQuery().toString());
        bundle.putLong(SearchResultLoader.SEARCH_REQUEST_START_INDEX, startSearchIndex);
        getLoaderManager().restartLoader(SearchResultLoader.SEARCH_RESULT_LOADER_ID,
                bundle, this)
                .forceLoad();
    }

    private void showKeyboard(View view){
        if (view.requestFocus()){
            getActivity().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
    }

    private void hideKeyboard(){
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public Loader<ArrayList<ItemSearchData>> onCreateLoader(int id, Bundle args) {
        Loader<ArrayList<ItemSearchData>> loader = null;
        if (id == SearchResultLoader.SEARCH_RESULT_LOADER_ID){
            loader = new SearchResultLoader(getContext(), args);
        }

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<ItemSearchData>> loader,
                               ArrayList<ItemSearchData> data) {
        if (data != null){
            mSearchResults.addAll(data);
            mRecyclerAdapter.notifyDataSetChanged();
            hideKeyboard();
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<ItemSearchData>> loader) {
    }

    @Override
    public void removeFromFavorite(String url) {
        SQLiteDatabase database = mDataBaseHelper.getWritableDatabase();
        database.delete(
                DataBaseHelper.TABLE_NAME_FAVORITE,
                DataBaseHelper.COLUMN_LINK + "='" + url + "'",
                null);
        mDataBaseHelper.close();
    }

    @Override
    public void writeToFavorite(String url) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase database = mDataBaseHelper.getWritableDatabase();
        contentValues.put(DataBaseHelper.COLUMN_LINK, url);
        database.insert(DataBaseHelper.TABLE_NAME_FAVORITE, null, contentValues);
        mDataBaseHelper.close();
    }

    @Override
    public boolean isFavorite(String url) {
        boolean result;
        SQLiteDatabase database = mDataBaseHelper.getWritableDatabase();
        Cursor cursor = database.query(
                DataBaseHelper.TABLE_NAME_FAVORITE,
                new String[]{DataBaseHelper.COLUMN_ID},
                DataBaseHelper.COLUMN_LINK + " = ?",
                new String[]{url},
                null,
                null,
                null);
        result = cursor.moveToFirst();
        mDataBaseHelper.close();
        return result;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchResults.clear();

        mRecyclerAdapter.setLoading(true);
        mRecyclerAdapter.notifyDataSetChanged();

        startSearch(1L);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }
}
