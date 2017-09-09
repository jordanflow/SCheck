package patience.cj.stockhawk;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by cj on 9/1/2017.
 */

public class StockDataContract {

    public static final String CP = "patience.cj.stockhawk";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CP);
    public static final String PATH_TASKS = "stock_data";

    public static final class StockEntry implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

        public static final String TABLE = "table";
        public static final String _ID = "id";
        public static final String DATE = "date";
        public static final String CLOSE = "close";
        public static final String TIMESTAMP = "time";
    }

    public static final class StockSymbol implements BaseColumns{
        public static final String STOCK_SYMBOL_TABLE = "stock_symbol_table";
        public static final String STOCK_SYMBOL = "stock_symbol";
        public static final String STOCK_NAME = "stock_name";
        public static final String LAST_SALE = "last_sale";
        public static final String IPO = "ipo_year";
        public static final String SECTOR = "sector";
        public static final String INDUSTRY = "industry";
        public static final String SUMMARY_QUOTE = "summary_quote";
    }

}
