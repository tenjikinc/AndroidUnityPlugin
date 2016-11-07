package jp.co.laiblitz.android.unity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import com.unity3d.player.UnityPlayer;

/**
 * アプリバージョン情報の取得
 * Created by sqrt3 on 2016/02/02.
 */
public class PackageInfoUtil {

    /**
     * バージョンコードの取得
     * @return ApplicationVersionCode
     */
    public static int getVersionCode() {
        return getPackageInfo().versionCode;
    }

    /**
     * バージョンの取得
     * @return ApplicationVersionName
     */
    public static String getVersionName() {
        return getPackageInfo().versionName;
    }

    private static PackageInfo getPackageInfo() {
        Activity activity = UnityPlayer.currentActivity;
        PackageInfo packageInfo = null;

        try {
            packageInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
            Log.e("BundleVersionManager", "packageInfo load error");
        }

        return packageInfo;
    }
}
