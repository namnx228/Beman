package com.uet.beman.common;

import android.app.Application;
import android.content.Context;

/**
 * Created by thanhpd on 3/14/2015.
 */
public class BM_Application extends Application {
    private static BM_Application instance;
    public BM_Application() {
        instance = this;
    }

    public static Context getInstance() {
        return instance;
    }

    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
