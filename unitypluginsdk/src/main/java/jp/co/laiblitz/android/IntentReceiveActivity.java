package jp.co.laiblitz.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.unity3d.player.UnityPlayerActivity;

/**
 * Created by corich_hosono on 16/10/27.
 */
public class IntentReceiveActivity extends Activity {

    @Override
    protected void onResume() {
        super.onResume();

        // スキーマ取得
        Uri uri = getIntent().getData();
        // スキーマをシングルトンに保持
        ApplicationCache.getInstance().customScheme = uri.toString();

        // UnityのActivityを呼ぶ
        Intent intent = new Intent(this, UnityPlayerActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // バックキーで何もしない
        // super.onBackPressed();
    }
}
