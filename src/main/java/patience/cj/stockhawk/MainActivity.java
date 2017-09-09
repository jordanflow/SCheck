package patience.cj.stockhawk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String> {

    private EditText mSearchBox;
    private TextView mSearchedItem;
    private TextView mSearchResults;
    private static final String STOCK_SEARCHED = "stock_searched";
    private static final String STOCK_NEWS = "stock_news";
    private static final int SEARCH_LOADER = 99;
    private static final String STOCK_SEARCHED_URL = "stock_URL";
    private SQLiteDatabase stockDb;
    private StockDataAdapter stockAdapter;
    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Set the views to do the inital news search or price search on your stock.
        mSearchBox = (EditText) findViewById(R.id.search_box);
        mSearchedItem = (TextView) findViewById(R.id.searched_for);
        mSearchResults = (TextView) findViewById(R.id.search_results);

        final Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                makeStockNewsSearch();
            }
        });
        if (savedInstanceState != null) {
            String stockSearched = savedInstanceState.getString(STOCK_SEARCHED);
            String stockNews = savedInstanceState.getString(STOCK_NEWS);
            mSearchedItem.setText(stockSearched);
            mSearchResults.setText(stockNews);
        }
    }


    //Retrieve cursor to take initial look at stock price search
    private Cursor getAllData() {
        return stockDb.query(
                StockDataContract.StockEntry.TABLE,
                null,
                null,
                null,
                null,
                null,
                StockDataContract.StockEntry.DATE
        );
    }


    //Create the RecyclerView with the Date and the Close price of Stocks
    public void createRecyclerView(){
        RecyclerView stockDataRecyclerView;
        stockDataRecyclerView = (RecyclerView) this.findViewById(R.id.stock_recycler_view);
        stockDataRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        StockDataHelper stockHelper = new StockDataHelper(this);
        stockDb = stockHelper.getWritableDatabase();
        StockDataUtil.insertStockData(stockDb);
        Cursor cursor = getAllData();
        stockAdapter = new StockDataAdapter(this, cursor);
        stockDataRecyclerView.setAdapter(stockAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    //Search for news via RSS feed from yahoo/google or companion site
    private void makeStockNewsSearch(){
        createRecyclerView();

        /**
        String news = mSearchBox.getText().toString();
        if (TextUtils.isEmpty(news)) {
            mSearchedItem.setText("Nothing to Do");
            return;
        }
        URL searchUrl = GetData.buildUrl(news);
        mSearchedItem.setText(news);
        Bundle searchBundle = new Bundle();
        searchBundle.putString(STOCK_SEARCHED_URL, searchUrl.toString());

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> searchLoader = loaderManager.getLoader(SEARCH_LOADER);
        if (searchLoader == null) {
            loaderManager.initLoader(SEARCH_LOADER, searchBundle, this);
        } else {
            loaderManager.restartLoader(SEARCH_LOADER, searchBundle, this);
        } */
    }


    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            String stockDataJson;

            @Override
            protected  void onStartLoading() {
                if (args == null) {
                    return;
                }
                if (stockDataJson != null) {
                    deliverResult(stockDataJson);
                } else{
                    forceLoad();
                }
            }

            @Override
            public String loadInBackground() {
                String searchString = args.getString(STOCK_SEARCHED_URL);
                if (searchString == null || TextUtils.isEmpty(searchString)){
                    return null;
                }
                try {
                    URL searchUrl = new URL(searchString);
                    String searchResults = GetData.getStockNewsFromURL(searchUrl);
                    return searchResults;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(String stockData) {
                stockDataJson = stockData;
                super.deliverResult(stockData);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (null == data) {
            mSearchResults.setText("Error");
        } else {
            mSearchResults.setText(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuitem = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    //Save your state
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String stockSearched = mSearchedItem.getText().toString();
        outState.putString(STOCK_SEARCHED, stockSearched);
        String stockNews = mSearchResults.getText().toString();
        outState.putString(STOCK_NEWS, stockNews);
    }

}
