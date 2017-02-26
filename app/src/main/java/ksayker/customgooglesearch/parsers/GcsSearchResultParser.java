package ksayker.customgooglesearch.parsers;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import ksayker.customgooglesearch.models.ItemSearchData;
import ksayker.customgooglesearch.utils.UtilBitmapDecoder;

/**
 * @author ksayker
 * @version 0.0.1
 * @since 23.02.17
 */
public class GcsSearchResultParser {

    public ArrayList<ItemSearchData> parse(Search search){
        ArrayList<ItemSearchData> result = new ArrayList<>();
        if (search.getItems() != null){
            for (Result item : search.getItems()) {
                if (item != null){
                    try {
                        Rect rect = new Rect();
                        rect.contains(100, 100, 100, 100);
                        Bitmap bitmap = UtilBitmapDecoder
                                .decodeSampledBitmapFromResource(
                                        new URL(item.getImage().getThumbnailLink()),
                                        rect,
                                        100,
                                        100);
                        result.add(new ItemSearchData(
                                bitmap, item.getTitle(), item.getLink(), false));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
}
