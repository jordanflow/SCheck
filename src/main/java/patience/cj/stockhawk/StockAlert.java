package patience.cj.stockhawk;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by cj on 9/2/2017.
 */

public class StockAlert {

    private static final int ALERT_ID = 99;

    private static final int ALERT_PENDING_INTENT = 100;


    public static void clearAllNotifications(Context context){
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void stockMovement(Context context) {
        NotificationCompat.Builder notifcationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentTitle(context.getString(R.string.up_title))
                .setContentText(context.getString(R.string.up))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.up)))
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notifcationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(ALERT_ID, notifcationBuilder.build());
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(
                context,
                ALERT_PENDING_INTENT,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
