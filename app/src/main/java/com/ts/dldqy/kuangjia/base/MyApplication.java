package com.ts.dldqy.kuangjia.base;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

/**
 * 自定义Application
 * @author wh
 */

public class MyApplication extends Application {

    /**
     * 开发者可控制应用最早的方法
     * @param base
     * 如果存在64K方法数限制，并使用MultiDex进行分包，那么MultiDex就在此方法中初始化
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * android7.0手机需要加上的代码，否则无法拍照，并出现闪退
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }
}
