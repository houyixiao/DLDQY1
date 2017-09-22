package com.ts.dldqy.kuangjia.homepage.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ts.dldqy.kuangjia.R;
import com.ts.dldqy.kuangjia.base.BaseFragment;

/**
 * 首页
 * @author wh
 */

public class HomePageFragment extends BaseFragment {

    private Context context;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=getActivity();
        if(view==null){
            view=inflater.from(context).inflate(R.layout.fragment_homepage,container,false);
            
        }
        return view;
        
    }

    @Override
    protected void lazyLoad() {
        
    }
}
