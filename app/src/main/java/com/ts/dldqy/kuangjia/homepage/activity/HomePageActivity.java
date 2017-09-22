package com.ts.dldqy.kuangjia.homepage.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.ts.dldqy.kuangjia.R;
import com.ts.dldqy.kuangjia.base.BaseFragmentActivity;
import com.ts.dldqy.kuangjia.base.ExitApplication;
import com.ts.dldqy.kuangjia.homepage.fragment.HomePageFragment;

/**
 * 主界面
 * @author wh
 */
public class HomePageActivity extends BaseFragmentActivity implements RadioGroup.OnCheckedChangeListener {

    private Context context;
    private HomePageFragment homePageFragment;
    private RadioButton homepageRadioButtonHomePage,homepageRadioButtonNotes,homepageRadioButtonMy;//底部导航栏三个按钮
    private RadioGroup homepageRadioGroup;//底部导航栏
    private FragmentManager fragmentmanager;
    private FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        initView();
        
    }
    // 退出程序监听
    private long mExitTime = 0;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                myToast(context,context.getResources().getString(R.string.homepage_exit));
                mExitTime = System.currentTimeMillis();
                return true;
            } else {
                finish();
            }
        }
        return super.dispatchKeyEvent(event);
    }
    /**
     * radiobutton 选中相关事件
     * @param pos radiobutton从左至右顺序
     */
    private void onSelectRadioButton(int pos) {
        //重置选项+隐藏所有Fragment
        fragmentTransaction = fragmentmanager.beginTransaction();
        switch (pos){
            case 0:
                if(homePageFragment==null){
                    homePageFragment=new HomePageFragment();
                    fragmentTransaction.add(R.id.homepageFrameLayout,homePageFragment);
                    onHideFragments(fragmentTransaction,pos);
                }else{
                    fragmentTransaction.show(homePageFragment);
                    onHideFragments(fragmentTransaction,pos);
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }
    /**
     * 隐藏所有的Fragment,避免fragment混乱
     */
    private void onHideFragments(FragmentTransaction transaction, int index) {
        switch (index){
            case 0:
//                transaction.hide();
                break;
            case 1:
                
                break;
            case 2:
                
                break;
        }
    }

    /**
     * 初始化布局
     */
    private void initView() {
        context=this;
        fragmentmanager=getSupportFragmentManager();
        homepageRadioButtonHomePage=$(R.id.homepageRadioButtonHomePage);
        homepageRadioButtonNotes=$(R.id.homepageRadioButtonNotes);
        homepageRadioButtonMy=$(R.id.homepageRadioButtonMy);
        homepageRadioGroup=$(R.id.homepageRadioGroup);
        homepageRadioGroup.setOnCheckedChangeListener(this);
        ExitApplication.getInstance().addActivity(this);
        homepageRadioButtonHomePage.setChecked(true);
        onSelectRadioButton(0);
        
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
            case R.id.homepageRadioButtonHomePage:
                onSelectRadioButton(0);
                break;
            case R.id.homepageRadioButtonNotes:
                onSelectRadioButton(1);
                break;
            case R.id.homepageRadioButtonMy:
                onSelectRadioButton(2);
                break;
        }
    }
}
