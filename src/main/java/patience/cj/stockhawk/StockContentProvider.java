package patience.cj.stockhawk;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import static patience.cj.stockhawk.StockDataContract.StockEntry.TABLE;
/**
 * Created by cj on 9/2/2017.
 */

public class StockContentProvider extends ContentProvider {

    public static final int STOCKS = 100;
    public static final int STOCKS_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private StockDataHelper mstockDataHelper;

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(StockDataContract.CP, StockDataContract.PATH_TASKS, STOCKS);
        uriMatcher.addURI(StockDataContract.CP, StockDataContract.PATH_TASKS + " ", STOCKS_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mstockDataHelper = new StockDataHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db = mstockDataHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match) {
            case STOCKS:
                retCursor = db.query(TABLE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mstockDataHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case STOCKS:
                long id = db.insert(TABLE, null, values);
                if ( id > 0) {
                    returnUri = ContentUris.withAppendedId(StockDataContract.StockEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed on this row" + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mstockDataHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        int tasksDeleted;

        switch (match) {
            case STOCKS_WITH_ID:
                String id = uri.getPathSegments().get(1);
                tasksDeleted = db.delete(TABLE, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (tasksDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return tasksDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mstockDataHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        //Wait for FireBaseJobService
        switch (match) {
            case STOCKS:
        }
        return 0;
    }
}
