package patience.cj.stockhawk;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by cj on 9/2/2017.
 */

public class IntraDayStockCheck extends IntentService {

    public IntraDayStockCheck() {
        super("IntraDayStockCheck");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        IntraDay.executeTask(this, action);
    }
}
