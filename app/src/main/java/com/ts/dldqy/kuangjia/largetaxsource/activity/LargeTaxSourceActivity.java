package com.ts.dldqy.kuangjia.largetaxsource.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.ts.dldqy.kuangjia.R;
import com.ts.dldqy.kuangjia.base.BaseActivity;
import com.ts.dldqy.kuangjia.base.ExitApplication;

/**
 * 税源大户页面
 * Created by HSL on 2017/9/21 0021.
 */

public class LargeTaxSourceActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_tax);
        ExitApplication.getInstance().addActivity(this);
    }
}
