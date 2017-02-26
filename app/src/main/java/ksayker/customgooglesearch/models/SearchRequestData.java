package ksayker.customgooglesearch.models;

/**
 * @author ksayker
 * @version 0.0.1
 * @since 23.02.17
 */
public class SearchRequestData {
    private final String mSearchText;
    private final long mSearchStartIndex;

    public SearchRequestData(String searchText, long searchStartIndex) {
        mSearchText = searchText;
        mSearchStartIndex = searchStartIndex;
    }

    public String getSearchText() {
        return mSearchText;
    }

    public long getSearchStartIndex() {
        return mSearchStartIndex;
    }
}
