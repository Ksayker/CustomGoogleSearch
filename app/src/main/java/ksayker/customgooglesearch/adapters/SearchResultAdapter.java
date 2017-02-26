package ksayker.customgooglesearch.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ksayker.customgooglesearch.R;
import ksayker.customgooglesearch.models.ItemSearchData;

/**
 * @author ksayker
 * @version 0.0.1
 * @since 18.02.17
 */
public class SearchResultAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_SEARCH_RESULT_ITEM = 1;
    private static final int VIEW_TYPE_LOADING_ITEM = 2;

    private ImageDisplayer mImageDisplayer;
    private DataBaseQueryExecutor mDataBaseQueryExecutor;

    private ArrayList<ItemSearchData> mSearchResults;

    private boolean isLoading;

    public SearchResultAdapter(ImageDisplayer imageDisplayer,
                               DataBaseQueryExecutor dataBaseQueryExecutor,
                               ArrayList<ItemSearchData> searchResults) {
        mImageDisplayer = imageDisplayer;
        mDataBaseQueryExecutor = dataBaseQueryExecutor;
        mSearchResults = searchResults;

        isLoading = false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if (viewType == VIEW_TYPE_SEARCH_RESULT_ITEM) {
            viewHolder = new ItemSearchResultViewHolder(layoutInflater.inflate(
                    R.layout.card_search_item, parent, false));
        } else if (viewType == VIEW_TYPE_LOADING_ITEM) {
            viewHolder = new LoadingSearchResultViewHolder(layoutInflater.inflate(
                    R.layout.card_search_loading, parent, false));
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < mSearchResults.size()) {
            mSearchResults.get(position).setFavorite(mDataBaseQueryExecutor.isFavorite(
                    mSearchResults.get(position).getLink()));

            ((ItemSearchResultViewHolder) holder).mTextView.setText(
                    mSearchResults.get(position).getName());
            ((ItemSearchResultViewHolder) holder).mCheckBox.setChecked(
                    mSearchResults.get(position).isFavorite());
            ((ItemSearchResultViewHolder) holder).mImageView.setImageBitmap(
                    mSearchResults.get(position).getBitmap());
            ((ItemSearchResultViewHolder) holder).mImageView.setOnClickListener(
                    new OnImageClickListener(mSearchResults.get(position).getLink()));
            ((ItemSearchResultViewHolder) holder).mCheckBox.setOnClickListener(
                    new OnFavoriteClockListener(
                            mSearchResults.get(position).getLink(), position));
        }
    }

    @Override
    public int getItemCount() {
        int result = mSearchResults.size();
        if (isLoading) {
            result++;
        }

        return result;
    }

    @Override
    public int getItemViewType(int position) {
        int result;
        if (position < mSearchResults.size()) {
            result = VIEW_TYPE_SEARCH_RESULT_ITEM;
        } else {
            result = VIEW_TYPE_LOADING_ITEM;
        }

        return result;
    }

    private class ItemSearchResultViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;
        CheckBox mCheckBox;

        ItemSearchResultViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(
                    R.id.card_search_item_image);
            mTextView = (TextView) itemView.findViewById(
                    R.id.card_search_item_text);
            mCheckBox = (CheckBox) itemView.findViewById(
                    R.id.card_search_item_cb_favorite);
        }
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    private class LoadingSearchResultViewHolder extends RecyclerView.ViewHolder {
        LoadingSearchResultViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class OnImageClickListener implements View.OnClickListener {
        private String mImageUrl;

        OnImageClickListener(String imageUrl) {
            mImageUrl = imageUrl;
        }

        @Override
        public void onClick(View v) {
            mImageDisplayer.onImageDisplay(mImageUrl);
        }
    }

    private class OnFavoriteClockListener implements View.OnClickListener {
        private String mUrl;
        private int mIndex;

        OnFavoriteClockListener(String url,int index) {
            mUrl = url;
            mIndex = index;
        }

        @Override
        public void onClick(View v) {
            if (mDataBaseQueryExecutor.isFavorite(mUrl)) {
                mDataBaseQueryExecutor.removeFromFavorite(mUrl);
                mSearchResults.get(mIndex).setFavorite(false);
            } else {
                mDataBaseQueryExecutor.writeToFavorite(mUrl);
                mSearchResults.get(mIndex).setFavorite(true);
            }
            ((CheckBox) v).setChecked(mSearchResults.get(mIndex).isFavorite());
        }
    }

    public interface ImageDisplayer {
        void onImageDisplay(String url);
    }

    public interface DataBaseQueryExecutor {
        void writeToFavorite(String url);

        void removeFromFavorite(String url);

        boolean isFavorite(String url);
    }
}
