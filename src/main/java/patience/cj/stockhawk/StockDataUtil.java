package patience.cj.stockhawk;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cj on 9/1/2017.
 */

public class StockDataUtil {

    List<ContentValues> list;

    public static void insertStockData(SQLiteDatabase db) {
        if (db == null) {
            return;
        }
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(StockDataContract.StockEntry.DATE, "8/26/17");
        cv.put(StockDataContract.StockEntry.CLOSE, 98);
        list.add(cv);

        cv = new ContentValues();
        cv.put(StockDataContract.StockEntry.DATE, "8/26/17");
        cv.put(StockDataContract.StockEntry.CLOSE, 99);
        list.add(cv);

        cv = new ContentValues();
        cv.put(StockDataContract.StockEntry.DATE, "8/26/17");
        cv.put(StockDataContract.StockEntry.CLOSE, 100);
        list.add(cv);

        cv = new ContentValues();
        cv.put(StockDataContract.StockEntry.DATE, "8/26/17");
        cv.put(StockDataContract.StockEntry.CLOSE, 101);
        list.add(cv);

        try {
            db.beginTransaction();
            db.delete(StockDataContract.StockEntry.TABLE, null, null);
            for (ContentValues c : list) {
                db.insert(StockDataContract.StockEntry.CLOSE, null, c);
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {

        } finally {
            db.endTransaction();
        }
    }

}
