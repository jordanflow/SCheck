package patience.cj.stockhawk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import patience.cj.stockhawk.StockDataContract.*;
/**
 * Created by cj on 9/1/2017.
 */

public class StockDataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "stockdata.db";
    private static final int DATABASE_VERSION = 1;

    public StockDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " + StockEntry.TABLE + " (" +
                StockEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                StockEntry.DATE + " TEXT NOT NULL," +
                StockEntry.CLOSE + " INTEGER NOT NULL," +
                StockEntry.TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + "); ";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + StockEntry.TABLE);
        onCreate(db);
    }
}
