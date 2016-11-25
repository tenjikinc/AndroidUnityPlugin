package jp.co.laiblitz.android.unity;

import android.app.Activity;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;

/**
 * Toastの表示
 * Created by sqrt3 on 2016/02/03.
 */
public class ToastUtil {

    /**
     * ToastをLENGTH_LONGで表示
     * @param 表示内容
     */
    public static void showLong(String body) {
        show(body, Toast.LENGTH_LONG);
    }

    /**
     * ToastをLENGTH_SHORTで表示
     * @param 表示内容
     */
    public static void showShort(String body) {
        show(body, Toast.LENGTH_SHORT);
    }

    private static void show(String body, int toastLength)
    {
        final Activity activity = UnityPlayer.currentActivity;
        activity.runOnUiThread(new Runnable() {
            String body;
            int toastLength;

            public Runnable setParams(String body, int toastLength) {
                this.body = body;
                this.toastLength = toastLength;
                return this;
            }

            @Override
            public void run() {
                Toast.makeText(activity, body, toastLength).show();
            }
        }.setParams(body, toastLength));
    }
}
