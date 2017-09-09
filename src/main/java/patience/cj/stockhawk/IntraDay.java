package patience.cj.stockhawk;

import android.content.Context;

/**
 * Created by cj on 9/2/2017.
 */

public class IntraDay {

    public static final String TIME_LAPSED = "time_lapse";
    public static final String CHECK_IN = "check_in";

    public static void executeTask(Context context, String action) {
        if (TIME_LAPSED.equals(action)) {
            //intradayquery
        } else if (CHECK_IN.equals(action)){
            //intradayquery
        }
    }

}
