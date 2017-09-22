package com.ts.dldqy.kuangjia.login;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ts.dldqy.kuangjia.R;
import com.ts.dldqy.kuangjia.base.BaseActivity;
import com.ts.dldqy.kuangjia.base.ExitApplication;
import com.ts.dldqy.kuangjia.homepage.activity.HomePageActivity;
import com.ts.dldqy.kuangjia.utils.Utils;
import com.ts.dldqy.kuangjia.view.LineEditText;


/**
 * 登录界面
 * @author wh
 * 
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Context context;
    private LineEditText loginEditTextUser,loginEditTextPwd;//用户登录信息输入框
    private Button loginButton;//登录按钮
    private String sLoginUser,sLoginPwd;//用户登录信息
    private RelativeLayout loginTopRelative;//上半部布局
    private LinearLayout loginBotLinear;//下半部布局
    private Drawable dLoginUserNormal,dLoginUserPressed,dLoginPwdNormal,dLoginPwdPressed;//用户登录是否有焦点切换图标
    private boolean bUserHaveFocus,bPwdHaveFocus;//用来判断EditText是否获取焦点
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initAnim();
        initListener();
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        loginButton.setOnClickListener(this);
        loginEditTextUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    bUserHaveFocus=true;
                    loginEditTextUser.setCompoundDrawablesWithIntrinsicBounds(dLoginUserPressed,null,null,null);
                }else{
                    bUserHaveFocus=false;
                    initDraw();
                }
            }
        });
        loginEditTextUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                initDraw();
            }
        });
        
        loginEditTextPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    bPwdHaveFocus=true;
                    loginEditTextPwd.setCompoundDrawablesWithIntrinsicBounds(dLoginPwdPressed,null,null,null);
                }else{
                    bPwdHaveFocus=false;
                    initDraw();
                }
            }
        });
        loginEditTextPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                initDraw();
            }
        });
    }

    /**
     * 初始化图标
     */
    private void initDraw() {
        if (loginEditTextUser.length() > 0) {
            loginEditTextUser.setCompoundDrawablesWithIntrinsicBounds(dLoginUserPressed, null, null, null);
        } else {
            if (bUserHaveFocus) {
                loginEditTextUser.setCompoundDrawablesWithIntrinsicBounds(dLoginUserPressed, null, null, null);
            }else{
                loginEditTextUser.setCompoundDrawablesWithIntrinsicBounds(dLoginUserNormal, null, null, null);
            }
        }

        if (loginEditTextPwd.length() > 0) {
            loginEditTextPwd.setCompoundDrawablesWithIntrinsicBounds(dLoginPwdPressed, null, null, null);
        } else {
            if(bPwdHaveFocus){
                loginEditTextPwd.setCompoundDrawablesWithIntrinsicBounds(dLoginPwdPressed, null, null, null);
            }else{
                loginEditTextPwd.setCompoundDrawablesWithIntrinsicBounds(dLoginPwdNormal, null, null, null);
            }
           
        }
    }

    /**
     * 初始化动画
     */
    private void initAnim() {
        anim(loginTopRelative, "translationY", -(viewHeight(loginTopRelative) + 100), 0, 2000);
        anim(loginBotLinear, "translationY", phoneSize().heightPixels, 0, 1000);
    }

    /**
     * 发送请求
     */
    private void initPost() {
        if(!getConnectivityStatus()){
            myToast(context,context.getResources().getString(R.string.toast_no_intent));
        }else{
            if(onUserInfoNotNull()){
                //登录请求
                Intent intent=new Intent(context, HomePageActivity.class);
                startActivity(intent);
                finish();
            }
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
        loginTopRelative=$(R.id.loginTopRelative);
        loginBotLinear=$(R.id.loginBotLinear);
        dLoginUserNormal=context.getResources().getDrawable(R.mipmap.login_user_normal);
        dLoginUserPressed=context.getResources().getDrawable(R.mipmap.login_user_pressed);
        dLoginPwdNormal=context.getResources().getDrawable(R.mipmap.login_pwd_normal);
        dLoginPwdPressed=context.getResources().getDrawable(R.mipmap.login_pwd_pressed);

    }
    
    /**
     * 判断用户登录信息是否完整
     * @return 
     */
    private boolean onUserInfoNotNull(){
        sLoginUser=loginEditTextUser.getText().toString();
        sLoginPwd=loginEditTextPwd.getText().toString();
        if(Utils.isEmpty(sLoginUser)){
            myToast(context,context.getResources().getString(R.string.login_user_null));
            return false;
        }else if(Utils.isEmpty(sLoginPwd)){
            myToast(context,context.getResources().getString(R.string.login_pwd_null));
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginButton:
                initPost();
                break;
        }
    }

    /**
     * 属性动画
     */
    private void anim(View view, final String animType, float value1, float value2, long time) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, animType, value1, value2);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(time).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ExitApplication.getInstance().exit(context);
    }
}
