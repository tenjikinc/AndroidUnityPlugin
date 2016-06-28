package jp.co.laiblitz.android.unity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

import java.util.Calendar;

import jp.co.laiblitz.android.NotificationReceiver;
import jp.co.laiblitz.android.constants.LocalNotificationConstants;

/**
 * 通知ユーティリティ
 * Created by sqrt3 on 2016/06/18.
 */
public class NotificationUtil {

    /**
     * ローカル通知をセットする
     * @param notificationId 通知ID
     * @param title          通知タイトル
     * @param message        通知メッセージ
     * @param sentAt         何秒後に通知するか
     * @param isUnique       通知を重複させないか
     */
    public static void setLocalNotification(int notificationId, String title, String message, int sentAt, boolean isUnique) {
        // Delete duplicate notification
        if (isUnique)
            cancelLocalNotification(notificationId);

        // Create intent
        Activity activity = UnityPlayer.currentActivity;
        Context context = activity.getApplicationContext();
        Intent intent = new Intent(context, NotificationReceiver.class);

        // Set intent params
        intent.putExtra(LocalNotificationConstants.INTENT_ID_KEY, notificationId);
        intent.putExtra(LocalNotificationConstants.INTENT_TITLE_KEY, title);
        intent.putExtra(LocalNotificationConstants.INTENT_MESSAGE_KEY, message);
        intent.putExtra(LocalNotificationConstants.INTENT_ACTION_KEY, UnityPlayerActivity.class.getName());

        // Set notification time
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, sentAt);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    /**
     * ローカル通知をセットする
     * @param notificationId 通知ID
     * @param title          通知タイトル
     * @param message        通知メッセージ
     * @param sentAt         何秒後に通知するか
     */
    public static void setLocalNotification(int notificationId, String title, String message, int sentAt) {
        setLocalNotification(notificationId, title, message, sentAt, true);
    }

    /**
     * ローカル通知をキャンセルする
     * @param notificationId 削除する通知ID
     */
    public static void cancelLocalNotification(int notificationId) {
        Context context = UnityPlayer.currentActivity.getApplicationContext();
        Intent intent = new Intent(context, NotificationReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pendingIntent);
    }
}
