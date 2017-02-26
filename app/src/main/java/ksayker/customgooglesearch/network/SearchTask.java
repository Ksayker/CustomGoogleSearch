package ksayker.customgooglesearch.network;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Search;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import ksayker.customgooglesearch.R;
import ksayker.customgooglesearch.parsers.GcsSearchResultParser;
import ksayker.customgooglesearch.models.ItemSearchData;
import ksayker.customgooglesearch.models.SearchRequestData;

/**
 * @author ksayker
 * @version 0.0.1
 * @since 21.02.17
 */
public class SearchTask extends
        AsyncTask<SearchRequestData, Void, ArrayList<ItemSearchData>> {
    private SearchResult mSearchResult;
    private Context mContext;

    public SearchTask(Context context, SearchResult searchResult) {
        mContext = context;
        mSearchResult = searchResult;
    }

    @Override
    protected ArrayList<ItemSearchData> doInBackground(SearchRequestData... params) {
        ArrayList<ItemSearchData> result = null;
        Customsearch.Builder customSearchBuilder = new Customsearch.Builder(
                new NetHttpTransport(), new JacksonFactory(), null);
        customSearchBuilder.setApplicationName(mContext.getResources().getString(R.string.app_name));
        try {
            Customsearch.Cse.List list = customSearchBuilder.build().cse()
                    .list(params[0].getSearchText());
            list.setKey(mContext.getResources().getString(R.string.custom_search_key));
            list.setCx(mContext.getResources().getString(R.string.custom_search_engine_id));
            list.setSearchType("image");
            list.setStart(params[0].getSearchStartIndex());

            Search search = list.execute();
            GcsSearchResultParser parser = new GcsSearchResultParser();
            result = parser.parse(search);
        } catch (UnknownHostException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //When search requests limit exhausted.
//        result = fillData();

        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<ItemSearchData> result) {
        super.onPostExecute(result);
        mSearchResult.onSearchResult(result);
    }

    public interface SearchResult {
        void onSearchResult(ArrayList<ItemSearchData> result);
    }

    //TODO delete
    private ArrayList<ItemSearchData> fillData(){
        ArrayList<ItemSearchData> result = new ArrayList<>();
        result.add(new ItemSearchData(
                BitmapFactory.decodeResource(mContext.getResources(), R.drawable.abc),
                "ne pingvin",
                "https://pbs.twimg.com/profile_images/378800000748859587/289a592b2989b6f2dabb76db7fc25947_400x400.jpeg",
                false));
        result.add(new ItemSearchData(
                BitmapFactory.decodeResource(mContext.getResources(), R.drawable.images),
                "pingvin_worker",
                "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSSOA8nWcrXya11Km451UmFopDtyUGTCO6Rce6yNyobeDCERlT0",
                false));
        result.add(new ItemSearchData(
                BitmapFactory.decodeResource(mContext.getResources(), R.drawable.bird),
                "bird",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ5Sb_ZREKPv6Fk-r85nvmIWc6FFrB0CxpSdX2XP8X508i3oMWFiQ",
                false));

        return result;
    }
}
