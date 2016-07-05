package jp.co.laiblitz.android;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import jp.co.laiblitz.android.constants.LocalNotificationConstants;

/**
 * ローカル通知受信
 */
public class NotificationReceiver extends BroadcastReceiver {

    private static final String NOTIFY_ICON_NAME = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {

        String packageName = context.getPackageName();

        // Receive notification vars
        Integer notificationId = intent.getIntExtra(LocalNotificationConstants.INTENT_ID_KEY, 0);
        String title = intent.getStringExtra(LocalNotificationConstants.INTENT_TITLE_KEY);
        String message = intent.getStringExtra(LocalNotificationConstants.INTENT_MESSAGE_KEY);
        String action = intent.getStringExtra(LocalNotificationConstants.INTENT_ACTION_KEY);

        // run app intent
        Intent runIntent = new Intent(Intent.ACTION_MAIN);
        runIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        runIntent.setClassName(packageName, action);
        runIntent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_NEW_TASK);

        // Intent to PendingIntent
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, runIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Create LargeIcon bitmap
        final PackageManager pm = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return;
        }
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), applicationInfo.icon);

        // Notify icon
        // Unity path (/Assets/Plugins/Android/res/drawable/notify.png)
        int notifyIconResourceId = context.getResources().getIdentifier(NOTIFY_ICON_NAME, "drawable", packageName);

        // Create NotificationBuilder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(pendingIntent);
        builder.setTicker(title);                       // Status bar message
        builder.setSmallIcon(notifyIconResourceId);     // Status bar icon
        builder.setContentTitle(title);                 // Status bar open title
        builder.setContentText(message);                // Status bar open subtitle
        builder.setLargeIcon(largeIcon);                // Status bar open list icon
        builder.setWhen(System.currentTimeMillis());    // display time

        // Notification Sound and vibe
        builder.setDefaults(Notification.DEFAULT_ALL);
        //builder.setLights(Color.green.getRGB(), 500, 500);
        // 1 : 振動開始までの時間 (ms)
        // 2 : 振動 (ms)
        // 3 : 休止 (ms)
        // 4 : (これ以降は要素2-3の繰り返し)
        //builder.setVibrate(new long[]{0, 500, 250, 500, 250});
        //Uri soundUri = Uri.parse("android.resource://" + R.raw.notification);
        //builder.setSound(soundUri);

        // Remove notification in the tap
        builder.setAutoCancel(true);

        NotificationManager manager = (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);
        manager.notify(notificationId, builder.build());
    }
}
