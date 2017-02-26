package ksayker.customgooglesearch.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author ksayker
 * @version 0.0.1
 * @since 26.02.17
 */
public class DataBaseHelper extends SQLiteOpenHelper{
    public static final String DATA_BASE_NAME = "favorites_data_base";
    public static final int DATA_BASE_VERSION = 1;

    public static final String TABLE_NAME_FAVORITE = "favorite";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LINK = "link";

    public DataBaseHelper(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String q = "CREATE TABLE " + TABLE_NAME_FAVORITE +" (" +
                COLUMN_ID + " integer primary key autoincrement," +
                COLUMN_LINK + " text NOT NULL" +
                ");";
        db.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
