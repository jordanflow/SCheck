package patience.cj.stockhawk;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Driver;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by cj on 8/31/2017.
 */

public class GetData {

    private static final int CHECK_TIME_MINUTES = 15;
    private static final int CHECK_TIME_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(CHECK_TIME_MINUTES));
    private static final int CHECK_TIME = CHECK_TIME_SECONDS;

    private static final String CHECK_TIME_TAG = "stock_check_tag";

    private static boolean checked;

    synchronized public static void scheduleChargingReminder(@NonNull final Context context) {

        if (checked) return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Job stockCheckJob = dispatcher.newJobBuilder()

                .setService(FirbaseIntraDayStockCheck.class)
                .setTag(CHECK_TIME_TAG)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        CHECK_TIME_SECONDS,
                        CHECK_TIME + CHECK_TIME_SECONDS))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(stockCheckJob);
        checked = true;
    }
}

/**
    //Build your Url from string input by User
    public static URL buildUrl(String string){
        Uri uri = Uri.parse("https://google/finance/company_news?q=NYSE%3").buildUpon()
                .appendQueryParameter("q", string)
                .appendQueryParameter("q=LON:VOD&output=rss", "").build();
        // http://finance.google.com/finance/info?client=ig&q=NSE:HDFC
        URL url = null;
        try{
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    //Retrieve news articles from Source
    public static String getStockNewsFromURL(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            //scanner.useDelimter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
