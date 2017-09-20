package com.ts.dldqy.kuangjia.utils.permissionutils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

/**
 * 权限跳转至系统应用权限设置界面
 * @author wh
 */

public class PerUtils {

    /**
     * 跳转到设置权限界面
     * @param packageName
     * @return
     */
    public static Intent openApplicationSettings(String packageName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + packageName));
            return intent;
        }
        return new Intent();
    }

    /**
     * 跳转到设置权限界面
     * @param context 当前Activity
     * @param packageName 包名
     */
    public static void openApplicationSettings(Context context, String packageName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            Intent intent = openApplicationSettings(packageName);
            context.startActivity(intent);
        }
    }
}
