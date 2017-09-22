package com.ts.dldqy.kuangjia.base;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.ts.dldqy.kuangjia.R;
import com.ts.dldqy.kuangjia.utils.permissionutils.PerUtils;
import com.ts.dldqy.kuangjia.utils.permissionutils.PerimissionsCallback;
import com.ts.dldqy.kuangjia.utils.permissionutils.PermissionEnum;
import com.ts.dldqy.kuangjia.utils.permissionutils.PermissionManager;
import com.ts.dldqy.kuangjia.view.LoadingDialog;
import com.ts.dldqy.kuangjia.view.SCLoadingDialog;

import java.util.ArrayList;
import java.util.UUID;


/**
 * Activity基类
 * @author wh
 */

public class BaseActivity extends AppCompatActivity {
    
    protected TelephonyManager tm;
    protected String tmDevice, tmSerial, androidId;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
         * 设置透明状态栏，非透明导航栏
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

    }
    
    /**
     * http://www.miui.com/thread-849316-1-1.html
     * 获取手机的设备识别号
     */
    protected String UUID(){
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid.toString();
    }
    
    /**
     * 获取当前的网络连接
     * @return
     */
    protected boolean getConnectivityStatus() {
        ConnectivityManager connMngr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            return connMngr.getActiveNetworkInfo().isConnectedOrConnecting();
        } catch (NullPointerException npe) {
            return false;
        }
    }
    
    protected Toast myToast;
    /**
     * 解决间隔时间段的情况下，toast显示不连贯的问题
     * @param context 当前activity
     * @param str 要显示的字符串
     */
    protected void myToast(Context context, String str) {
        if (myToast == null) {
            try {
                myToast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
                myToast.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                myToast.setText(str);
                myToast.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 简化初始化控件
     * @param id 控件ID
     * @param <T> 
     * @return
     */
    protected <T extends View> T $(int id){
        return (T) super.findViewById(id);
    }

    /**
     * 6.0以上的权限检查需要加在onStart()中
     * @param mContext 当前Activity
     * @param listPermission 危险权限枚举集合，必须使用ArrayList<PermissionEnum></>不可以使用List<PermissionEnum></>
     * 如果不加在onStart中，会出现进入系统权限设置不允许任何操作权限，返回后界面仍可以进行操作然后出错
     */
    protected void checkPermissions(final Context mContext,ArrayList<PermissionEnum> listPermission) {
        PermissionManager
                .with(mContext)
                .tag(1000)
                .permissions(listPermission)
                .callback(new PerimissionsCallback() {
                    @Override
                    public void onGranted(ArrayList<PermissionEnum> grantedList) {
                        //权限被允许
                        //TODO...
                    }

                    @Override
                    public void onDenied(ArrayList<PermissionEnum> deniedList) {
                        //权限被拒绝
                        //TODO...
                        //重新检查权限
                        PermissionDenied(deniedList,mContext);
                    }
                })
                .checkAsk();
    }

    /**
     * 6.0以上的权限检查
     */
    protected void PermissionDenied(final ArrayList<PermissionEnum> permissionsDenied, final Context mContext) {
        StringBuilder msgCN = new StringBuilder();
        for (int i = 0; i < permissionsDenied.size(); i++) {
            if (i == permissionsDenied.size() - 1) {
                msgCN.append(permissionsDenied.get(i).getName_cn());
            } else {
                msgCN.append(permissionsDenied.get(i).getName_cn() + ",");
            }
        }
        if (mContext == null) {
            return;
        }

        /**
         * 弹出对话框
         * 6.0以上的权限检查
         */
        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                .setMessage(String.format(mContext.getResources().getString(R.string.permission_explain), msgCN.toString()))
                .setCancelable(false)
                .setPositiveButton(R.string.per_setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //确定，跳转到设置权限页面
                        PerUtils.openApplicationSettings(mContext, R.class.getPackage().getName());
                    }
                })
                .setNegativeButton(R.string.per_cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /**
                         * 取消
                         * 暂定结束此界面不允许用户操作
                         */
                        finish();
                    }
                }).create();
        /**
         * 禁止点击系统返回键及触摸其他地方消失
         * 如果消失，获取权限就没有意义
         */
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    /**
     * 6.0以上的权限检查
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handleResult(requestCode, permissions, grantResults);
    }
    

    /**
     * 右上角弹出菜单
     */
    protected PopupWindow popupWindow;
    protected ListView showPop(final Context context, final String[] names, final int[] imgs, View popimg) {
        View popView=View.inflate(context, R.layout.base_pop_window,null);
        /**
         * 第一个参数：View contenView（布局）
         * 第二个参数：int width（宽度）
         * 第三个参数：int height（高度）
         *      宽高参数：-2 和 ViewGroup.LayoutParams.WRAP_CONTENT 一样
         *                -1 和 ViewGroup.LayoutParams.MATCH_PARENT 一样
         *
         * 三个参数缺少任意一个都不可能弹出来PopWindow；
         *
         */
        popupWindow = new PopupWindow(popView, -2, -2);
        //popupWindow是否响应touch事件
        popupWindow.setTouchable(true);
        //popupWindow是否具有获取焦点的能力
        popupWindow.setFocusable(true);
        //这个方法是重中之重，不仅仅是设置背景,不设置背景上面两行代码无效
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.colorWhite));

        ListView popListView = (ListView) popView.findViewById(R.id.poplist);

        //这里有个小坑，代码设置分割线，必须先设置颜色，再设置高度，不然不生效
        popListView.setDivider(new ColorDrawable(Color.WHITE));
        popListView.setDividerHeight(1);

        PopListViewAdapter adapter = new PopListViewAdapter(context,names,imgs);
        popListView.setAdapter(adapter);

        /**
         * 第一个参数：显示在ｉｖ布局下面
         * 第二个参数：xoff表示x轴的偏移，正值表示向左，负值表示向右；
         * 第三个参数：yoff表示相对y轴的偏移，正值是向下，负值是向上；
         */
        popupWindow.showAsDropDown(popimg,40,0);

        return popListView;
    }

    /**
     * 请求服务器进度条
     */
    protected LoadingDialog mDialog;
    protected LoadingDialog loadDialog(){
        if(mDialog == null){
            mDialog = new LoadingDialog(this);
        }
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        return mDialog;
    }


    /**
     * 上传服务器进度条
     */
    protected SCLoadingDialog scmDialog;
    protected SCLoadingDialog scloadDialog(){
        if(scmDialog == null){
            scmDialog = new SCLoadingDialog(this);
        }
        scmDialog.setCanceledOnTouchOutside(false);
        scmDialog.setCancelable(false);
        return scmDialog;
    }

    /**
     * 获取控件的宽度或高度
     */
    protected float viewHeight(View view){
        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        float jiandanwidth =view.getMeasuredHeight();
        return jiandanwidth;
    }
    /**
     * 获取屏幕高宽
     * @return
     */
    protected DisplayMetrics phoneSize(){
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric;
    }
    
}
