package com.ts.dldqy.kuangjia.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.ts.dldqy.kuangjia.R;


/**
 * Fragment基类
 * @author wh
 */

public abstract class BaseFragment extends Fragment {
    /** Fragment当前状态是否可见 */
    protected boolean isVisible;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }
    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }


    /**
     * 不可见
     */
    protected void onInvisible() {


    }


    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();
    
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
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T $(int id){
        return (T) super.getActivity().findViewById(id);
    }

    /**
     * 右上角弹出菜单
     */
    protected PopupWindow popupWindow;
    protected ListView showPop(final Context context, final String[] names, final int[] imgs, View popimg) {
        View popView=View.inflate(getActivity(), R.layout.base_pop_window,null);
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

}
