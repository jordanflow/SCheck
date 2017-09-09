package patience.cj.stockhawk;


import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

/**
 * Created by cj on 9/2/2017.
 */


public class FirbaseIntraDayStockCheck extends JobService{

    private AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final JobParameters params) {
        mBackgroundTask = new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                Context context = FirebaseIntraDayStockCheck.this;
                IntraDay.executeTask(context, IntraDay.TIME_LAPSED);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(params, false);
            }
        };
        mBackgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        if (mBackgroundTask != null) mBackgroundTask.cancel(true);
        return true;
    }
}
