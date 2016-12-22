package jp.co.laiblitz.android;

import android.app.Application;

public class ApplicationCache extends Application {

    private static ApplicationCache instance;
    public static ApplicationCache getInstance() {
        return instance;
    }

    public String customScheme;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}