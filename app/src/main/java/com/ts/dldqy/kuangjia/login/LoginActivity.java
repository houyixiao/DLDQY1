package com.ts.dldqy.kuangjia.login;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.ts.dldqy.kuangjia.R;
import com.ts.dldqy.kuangjia.base.BaseActivity;
import com.ts.dldqy.kuangjia.view.LineEditText;


/**
 * 登录界面
 * @author wh
 * 
 */
public class LoginActivity extends BaseActivity {

    private Context context;
    private LineEditText loginEditTextUser,loginEditTextPwd;
    private Button loginButton;
    private String sLoginUser,sLoginPwd;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initPost();
    }

    /**
     * 发送请求
     */
    private void initPost() {
        if(!getConnectivityStatus()){
            myToast(context,context.getResources().getString(R.string.toast_no_intent));
        }else{
            
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        context=this;
        loginEditTextUser=$(R.id.loginEditTextUser);
        loginEditTextPwd=$(R.id.loginEditTextPwd);
        loginButton=$(R.id.loginButton);
        sLoginUser=loginEditTextUser.getText().toString();
        sLoginPwd=loginEditTextPwd.getText().toString();
    }

    @Override
    protected void onStart() {
        super.onStart();
        
    }
    
}
